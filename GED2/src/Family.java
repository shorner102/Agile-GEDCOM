import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Family {
	String id;
	LocalDate married;
	LocalDate divorced;
	String husbandID;
	String husbandName;
	String wifeID;
	String wifeName;
	ArrayList<String> children;

	public Family() {

	}

	public Family(String id) {
		this.id = id;
		children = new ArrayList<String>();
	}

	

	public Family(String id, String married, String divorced, Person husband, Person wife, ArrayList<String> children) {
		this.id = id;
		this.married = parseDate(married);
		this.divorced = parseDate(divorced);
		this.husbandID = husband.getId();
		this.husbandName = husband.getName();
		this.wifeID = wife.getId();
		this.wifeName = wife.getName();
		this.children = new ArrayList<String>();
		for (int i = 0; i < children.size(); i++) {
			this.children.add(children.get(i));
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getMarried() {
		return married;
	}

	public void setMarried(String married, LocalDate husbBirth, LocalDate wifeBirth) throws BadDateException {
	    if (married != null)
		    this.married = parseDate(married);
	    if(!dateBeforeCurrentDate(this.married))
	    	throw new BadDateException("Date for marriage in family " + id + " is after the current date");
	    if(!birthDateBeforeMarriageDate(husbBirth))
			throw new BadDateException("Date for marriage " + this.married + " is before husband's birthday " + husbBirth);
		if(!birthDateBeforeMarriageDate(wifeBirth))
			throw new BadDateException("Date for marriage " + this.married + " is before wife's birthday " + wifeBirth);
	}

	public LocalDate getDivorced() {
		return divorced;
	}

	public void setDivorced(String divorced) throws BadDateException {
	    if (divorced != null)
		    this.divorced = parseDate(divorced);
	    if(!dateBeforeCurrentDate(this.divorced))
	    	throw new BadDateException("Date for divorce in family " + id + " is after the current date");
	}

	public String getHusbandID() {
		return husbandID;
	}

	public void setHusbandID(String husbandID) {
		this.husbandID = husbandID;
	}

	public String getHusbandName() {
		return husbandName;
	}

	public void setHusbandName(String husbandName) {
		this.husbandName = husbandName;
	}

	public String getWifeID() {
		return wifeID;
	}

	public void setWifeID(String wifeID) {
		this.wifeID = wifeID;
	}

	public String getWifeName() {
		return wifeName;
	}

	public void setWifeName(String wifeName) {
		this.wifeName = wifeName;
	}

	public ArrayList<String> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<String> children) {
		this.children = children;
	}
	
	public void addChild(String id) {
		children.add(id);
	}

	public LocalDate parseDate(String input) {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("d MMM yyyy").toFormatter();
		try {
			LocalDate date = LocalDate.parse(input, formatter);
			return date;
		} catch (DateTimeParseException exc){
			System.out.printf("%s is not parsable!%n", input);
			return null;
		}
	}
	
	public boolean dateBeforeCurrentDate(LocalDate date) {
		LocalDate currentDate = LocalDate.now();
		return date.isBefore(currentDate);
	}
	
	public boolean marriageDateBeforeDivorceDate() {
		
		return married.isBefore(divorced);
	}

	public boolean birthDateBeforeMarriageDate(LocalDate birthday) {
		return birthday.isBefore(this.married);
	}
	/*@Override
	public String toString() {
		return "Family [id=" + id + ", married=" + married + ", divorced=" + divorced + ", husbandID=" + husbandID
				+ ", husbandName=" + husbandName + ", wifeID=" + wifeID + ", wifeName=" + wifeName + ", children="
				+ children + "]";
	}*/
	
	

}
