import java.util.Arrays;

public class Tag {
	int level;
	String tagName;
	boolean special;
	String args;
	
	public Tag(int l, String t, boolean s) {
		level = l;
		tagName = t;
		special = s;
	}
	
	public Tag(int l, String t, boolean s, String a) {
		level = l;
		tagName = t;
		special = s;
		args = a;
	}

	public boolean hasArgs() {
		if(args == null)
			return false;
		else
			return true;
					
	}
	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public boolean isSpecial() {
		return special;
	}

	public void setSpecial(boolean special) {
		this.special = special;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public String toString() {
		return level + " " + tagName;
	}
	
}