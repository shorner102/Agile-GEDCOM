import java.util.*;

public class Family {
	String id;
	String married;
	String divorced;
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
		this.married = married;
		this.divorced = divorced;
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

	public ArrayList<String> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<String> children) {
		this.children = children;
	}
	
	public void addChild(String id) {
		children.add(id);
	}
	
	/*@Override
	public String toString() {
		return "Family [id=" + id + ", married=" + married + ", divorced=" + divorced + ", husbandID=" + husbandID
				+ ", husbandName=" + husbandName + ", wifeID=" + wifeID + ", wifeName=" + wifeName + ", children="
				+ children + "]";
	}*/
	
	
	public static void main (String[] args) {
		//read in the file
		//parse through the lines
		//fp = filepath (should be each line)
		
		System.out.println("Individuals");
		System.out.println("+------+---------------------+------------+----------------+-------+-----------+-------------+----------------+---------------+");
		System.out.println("|  ID  |        Name         |   Gender   |    Birthday    |  Age  |   Alive   |    Death    |     Child      |     Spouse    |");
		System.out.println("+------+---------------------+------------+----------------+-------+-----------+-------------+----------------+---------------+");
		
		/*for(int i = 1; i <= fp.length; i++) {
			System.out.println("|  " + id + "  |        " + name + "         |   " + gender + "   |    " + birthday + "    |  " + age + "  |   " + alive + "   |    " + death + "    |     " + children + "      |     " + spouse + "    |");
			i++;
		}*/
		
		//System.out.println("|  " + id + "  |        " + name + "         |   " + gender + "   |    " + birthday + "    |  " + age + "  |   " + alive + "   |    " + death + "    |     " + children + "      |     " + spouse + "    |");
		System.out.println("+------+---------------------+------------+----------------+-------+-----------+-------------+----------------+---------------+");
		System.out.println(" ");
		System.out.println("Families");
		System.out.println("+------+---------------+----------------+---------------+------------------+-------------+-----------------+-----------------+");
		System.out.println("|  ID  |    Married    |    Divorced    |   Husband ID  |   Husband Name   |   Wife ID   |    Wife Name    |     Children    |");
		System.out.println("+------+---------------+----------------+---------------+------------------+-------------+-----------------+-----------------+");
		
		/*for(int i = 1; i <= fp.length; i++) {
			System.out.println("|  " + id + "  |    " + married + "    |    " + divorced + "   |   " + husbandID + "  |   " + husbandName + "   |   " + wifeID + "   |    " + wifeName + "    |     " + children + "    |");
			i++;
		}*/
		
		//System.out.println("|  " + id + "  |    " + married + "    |    " + divorced + "   |   " + husbandID + "  |   " + husbandName + "   |   " + wifeID + "   |    " + wifeName + "    |     " + children + "    |");
		System.out.println(" ");
		System.out.println("+------+---------------+----------------+---------------+------------------+-------------+-----------------+-----------------+");
	}
}
