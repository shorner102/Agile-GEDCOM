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

	public void setMarried(String married) {
	    if (married != null)
		    this.marriageDate = dateHelper.parseDate(married);
	    if(this.marriageDate == null)
	    	errors.add("Marriage date for family " + id + " is not a valid date");
	    else if(!dateHelper.dateBeforeCurrentDate(this.marriageDate))
	    	errors.add("Marriage date in family " + id + " has not happened yet.");
	}

	public LocalDate getDivorced() {
		return divorceDate;
	}

	public void setDivorced(String divorced)  {
	    if (divorced != null)
		    this.divorceDate = dateHelper.parseDate(divorced);
	    if(this.divorceDate == null)
	    	errors.add("Divorce date for family " + id + " is not a valid date");
	    else if(!dateHelper.dateBeforeCurrentDate(this.divorceDate))
	    	errors.add("Divorce date in family " + id + " has not happened yet.");
	    else if(!dateHelper.marriageDateBeforeDivorceDate(this.marriageDate, this.divorceDate))
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

	
	
	

	

}
