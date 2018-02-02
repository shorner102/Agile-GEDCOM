import java.util.*;

public class Family {
	String id;
	String married;
	String divorced;
	String husbandID;
	String husbandName;
	String wifeID;
	String wifeName;
	ArrayList<Person> children;
	
	public Family() {
		
	}
	
	public Family(String id, String married, String divorced, Person husband, Person wife, ArrayList<Person> children) {
		this.id = id;
		this.married = married;
		this.divorced = divorced;
		this.husbandID = husband.getId();
		this.husbandName = husband.getName();
		this.wifeID = wife.getId();
		this.wifeName = wife.getName();
		this.children = new ArrayList<Person>();
		for(int i = 0 ; i < children.size(); i++) {
			this.children.add(children.get(i));
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMarried() {
		return married;
	}

	public void setMarried(String married) {
		this.married = married;
	}

	public String getDivorced() {
		return divorced;
	}

	public void setDivorced(String divorced) {
		this.divorced = divorced;
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

	public ArrayList<Person> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Person> children) {
		this.children = children;
	}
}
