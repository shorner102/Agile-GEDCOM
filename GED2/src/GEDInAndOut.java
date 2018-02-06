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
					people[personCounter].setAlive(false);
				}
				else if(parsedTags.get(i).getTagName().equals("FAM")) 
					families[++familyCounter] = new Family(parsedTags.get(i).getArgs());
				
				else if(parsedTags.get(i).getTagName().equals("HUSB")) {
					families[familyCounter].setHusbandID(parsedTags.get(i).getArgs());
				}
				else if(parsedTags.get(i).getTagName().equals("WIFE")) 
					families[familyCounter].setWifeID(parsedTags.get(i).getArgs());
				else if(parsedTags.get(i).getTagName().equals("CHIL")) 
					families[familyCounter].addChild(parsedTags.get(i).getArgs());

				//System.out.println(parsedTags.get(i));
				
			}
				
		}
		
		printPeople(people);
		
		printFamilies(families);

		
		
	}
	
	public static void printPeople(Person[] people) {
		System.out.println("Individuals");
		System.out.println("+-------+--------------------------------+--------+-----------------+-----+-------+-----------------+------------+------------+");
		System.out.println("|  ID   |             Name               | Gender |    Birthday     | Age | Alive |      Death      |   Child    |    Spouse  |");
		System.out.println("+-------+--------------------------------+--------+-----------------+-----+-------+-----------------+------------+------------+");
		

		for(int i = 0; i < people.length; i++) {
			if(people[i] != null)
				System.out.format("| %5s | %30s | %6s | %15s | %3d | %5s | %15s | %10s | %10s |\n", people[i].getId() , people[i].getName(), people[i].getGender(), people[i].getBirthday() , people[i].getAge() , people[i].isAlive() ,  people[i].getDeath(), people[i].getChild() , people[i].getSpouse());
				
		}
		System.out.println("+-------+--------------------------------+--------+-----------------+-----+-------+-----------------+------------+------------+");
		
	}
	
	public static void printFamilies(Family[] families) {
		
		System.out.println("Families");
		System.out.println("+-------+-----------------+-----------------+------------+--------------------------------+------------+--------------------------------+--------------------------------+");
		System.out.println("|  ID   |     Married     |     Divorced    | Husband ID |          Husband Name          |   Wife ID  |             Wife Name          |             Children           |");
		System.out.println("+-------+-----------------+-----------------+------------+--------------------------------+------------+--------------------------------+--------------------------------+");
		
		for(int i = 0; i < families.length; i++) {
			if(families[i] != null)
				System.out.format("| %5s | %15s | %15s | %10s | %30s | %10s | %30s | %30s |\n", families[i].getId() , families[i].getMarried(),families[i].getDivorced() , families[i].getHusbandID(),families[i].getHusbandName() , families[i].getWifeID(),families[i].getWifeName(),families[i].getChildren());
			
		}

		System.out.println("+-------+-----------------+-----------------+------------+--------------------------------+------------+--------------------------------+--------------------------------+");
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
