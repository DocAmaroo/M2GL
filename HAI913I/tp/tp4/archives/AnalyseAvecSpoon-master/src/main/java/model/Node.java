package model;

import spoon.reflect.declaration.CtType;

/**
 * Node for a dependency graph analysis.
 * Contains a string for the classname,
 * CtType with either an interface or a class from Spoon.
 * @author Pascal.ZARAGOZA
 *
 */
public class Node {

	private String classname;
	private CtType type;
	
	// TODO insert dependency here
	//
	
	public Node(String classname, CtType type) {
		this.classname = classname;
		this.type = type;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public CtType getType() {
		return type;
	}

	public void setType(CtType type) {
		this.type = type;
	}
	
}
