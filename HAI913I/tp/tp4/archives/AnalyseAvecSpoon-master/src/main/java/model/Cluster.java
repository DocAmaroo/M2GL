package model;

import java.io.IOException;
import java.util.ArrayList;


public class Cluster {

	private ArrayList<String> classes;
	private float score;
	
	public Cluster(String string, float score) {
		ArrayList<String> classes = new ArrayList<String>();
		classes.add(string);
		this.classes = classes;
		this.score = score;
	}
	
	public Cluster(Cluster other) {
		this.classes = new ArrayList<String>(other.getClasses());
		this.score = other.getScore();
	}

	public Cluster(ArrayList<String> classes, float score) {
		this.classes = classes;
		this.score = score;
	}

	public ArrayList<String> getClasses() {
		return classes;
	}

	public void setClasses(ArrayList<String> classes) {
		this.classes = classes;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
	public void addClasses(ArrayList<String> classesToAdd) {
		for(String classToAdd : classesToAdd) {
			if(!getClasses().contains(classToAdd)) {
				this.classes.add(classToAdd);
			}
		}
	}
	
	boolean isCoupledWith(Cluster other){
		for(String className : getClasses()) {
			if(other.getClasses().contains(className))
				return true;
		}
		return false;
	}

	public void setClass(String cls) {
		ArrayList<String> c = new ArrayList<String>();
		c.add(cls);
		this.classes = c;
	}

    public int getScoreBetweenClusters(ArrayList<CoupleClass> couples, Cluster target) throws IOException {
        int score = 0;
        for(String sourceCls : this.getClasses()) {
            for(String targetCls : target.getClasses()) {
                if(!sourceCls.equals(targetCls)) {
                    for(CoupleClass c : couples) {
                        if(c.getC1().equals(sourceCls) && c.getC2().equals(targetCls)) {
                            score += c.getCpt();
                        }
                    }
                }
            }
        }
        return score;
    }
	
}