import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Family {
	String id;
	LocalDate marriageDate;
	LocalDate divorceDate;
	String husbandID;
	String husbandName;
	String wifeID;
	String wifeName;
	ArrayList<String> children;
	ArrayList<String> errors;
	DateHelper dateHelper;

	public Family() {

	}

	public Family(String id) {
		this.id = id;
		children = new ArrayList<String>();
		errors = new ArrayList<String>();
		dateHelper = new DateHelper();
	}

	

	public Family(String id, String married, String divorced, Person husband, Person wife, ArrayList<String> children) {
		errors = new ArrayList<String>();
		this.id = id;
		this.marriageDate = dateHelper.parseDate(married);
		this.divorceDate = dateHelper.parseDate(divorced);
		this.husbandID = husband.getId();
		this.husbandName = husband.getName();
		this.wifeID = wife.getId();
		this.wifeName = wife.getName();
		this.children = new ArrayList<String>();
		for (int i = 0; i < children.size(); i++) {
			this.children.add(children.get(i));
		}
		dateHelper = new DateHelper();
	}

	public ArrayList<String> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}

	public void setMarried(LocalDate married) {
		this.marriageDate = married;
	}

	public void setDivorced(LocalDate divorced) {
		this.divorceDate = divorced;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getMarried() {
		return marriageDate;
	}

	public void setMarried(String married, Person husband, Person wife, int lineNumber) {
	    if (married != null) {
			this.marriageDate = dateHelper.parseDate(married);
			if(!dateHelper.dateBeforeCurrentDate(this.marriageDate))
				errors.add("ERROR: FAMILY: US01: " + lineNumber + ": " + id + ": Marriage date " + this.marriageDate + " occurs in the future");
			if(!dateHelper.birthDateBeforeMarriageDate(husband.getBirthday(), this.marriageDate))
				errors.add("ERROR: FAMILY: US02: " + lineNumber + ": " + id + ": Husband's birth date " + husband.getBirthday() + " following marriage date " + this.marriageDate);
			if(!dateHelper.birthDateBeforeMarriageDate(wife.getBirthday(), this.marriageDate))
				errors.add("ERROR: FAMILY: US02: " + lineNumber + ": " + id + ": Wife's birth date " + wife.getBirthday() + " following marriage date " + this.marriageDate);
			if(!dateHelper.marriageDateBeforeDeathDate(this.marriageDate, husband.getDeath()))
				errors.add("ERROR: FAMILY: US05: " + lineNumber + ": " + id + ": Married " + this.marriageDate + " after husband's (" + husband.getId() +") death on " + husband.getDeath());
			if(!dateHelper.marriageDateBeforeDeathDate(this.marriageDate, wife.getDeath()))
				errors.add("ERROR: FAMILY: US05: " + lineNumber + ": " + id + ": Married " + this.marriageDate + " after wife's (" + wife.getId() + ") death on " + wife.getDeath());
			if(!dateHelper.marriageAfter14(this.marriageDate, wife.getBirthday()))
				errors.add("ERROR: FAMILY: US10: " + lineNumber + ": " + id + ":  " + wife.getId() + " got married before she was 14");
			if(!dateHelper.marriageAfter14(this.marriageDate, husband.getBirthday()))
				errors.add("ERROR: FAMILY: US10: " + lineNumber + ": " + id + ":  " + husband.getId() + " got married before he was 14");
			if(!siblingsNotMarried(wife, husband))
				errors.add("ERROR: FAMILY: US18: " + lineNumber + ": " + husband.getId() + " cannot be married to " + wife.getId() + " because they are siblings");
		} else {
			errors.add("ERROR: FAMILY: US42: " + lineNumber + ": " + id + ": Marriage date " + this.marriageDate + " is not a valid date");
		}
	}

	public LocalDate getDivorced() {
		return divorceDate;
	}

	public void setDivorced(String divorced, Person husband, Person wife, int lineNumber)  {
	    if (divorced != null)
		    this.divorceDate = dateHelper.parseDate(divorced);
	    if(this.divorceDate == null)
	    	errors.add("ERROR: FAMILY: US42: " + lineNumber + ": " + id + ": Divorce date " + this.divorceDate + " is not a valid date");
	    else if(dateHelper.divorceDateBeforeDeathDate(this.divorceDate, husband.getDeath()))
	    	errors.add("ERROR: FAMILY: US06: " + lineNumber + ": " + id + ": Divorced " + this.divorceDate + " after husband's (" + husband.getId() + ") death on " + husband.getDeath());
	    else if(dateHelper.divorceDateBeforeDeathDate(this.divorceDate, wife.getDeath()))
	    	errors.add("ERROR: FAMILY: US06: " + lineNumber + ": " + id + ": Divorced " + this.divorceDate + " after wife's (" + wife.getId() + ") death on " + wife.getDeath());
	    else if(!dateHelper.dateBeforeCurrentDate(this.divorceDate))
	    	errors.add("ERROR: FAMILY: US01: " + lineNumber + ": " + id + ": Divorce date " + this.divorceDate + " occurs in the future");
	    else if(!dateHelper.marriageDateBeforeDivorceDate(this.marriageDate, this.divorceDate))
	    	errors.add("ERROR: FAMILY: US04: " + lineNumber + ": " + id + ": Divorced " + this.divorceDate + " before married " + this.marriageDate);
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

	public boolean siblingsNotMarried(Person wife, Person husband) {
		
		return !husband.getChild().equals(wife.getChild());
	}

}
