import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class GEDInAndOut {

	static Tag[] validTags = { new Tag(0, "INDI", true), new Tag(1, "NAME", false), new Tag(1, "SEX", false),
			new Tag(1, "BIRT", false), new Tag(1, "DEAT", false), new Tag(1, "FAMC", false), new Tag(1, "FAMS", false),
			new Tag(0, "FAM", true), new Tag(1, "MARR", false), new Tag(1, "HUSB", false), new Tag(1, "WIFE", false),
			new Tag(1, "CHIL", false), new Tag(1, "DIV", false), new Tag(2, "DATE", false), new Tag(0, "HEAD", false),
			new Tag(0, "TRLR", false), new Tag(0, "NOTE", false) };

	static HashMap<String, Family> fams = new HashMap<>();
	static HashMap<String, Person> indis = new HashMap<>();

	public static void main(String[] args) {
		// String fileName = "proj02test.ged";
		// String fileName = "Sydni_Horner-Project1.ged";
		String fileName = "hapsburgtree.ged";
		//File f = new File("GED2/resources/" + fileName); 
		File f = new File("resources/" + fileName); //leave this line of code in for syd and cass
		System.out.println(f.getAbsolutePath());

		ArrayList<Tag> parsedTags = new ArrayList<Tag>();
		fams = new HashMap<>();
		indis = new HashMap<>();

		String line;
		String currPerson = "";
		String currFamily = "";
		try {
			FileReader fileReader = new FileReader(f);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			int level;
			Tag tag;

			while ((line = bufferedReader.readLine()) != null) {
				// System.out.println("-->" + line);
				level = getLevel(line);
				tag = getTag(line, level);
				tag.setValid(getValid(tag));
				// System.out.println(tag);
				parsedTags.add(tag);
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");

		}
		try {
			for (int i = 0; i < parsedTags.size(); i++) {
				if (parsedTags.get(i).isValid()) {
					String tagName = parsedTags.get(i).getTagName();
					switch (tagName) {
					case "INDI":
						Person newP = new Person(parsedTags.get(i).getArgs());
						currPerson = newP.getId();
						indis.put(currPerson, newP);
						break;
					case "NAME":
						indis.get(currPerson).setName(parsedTags.get(i).getArgs());
						break;
					case "SEX":
						indis.get(currPerson).setGender(parsedTags.get(i).getArgs());
						break;
					case "BIRT":
						indis.get(currPerson).setBirthday(parsedTags.get(i + 1).getArgs());
						break;
					case "DEAT":
						indis.get(currPerson).setAlive(false);
						indis.get(currPerson).setDeath(parsedTags.get(i + 1).getArgs());
						break;
					case "FAMC":
						indis.get(currPerson).setChild(parsedTags.get(i).getArgs());
						break;
					case "FAMS":
						indis.get(currPerson).setSpouse(parsedTags.get(i).getArgs());
						break;
					case "FAM":
						Family newF = new Family(parsedTags.get(i).getArgs());
						currFamily = newF.getId();
						fams.put(currFamily, newF);
						break;
					case "HUSB":
						String hid = parsedTags.get(i).getArgs();
						fams.get(currFamily).setHusbandID(hid);
						fams.get(currFamily).setHusbandName(indis.get(hid).getName());
						break;
					case "WIFE":
						String wid = parsedTags.get(i).getArgs();
						fams.get(currFamily).setWifeID(wid);
						fams.get(currFamily).setWifeName(indis.get(wid).getName());
						break;
					case "CHIL":
						fams.get(currFamily).addChild(parsedTags.get(i).getArgs());
						break;
					case "MARR":
						Person husband = indis.get(fams.get(currFamily).husbandID);
						Person wife = indis.get(fams.get(currFamily).wifeID);
						fams.get(currFamily).setMarried(parsedTags.get(i + 1).getArgs(), husband, wife);
						break;
					case "DIV":
						fams.get(currFamily).setDivorced(parsedTags.get(i + 1).getArgs());
					}

				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}

		printPeople();
		printFamilies();
		printErrors();

	}

	public static void printPeople() {
		System.out.println("Individuals");
		System.out.println(
				"+-------+--------------------------------+--------+-----------------+-----+-------+-----------------+------------+------------+");
		System.out.println(
				"|  ID   |             Name               | Gender |    Birthday     | Age | Alive |      Death      |   Child    |    Spouse  |");
		System.out.println(
				"+-------+--------------------------------+--------+-----------------+-----+-------+-----------------+------------+------------+");

		indis.forEach((k, v) -> System.out.format("| %5s | %30s | %6s | %15s | %3d | %5s | %15s | %10s | %10s |\n",
				v.getId(), v.getName(), v.getGender(), v.getBirthday(), v.getAge(), v.isAlive(), v.getDeath(),
				v.getChild(), v.getSpouse()));

		System.out.println(
				"+-------+--------------------------------+--------+-----------------+-----+-------+-----------------+------------+------------+");

	}

	public static void printFamilies() {

		
		System.out.println("Families");
		System.out.println(
				"+-------+-----------------+-----------------+------------+--------------------------------+------------+--------------------------------+--------------------------------+");
		System.out.println(
				"|  ID   |     Married     |     Divorced    | Husband ID |          Husband Name          |   Wife ID  |             Wife Name          |             Children           |");
		System.out.println(
				"+-------+-----------------+-----------------+------------+--------------------------------+------------+--------------------------------+--------------------------------+");

		fams.forEach((k, v) -> System.out.format("| %5s | %15s | %15s | %10s | %30s | %10s | %30s | %30s |\n",
				v.getId(), v.getMarried(), v.getDivorced(), v.getHusbandID(), v.getHusbandName(), v.getWifeID(),
				v.getWifeName(), v.getChildren()));

		System.out.println(
				"+-------+-----------------+-----------------+------------+--------------------------------+------------+--------------------------------+--------------------------------+");
	}

	public static void printErrors() {
		System.out.println("\nErrors to fix in the GEDCOM files: ");
		fams.forEach((k,v) -> printArrayList(v.getErrors()));
		indis.forEach((k,v) -> printArrayList(v.getErrors()));
	}

	public static int getLevel(String line) {
		return Character.getNumericValue(line.charAt(0));
	}

	public static Tag getTag(String line, int level) {
		int count = 2;
		if (Character.isLowerCase(line.charAt(2)) || line.charAt(2) == '@') {
			return specialCaseTag(line, level);
		}
		while (line.length() > count && line.charAt(count) != ' ')
			count++;
		if (count < line.length())
			return new Tag(level, line.substring(2, count), false, line.substring(count + 1));
		return new Tag(level, line.substring(2, count), false);
	}

	public static boolean getValid(Tag tag) {

		boolean valid = false;

		for (int i = 0; i < validTags.length; i++) {
			if (tag.tagName.equals(validTags[i].getTagName()) && tag.level == validTags[i].getLevel()
					&& tag.special == validTags[i].isSpecial()) {
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
	
	public static void printArrayList(ArrayList<String> arr) {
		for(int i = 0 ; i < arr.size(); i ++) {
			System.out.println(arr);
		}
	}
	
	public static Family getFamily(String id) {

		for(String i : fams.keySet()) {
			if(id.equals(fams.get(i).getId())){
				return fams.get(i);
			}
		}
		return null;
	}

}
