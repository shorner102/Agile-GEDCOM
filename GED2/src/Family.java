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
	ArrayList<String> errors;

	public Family() {

	}

	public Family(String id) {
		this.id = id;
		children = new ArrayList<String>();
		errors = new ArrayList<String>();
	}

	

	public Family(String id, String married, String divorced, Person husband, Person wife, ArrayList<String> children) {
		errors = new ArrayList<String>();
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

	public ArrayList<String> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}

	public void setMarried(LocalDate married) {
		this.married = married;
	}

	public void setDivorced(LocalDate divorced) {
		this.divorced = divorced;
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

	public void setMarried(String married) {
	    if (married != null)
		    this.married = parseDate(married);
	    if(this.married == null)
	    	errors.add("Marriage date for family " + id + " is not a valid date");
	    else if(!dateBeforeCurrentDate(this.married))
	    	errors.add("Marriage date in family " + id + " has not happened yet.");
	}

	public LocalDate getDivorced() {
		return divorced;
	}

	public void setDivorced(String divorced)  {
	    if (divorced != null)
		    this.divorced = parseDate(divorced);
	    if(this.divorced == null)
	    	errors.add("Divorce date for family " + id + " is not a valid date");
	    else if(!dateBeforeCurrentDate(this.divorced))
	    	errors.add("Divorce date in family " + id + " has not happened yet.");
	    else if(!marriageDateBeforeDivorceDate())
	    	errors.add("Divorce date in family " + id + " is before marriage date.");
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
			//System.out.printf("%s is not parsable!%n", input);
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
	

	

}
