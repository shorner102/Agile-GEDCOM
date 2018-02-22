import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Person {
	String id;
	String name;
	String gender;
	LocalDate birthday;
	int age;
	boolean alive;
	LocalDate death;
	String child;
	String spouse;
	ArrayList<String> errors;
	DateHelper dateHelper;
	
	public Person() {
		
	}	
	public Person(String id) {
		this.id = id;
		alive = true;
		errors = new ArrayList<String>();
		dateHelper = new DateHelper();
	}
	
	public Person(String id, String name, String gender, String birthday, int age, boolean alive, String death, String child, String spouse) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birthday = dateHelper.parseDate(birthday);
		this.alive = alive;
		this.death = dateHelper.parseDate(death);
		this.child = child;
		this.spouse = spouse;
		this.age = dateHelper.calculateAge(this.alive, this.birthday, this.death);
		errors = new ArrayList<String>();
		dateHelper = new DateHelper();
	}

	public ArrayList<String> getErrors() {
		return errors;
	}
	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	public void setDeath(LocalDate death) {
		this.death = death;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = dateHelper.parseDate(birthday);
	    if(this.birthday == null) 
    		errors.add("Birthday for person " + id + " is not a valid date.");
	    else if(!dateHelper.dateBeforeCurrentDate(this.birthday))
	    	errors.add("Birthday for person " + id + " has not happened yet.");
	    else
	    	this.age = dateHelper.calculateAge(this.alive, this.birthday, this.death);
	    
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public LocalDate getDeath() {
		return death;
	}

	public void setDeath(String death) {
		this.death = dateHelper.parseDate(death);

	    if(this.death == null) 
    		errors.add("Death date for person " + id + " is not a valid date.");
	    else if(!dateHelper.dateBeforeCurrentDate(this.death))
    		errors.add("Death date for person " + id + " has not happened yet.");
	    else if(this.birthday != null)
	    	this.age = dateHelper.calculateAge(this.alive, this.birthday, this.death);
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getSpouse() {
		return spouse;
	}

	public void setSpouse(String spouse) {
		this.spouse = spouse;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", gender=" + gender + ", birthday=" + birthday + ", age=" + age
				+ ", alive=" + alive + ", death=" + death + ", child=" + child + ", spouse=" + spouse + "]";
	}


    
    

}
