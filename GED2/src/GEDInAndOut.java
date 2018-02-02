import java.io.*;
import java.util.Arrays;

public class GEDInAndOut {
	
	static Tag[] validTags = { new Tag(0, "INDI", true), new Tag(1, "NAME", false), new Tag(1, "SEX", false), new Tag(1, "BIRT", false),
			new Tag(1, "DEAT", false), new Tag(1, "FAMC", false), new Tag(1, "FAMS", false), new Tag(0, "FAM", true), new Tag(1, "MARR", false),
			new Tag(1, "HUSB", false), new Tag(1, "WIFE", false), new Tag(1, "CHIL", false), new Tag(1, "DIV", false), new Tag(2, "DATE", false),
			new Tag(0, "HEAD", false), new Tag(0, "TRLR", false), new Tag(0, "NOTE", false) };

	
	public static void main(String[] args) {
		String fileName = "proj02test.ged";
		//String fileName = "Sydni_Horner-Project1.ged";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			
			int level;
			Tag tag;

			while ((line = bufferedReader.readLine()) != null) {
				System.out.println("-->" + line);
				level = getLevel(line);
				tag = getTag(line, level);
				System.out.print("<--" + level + "|" + tag.getTagName() + "|" + getValid(tag));
				if(tag.hasArgs())
					System.out.println("|" + tag.getArgs());
				else
					System.out.println();
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");

		}
	}

	public static int getLevel(String line) {
		return Character.getNumericValue(line.charAt(0));
	}

	public static Tag getTag(String line, int level) {
		int count = 2;
		if(Character.isLowerCase(line.charAt(2))) {
			return specialCaseTag(line, level);
		}
		while (line.length() > count && line.charAt(count) != ' ')
			count++;
		if(count < line.length())
			return new Tag(level, line.substring(2, count), false, line.substring(count + 1));
		return new Tag(level, line.substring(2, count), false);
	}

	public static String getValid(Tag tag) {
		
		boolean valid = false;
		
		for(int i = 0; i < validTags.length; i++) {
			if(tag.tagName.equals(validTags[i].getTagName()) && tag.level == validTags[i].getLevel() && tag.special == validTags[i].isSpecial()) {
				valid = true;
				break;
			}
		}
		if(valid) return "Y";
		else return "N";
	}
	
	public static Tag specialCaseTag(String line, int level) {
		int count = 2;
		while (line.charAt(count) != ' ')
			count++;
		String args = line.substring(2, count);
		return new Tag(level, line.substring(count + 1), true, args);
	}

}
