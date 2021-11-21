package model;

import analyse.SpoonMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoupleClass{

    String class1;
    String class2;
    float cpt;
    float score;
    boolean traité;
    
    public CoupleClass() {

    }

    public CoupleClass(String class1, String class2) {
        this.class1 = class1;
        this.class2 = class2;
        this.cpt = 1;
    }

    public float getCpt() {
        return this.cpt;
    }

    public void incr() {
        this.cpt++;
    }

    public void setCpt(float cpt){
        this.cpt = cpt;
    }
    public String getC1() {
        return class1;
    }

    public String getC2() { return class2; }

    public static ArrayList<CoupleClass> extractFromFile(String path) throws IOException {

        ArrayList<CoupleClass> cc = new  ArrayList<CoupleClass>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!(line.contains("}") || line.contains("{"))) {
                    String pattern1 = "[a-z|A-Z]+-";
                    String pattern2 = ">[a-z|A-Z]+\\[ .*\\]";

                    Pattern r1 = Pattern.compile(pattern1);
                    Matcher m1 = r1.matcher(line);

                    Pattern r2 = Pattern.compile(pattern2);
                    Matcher m2 = r2.matcher(line);

                    String n1 = "";
                    if (m1.find()) {
                        n1 = SpoonMain.removeLastChar(m1.group(0));

                    }
                    String n2 = "";
                    if (m2.find()) {
                        String pattern3 = "\"[0-9| \\.]+";
                        String pattern4 = "[a-z|A-Z]+\\[";

                        n2 = SpoonMain.removeLastChar(m2.group(0));

                        Pattern r3 = Pattern.compile(pattern3);
                        Matcher m3 = r3.matcher(line);

                        Pattern r4 = Pattern.compile(pattern4);
                        Matcher m4 = r4.matcher(line);

                        if(m4.find()) {
                            String n22 = SpoonMain.removeLastChar(m4.group(0));

                            if (m3.find()) {
                                String cpt = m3.group(0).substring(1);
                                CoupleClass cclass = new CoupleClass(n1, n22);

                                cclass.setCpt(Float.parseFloat(cpt));
                                cc.add(cclass);
                            }
                        }

                    }

                }
            }

        }
        return cc;

    }

	public void setScore(float score) {
		this.score=score;
		
	}

	public float getScore() {
		return this.score;
	}
}