
public class Person {
	String id;
	String name;
	char gender;
	String birthday;
	int age;
	boolean alive;
	String death;
	String child;
	String spouse;
	
	public Person() {
		
	}
	
	public Person(String id, String name, char gender, String birthday, int age, boolean alive, String death, String child, String spouse) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.age = age;
		this.alive = alive;
		this.death = death;
		this.child = child;
		this.spouse = spouse;
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

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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

	public String getDeath() {
		return death;
	}

	public void setDeath(String death) {
		this.death = death;
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
}
