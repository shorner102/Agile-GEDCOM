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
	static int lineNumber;
	static DateHelper dateHelper = new DateHelper();

	public static void main(String[] args) {
		 //String fileName = "Family-3-24-Mar-2018-893.ged";
		//String fileName = "Sydni_Horner-Project1.ged";
		//String fileName = "hapsburgtree.ged";
		String fileName = "Sydni_Horner-Errors.ged";

		File f = new File("GED2/resources/" + fileName);
		//File f = new File("resources/" + fileName); //leave this line of code in for syd and cass
		System.out.println(f.getAbsolutePath());

		ArrayList<Tag> parsedTags = new ArrayList<Tag>();
		fams = new HashMap<>();
		indis = new HashMap<>();

		String line;
		String currPerson = "";
		String currFamily = "";
		lineNumber = 1;
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
				parsedTags.add(tag);
				lineNumber++;
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");

		}
		int i = 0;
		try {
			for (; i < parsedTags.size(); i++) {
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
						indis.get(currPerson).setBirthday(parsedTags.get(i + 1).getArgs(), parsedTags.get(i).getLineNumber());
						break;
					case "DEAT":
						indis.get(currPerson).setAlive(false);
						indis.get(currPerson).setDeath(parsedTags.get(i + 1).getArgs(), parsedTags.get(i).getLineNumber());
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
						fams.get(currFamily).setMarried(parsedTags.get(i + 1).getArgs(), husband, wife, parsedTags.get(i).getLineNumber());
						break;
					case "DIV":
						Person husband1 = indis.get(fams.get(currFamily).husbandID);
						Person wife1 = indis.get(fams.get(currFamily).wifeID);
						fams.get(currFamily).setDivorced(parsedTags.get(i + 1).getArgs(), husband1, wife1, parsedTags.get(i).getLineNumber());
					}

				}

			}
		} catch (Exception e) {
			System.out.println(e + " " + i + " " + e.getStackTrace()[0].getLineNumber()) ;
		}

		printPeople();
		printFamilies();
		printDeceased();
		printLivingSingles();
		printLivingMarried();
		listUpcomingBirthdays();
		listUpcomingAnniversaries();
		listLargeAgeDifferences();
		listRecentBirths();
		listRecentDeaths();
		validateBirthBefore();
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
	
	public static void printDeceased() {

		
		System.out.println("Deceased");
		System.out.println(
				"+-------+--------------------------------+-----------------+");
		System.out.println(
				"|  ID   |             Name               |      Death      |");
		System.out.println(
				"+-------+--------------------------------+-----------------+");

		
		for(String i : indis.keySet()) {
			if(!indis.get(i).isAlive())
				System.out.format("| %5s | %30s | %15s |\n",
						indis.get(i).getId(), indis.get(i).getName(), indis.get(i).getDeath());
		}
		

		System.out.println(
				"+-------+--------------------------------+-----------------+");

	}
	
	public static void printLivingSingles() {

		
		System.out.println("Living Singles");
		System.out.println(
				"+-------+--------------------------------+-----+-------+");
		System.out.println(
				"|  ID   |             Name               | Age | Alive |");
		System.out.println(
				"+-------+--------------------------------+-----+-------+");

		
		for(String i : indis.keySet()) {
			if(indis.get(i).isAlive() && indis.get(i).age > 30 && indis.get(i).spouse == null)
				System.out.format("| %5s | %30s | %3d | %5s |\n",
						indis.get(i).getId(), indis.get(i).getName(), indis.get(i).getAge(), indis.get(i).isAlive());
		}
		

		System.out.println(
				"+-------+--------------------------------+-----+-------+");

	}
	
	public static void printLivingMarried() {

		
		System.out.println("Living Married");
		System.out.println(
				"+-------+--------------------------------+-------+------------+");
		System.out.println(
				"|  ID   |             Name               | Alive |    Spouse  |");
		System.out.println(
				"+-------+--------------------------------+-------+------------+");

		
		for(String i : indis.keySet()) {
			if(indis.get(i).isAlive() && indis.get(i).getSpouse() != null)
				System.out.format("| %5s | %30s | %5s | %10s |\n",
						indis.get(i).getId(), indis.get(i).getName(), indis.get(i).isAlive(), indis.get(i).getSpouse());
		}
		

		System.out.println(
				"+-------+--------------------------------+-------+------------+");

	}

	public static void printErrors() {
		System.out.println("\nErrors to fix in the GEDCOM files: ");
		for(String i : fams.keySet()) {
			for(int j = 0; j < fams.get(i).getErrors().size(); j++) {
				System.out.println(fams.get(i).getErrors().get(j));
			}
		}
		for(String i : indis.keySet()) {
			for(int j = 0; j < indis.get(i).getErrors().size(); j++) {
				System.out.println(indis.get(i).getErrors().get(j));
			}
		}
		//fams.forEach((k,v) -> printArrayList(v.getErrors()));
		//indis.forEach((k,v) -> printArrayList(v.getErrors()));
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
			return new Tag(level, line.substring(2, count), false, line.substring(count + 1), lineNumber);
		return new Tag(level, line.substring(2, count), false, lineNumber);
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
		return new Tag(level, line.substring(count + 1), true, args, lineNumber);
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
	
	//Next 30 days
	public static void listUpcomingBirthdays() {
		System.out.println("Birthdays in the next 30 days:" );
		for(String i : indis.keySet()) {
			if(indis.get(i).getBirthday() != null && dateHelper.monthDayInTheNext30Days(indis.get(i).getBirthday()))
					System.out.println(indis.get(i).getId() + ", " + indis.get(i).getName() + ", " + indis.get(i).getBirthday());
		}
		System.out.println();
	}
	
	//Next 30 days
	public static void listUpcomingAnniversaries() {
		System.out.println("Anniversaries in the next 30 days:" );
		for(String i : fams.keySet()) {
			if(fams.get(i).getMarried() != null &&dateHelper.monthDayInTheNext30Days(fams.get(i).getMarried()))
					System.out.println(fams.get(i).getId() + ", " + fams.get(i).getWifeName() + " and " + fams.get(i).getHusbandName() + ", " + fams.get(i).getMarried());
		}
		System.out.println();
	}
	 
	public static void listLargeAgeDifferences() {
		System.out.println("Large Age differences in Married Couples");
		//fams.get(currFamily).setHusbandName(indis.get(hid).getName());
		int husbandAge;
		int wifeAge;
		for(String i : fams.keySet()) {
			//Get the age at marriage (this is the current age)
			if(fams.get(i).getMarried() != null && indis.get(fams.get(i).getHusbandID()).getBirthday() != null && indis.get(fams.get(i).getWifeID()).getBirthday() != null) {

				husbandAge = dateHelper.ageAtDate(indis.get(
						fams.get(i).getHusbandID()).getBirthday(), 
						fams.get(i).getMarried());
				wifeAge = dateHelper.ageAtDate(indis.get(fams.get(i).getWifeID()).getBirthday(), fams.get(i).getMarried());
				if(husbandAge * 2 <= wifeAge || wifeAge * 2 <= husbandAge)
					System.out.println(fams.get(i).getHusbandName() + " was " + husbandAge +" and " + fams.get(i).getWifeName() + " was " + wifeAge + " when they got married");
			}
				
		}
		System.out.println();
	}

	public static void listRecentBirths() {
		System.out.println("Births in the past 30 days:");
		for(String i : indis.keySet()) {
			Person ind = indis.get(i);
			if(dateHelper.dateIsInPast30Days(ind.getBirthday())) {
				System.out.println("Individual " + ind.getId() + ": " + ind.getName() + " was born on " + ind.getBirthday());
			}
		}
		System.out.println();
	}

	public static void listRecentDeaths() {
		System.out.println("Deaths in the past 30 days:");
		for(String i : indis.keySet()) {
			Person ind = indis.get(i);
			if(dateHelper.dateIsInPast30Days(ind.getDeath())) {
				System.out.println("Individual " + ind.getId() + ": " + ind.getName() + " died on " + ind.getDeath());
			}
		}
		System.out.println();
	}

	public static void validateBirthBefore() {
		for(String i : indis.keySet()) {
			LocalDate birth = indis.get(i).getBirthday();
			Family fam = fams.get(indis.get(i).getChild());
			if(fam == null || birth == null) {
				continue;
			}
			LocalDate married = fam.getMarried();
			LocalDate divorced = fam.getDivorced();
			if(dateHelper.birthDateBeforeMarriageDate(birth, married) || !dateHelper.birthBeforeDivorce(birth, divorced)) {
				indis.get(i).addError("ERROR: INDIVIDUAL: US08: " + i + ": Birthday is before marriage of parents");
			}

			Person mother = indis.get(fam.getWifeID());
			Person father = indis.get(fam.getHusbandID());
			if(!dateHelper.birthBeforeParentsDeath(birth, father.getDeath(), mother.getDeath())) {
				indis.get(i).addError("ERROR: INDIVIDUAL: US09: " + i + ": Birthday is after death of parents");
			}
		}
	}

}
