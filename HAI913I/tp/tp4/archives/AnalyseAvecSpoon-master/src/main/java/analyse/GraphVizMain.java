package analyse;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Rank.RankDir;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizJdkEngine;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;
import spoon.Launcher;
import spoon.MavenLauncher;
import spoon.compiler.Environment;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;
import static guru.nidi.graphviz.model.Factory.*;

public class GraphVizMain {
	


public static String removeLastChar(String s) {
    if (s == null || s.length() == 0) {
        return s;
    }
    return s.substring(0, s.length()-1);
}



	public void generateGraph(String path, String graphName, String output) throws IOException {
		
		MutableGraph g = mutGraph("example1");
		
		
		StringBuilder sb = new StringBuilder();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            String line;
            ArrayList<String> relations = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
            	if( !(line.contains("}") || line.contains("{") ) ) {
                   // System.out.println(line);
                    relations.add(line);
            	}
            }
            
            for(String rel : relations) {
            	String pattern1 = "[a-z|A-Z]+-";
            	String pattern2 = "[a-z|A-Z]+;";
            	
                Pattern r1 = Pattern.compile(pattern1);
                Matcher m1 = r1.matcher(rel);
                
                Pattern r2 = Pattern.compile(pattern2);
                Matcher m2 = r2.matcher(rel);
                
                String n1="";
                if (m1.find()) {
                     n1= removeLastChar(m1.group(0));
                 //   System.out.print("N1 ->" + n1 + "|");
                    
                }
                String n2 ="";
                if(m2.find()) {
                     n2 = removeLastChar(m2.group(0));
                //    System.out.println("N2 ->" + n2 );

                }

                
            	g.setDirected(true).add(mutNode(n1).addLink(mutNode(n2)));
        		
            }
        }
        Graphviz.useEngine(new GraphvizJdkEngine());
		Graphviz.fromGraph(g).height(700).render(Format.PNG).toFile(new File(output));
		
	}
	public static void main(String[] args) throws IOException {
		GraphVizMain gm = new GraphVizMain();
		System.out.println("génération en cours.... (ça prend un peu de temps :/)");
		gm.generateGraph("Q1.dot", "graph", "graph.png");
		System.out.println("graph généré avec succès, rafraîchissez le projet");
	
	}
}
