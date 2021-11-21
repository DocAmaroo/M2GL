package analyse;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex. * ;

import model.Cluster;
import model.CoupleClass;
import spoon.Launcher;
import spoon.MavenLauncher;
import spoon.compiler.Environment;
import spoon.processing.AbstractProcessor;
import spoon.reflect.CtModel;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtTypeParameter;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtStatementListImpl;

public class SpoonMain {

    public static int  cptLiens = 0;


    public static String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length() - 1);
    }

    public static ArrayList<CoupleClass> question1(Collection<CtType<?>> listClass, ArrayList<String> projectClass ){
        ArrayList<CoupleClass> coupleclass = new ArrayList<CoupleClass>();
        for (CtType<?> aClass : listClass) {
            Set<CtMethod<?>> listMethods = aClass.getMethods();
            for (CtMethod m : listMethods) {
                List<CtAbstractInvocation> listInvocs = m.getElements(new TypeFilter<CtAbstractInvocation>(CtAbstractInvocation.class));
                for (CtAbstractInvocation invoc : listInvocs) {
                     if(invoc.getExecutable().isConstructor() || !invoc.getExecutable().isStatic()) {
                            String invocName = invoc.getExecutable().getDeclaringType().toString();
                            if(projectClass.contains(invocName)) {
                                if (SpoonMain.containsByCouple(coupleclass, aClass.getSimpleName(), invocName) ||
                                        SpoonMain.containsByCouple(coupleclass, invocName, aClass.getSimpleName())) {
                                    SpoonMain.cptLiens++;
                                    SpoonMain.findByCouple(coupleclass, aClass.getSimpleName(), invocName).incr();
                                } else {
                                    SpoonMain.cptLiens++;
                                    coupleclass.add(new CoupleClass(aClass.getSimpleName(), invocName));
                                }
                            }

                        }else {
                		if(invoc instanceof CtInvocation && 
                			((CtInvocation)invoc).getTarget().getType() != null &&
                			((CtInvocation)invoc).getTarget().getType().getDeclaration() != null){
                			
                    	CtInvocation<?> castedInvoc = (CtInvocation<?>) invoc;
                         String invocName = castedInvoc.getTarget().getType().getTypeDeclaration().getSimpleName();
                         if (projectClass.contains(invocName)) {
                             if (SpoonMain.containsByCouple(coupleclass, aClass.getSimpleName(), invocName) ||
                                     SpoonMain.containsByCouple(coupleclass, invocName, aClass.getSimpleName())) {
                                 SpoonMain.cptLiens++;
                                 SpoonMain.findByCouple(coupleclass, aClass.getSimpleName(), invocName).incr();
                             } else {
                                 SpoonMain.cptLiens++;
                                 coupleclass.add(new CoupleClass(aClass.getSimpleName(), invocName));
                             }
                         }

                         }
                     }
                }
            }
        }
        return coupleclass;
    }
    
    public static ArrayList<Cluster> initializeClusters(Collection<CtType<?>> listClass) throws IOException{
        ArrayList<Cluster> clusters = new ArrayList<Cluster>();

        for (CtType<?> type : listClass) {
            boolean isAlreadyInCluster = false;
            for(Cluster c : clusters) {
                if(c.getClasses().contains(type.getSimpleName())) {
                    isAlreadyInCluster = true;
                }
            }
            if(!isAlreadyInCluster) {
                Cluster cls = new Cluster(type.getSimpleName(), 0);
                clusters.add(cls);
            }
              
        }
        return clusters;
    }
    
    
    public static Stack<Cluster> Q2(ArrayList<Cluster> clusters,
            ArrayList<CoupleClass> couples) throws IOException {
    	
        Stack<Cluster> hierarchicalCluster = new Stack<Cluster>();
        Cluster sourceCluster, targetCluster, firstPart, secondPart, newCluster;
        int bestScore, firstIndex, secondIndex;


        while(clusters.size()>1) { 
            bestScore = 0;
            firstIndex = 0;
            secondIndex = 0;

            for(int i = 0 ; i < clusters.size() ; i++) {
                sourceCluster = clusters.get(i);
                for(int j = 0 ; j < clusters.size() ; j++) {
                    targetCluster = clusters.get(j);
                    if(i != j) {
                        int coupleScore = sourceCluster.getScoreBetweenClusters(couples, targetCluster);
                        if(bestScore < coupleScore){
                            bestScore = coupleScore;
                            firstIndex = i;
                            secondIndex = j;
                        }
                    }                   
                }
            }

            if(bestScore == 0)
                break;

            firstPart = clusters.get(firstIndex);
            secondPart = clusters.get(secondIndex);
            newCluster = new Cluster(firstPart.getClasses(),
                    bestScore + firstPart.getScore() + secondPart.getScore());
            newCluster.addClasses(secondPart.getClasses());
            clusters.remove(firstPart);            
            clusters.remove(secondPart);           
            clusters.add(newCluster);
            hierarchicalCluster.push(newCluster);  
        }
        System.out.println("CLUSTERS ");
        for(Cluster cluster : clusters) {
        	if(cluster.getScore() > 0) {
        		System.out.println(cluster.getClasses().toString() +"[" + cluster.getScore()+ "]");
        	}
        }
       
        return hierarchicalCluster;
    }

    public static boolean containsByCouple(ArrayList<CoupleClass> listCc, String c1, String c2){
        boolean res = false;
        for (CoupleClass cclass : listCc){
            if(cclass.getC1().equals(c1) && cclass.getC2().equals(c2)){
                res = true;
                break;
            }
        }
        return res;
    }

    public static CoupleClass findByCouple(ArrayList<CoupleClass> listCc, String c1, String c2){
        CoupleClass res = new CoupleClass();
        for (CoupleClass cclass : listCc){
            if(cclass.getC1().equals(c1) && cclass.getC2().equals(c2)){
                res = cclass;
                break;
            }
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {

        System.out.println("Begin Analysis");

        // Parsing arguments using JCommander
        Arguments arguments = new Arguments();
        boolean isParsed = arguments.parseArguments(args);

        // if there was a problem parsing the arguments then the program is terminated.
        if (!isParsed) return;

        // Parsed Arguments
        String experiment_source_code = arguments.getSource();
        String experiment_output_filepath = arguments.getTarget();

        // Load project (APP_SOURCE only, no TEST_SOURCE for now)
        Launcher launcher = null;
        if (arguments.isMavenProject()) {
            launcher = new MavenLauncher(experiment_source_code, MavenLauncher.SOURCE_TYPE.APP_SOURCE); // requires M2_HOME environment variable
        } else {
            launcher = new Launcher();
            launcher.addInputResource(experiment_source_code + "/src");
        }

        // Setting the environment for Spoon
        Environment environment = launcher.getEnvironment();
        environment.setCommentEnabled(true); // represent the comments from the source code in the AST
        environment.setAutoImports(true); // add the imports dynamically based on the typeReferences inside the AST nodes.
        //      environment.setComplianceLevel(0); // sets the java compliance level.
        System.out.println("Run Launcher and fetch model.");
        launcher.run(); // creates model of project
        CtModel model = launcher.getModel(); // returns the model of the project
        ArrayList<CoupleClass> coupleclass = new ArrayList<CoupleClass>();
        ArrayList<String> projectClass = new ArrayList<String>();
        Collection<CtType<?>> listClass = model.getAllTypes();
        for (CtType<?> aClass : listClass) {
            projectClass.add(aClass.getSimpleName());
        }
                            //DEBUT EXO 1
        //Q1
        coupleclass = SpoonMain.question1(listClass, projectClass);
        System.out.println(SpoonMain.cptLiens);

        //Q2
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("graph.dot"), "utf-8"))) {
            writer.write(" digraph Q1{\n");
            for (CoupleClass cc : coupleclass) {
                cc.setScore(cc.getCpt()/SpoonMain.cptLiens);
                writer.write(cc.getC1()+"->"+cc.getC2() +"[ label=\""+cc.getScore()+"\"]; \n");
                  System.out.println(cc.getC1() + "->" + cc.getC2() + "[" + cc.getScore() + "]");
            }
            writer.write(" }\n");
        }
                        //FIN EXO 1


                        //DEBUT EXO 2
        //Q1
        SpoonMain.Q2(SpoonMain.initializeClusters(listClass), coupleclass);
        
        //Q2
        
        
    }

}