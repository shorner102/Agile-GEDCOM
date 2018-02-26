import java.util.Arrays;

public class Tag {
	int level;
	String tagName;
	boolean special;
	String args;
	boolean valid;
	int lineNumber;
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Tag(int l, String t, boolean s) {
		level = l;
		tagName = t;
		special = s;
		
	}
	
	public Tag(int l, String t, boolean s, int ln) {
		level = l;
		tagName = t;
		special = s;
		lineNumber = ln;
		
	}

	public Tag(int l, String t, boolean s, String a, int ln) {
		level = l;
		tagName = t;
		special = s;
		args = a;
		lineNumber = ln;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
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
		String str = "";
		str += "<--" + getLevel() + "|" + getTagName() + "|" ;
		if(isValid()) str += "Y";
		else str += "N";
		if(hasArgs())
			str += "|" + getArgs();
		return str;
	}
	
}
