import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GEDInAndOut {
	
	static Tag[] validTags = { new Tag(0, "INDI", true), new Tag(1, "NAME", false), new Tag(1, "SEX", false), new Tag(1, "BIRT", false),
			new Tag(1, "DEAT", false), new Tag(1, "FAMC", false), new Tag(1, "FAMS", false), new Tag(0, "FAM", true), new Tag(1, "MARR", false),
			new Tag(1, "HUSB", false), new Tag(1, "WIFE", false), new Tag(1, "CHIL", false), new Tag(1, "DIV", false), new Tag(2, "DATE", false),
			new Tag(0, "HEAD", false), new Tag(0, "TRLR", false), new Tag(0, "NOTE", false) };

	
	public static void main(String[] args) {
		//String fileName = "proj02test.ged";
		String fileName = "Sydni_Horner-Project1.ged";
		
		ArrayList<Tag> parsedTags = new ArrayList<Tag>();
		Family[] families = new Family[1000];
		Person[] people = new Person[5000];
		int familyCounter = -1;
		int personCounter = -1;
		
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			
			int level;
			Tag tag;

			while ((line = bufferedReader.readLine()) != null) {
				//System.out.println("-->" + line);
				level = getLevel(line);
				tag = getTag(line, level);
				tag.setValid(getValid(tag));
				//System.out.println(tag);
				parsedTags.add(tag);
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");

		}
		for(int i = 0; i < parsedTags.size(); i++) {
			if(parsedTags.get(i).isValid()) {
				if(parsedTags.get(i).getTagName().equals("INDI"))
					people[++personCounter] = new Person(parsedTags.get(i).getArgs());
				else if(parsedTags.get(i).getTagName().equals("NAME"))
					people[personCounter].setName(parsedTags.get(i).getArgs());
				else if(parsedTags.get(i).getTagName().equals("SEX"))
					people[personCounter].setGender(parsedTags.get(i).getArgs());
				else if(parsedTags.get(i).getTagName().equals("BIRT"))
					people[personCounter].setBirthday(parsedTags.get(i + 1).getArgs());
				else if(parsedTags.get(i).getTagName().equals("DEAT")) {
					people[personCounter].setDeath(parsedTags.get(i + 1).getArgs());
					people[personCounter].setAlive(true);
				}
				
			}
				
		}
		for(int i = 0; i < people.length; i++) {
			System.out.println(people[i]);
		}
		
		
		
	}

	public static int getLevel(String line) {
		return Character.getNumericValue(line.charAt(0));
	}

	public static Tag getTag(String line, int level) {
		int count = 2;
		if(Character.isLowerCase(line.charAt(2)) || line.charAt(2) == '@') {
			return specialCaseTag(line, level);
		}
		while (line.length() > count && line.charAt(count) != ' ')
			count++;
		if(count < line.length())
			return new Tag(level, line.substring(2, count), false, line.substring(count + 1));
		return new Tag(level, line.substring(2, count), false);
	}

	public static boolean getValid(Tag tag) {
		
		boolean valid = false;
		
		for(int i = 0; i < validTags.length; i++) {
			if(tag.tagName.equals(validTags[i].getTagName()) && tag.level == validTags[i].getLevel() && tag.special == validTags[i].isSpecial()) {
				valid = true;
				break;
			}
		}
		return valid;
	}
	
	public static Tag specialCaseTag(String line, int level) {
		int count = 2;
		while (line.charAt(count) != ' ')
			count++;
		String args = line.substring(2, count);
		return new Tag(level, line.substring(count + 1), true, args);
	}

}
