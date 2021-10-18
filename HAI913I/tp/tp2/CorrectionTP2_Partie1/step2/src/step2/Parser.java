package step2;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.internal.utils.FileUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Parser {

	public static final String projectPath = "/home/thomas/Documents/M2GL/HAI913I/tp/tp2/CorrectionTP2_Partie1/step2";
	public static final String projectSourcePath = projectPath + "/src";
	public static final String jrePath = "/usr/lib/jvm/jrt-fs.jar";

	public static void main(String[] args) throws IOException {

		// read java files
		final File folder = new File(projectSourcePath);
		ArrayList<File> javaFiles = listJavaFilesForFolder(folder);

		
		double totalClass = 0;
		double totalMethods = 0;
		double totalLines = 0;
		Set<String> packages = new HashSet<String>(); // use Set to force unique element
		double totalLinesByMethods = 0;
		double totalAttributs = 0;

		// List of classes and their number of methods
		Map<String, Integer> classesMethods = new LinkedHashMap<>();
		Map<String, Integer> classesAttributes = new LinkedHashMap<>();

		//
		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			// System.out.println(content);

			CompilationUnit parse = parse(content.toCharArray());

			// print methods info
//			printMethodInfo(parse);

			// print variables info
//			printVariableInfo(parse);
			
			// print method invocations
//			printMethodInvocationInfo(parse);
			
			// Get the number of classe
			totalClass += getNumberOfClasses(parse);
			
			// Get the number of methods
			totalMethods += getNumberOfMethods(parse);
			
			// Get the number of lines
			totalLines += getNumberOfLines(parse);
			
			// Get the number of packages
			packages.addAll(getPackages(parse));
			
			// Get the number of lines by methods
			totalLinesByMethods += getNumberOfLinesByMethods(parse);
			
			// Get the number of attributs
			totalAttributs += getNumberOfAttributs(parse);

			// hashMap update
			addClassMethods(parse, classesMethods);

			// hashMap KEY: class - VALUE : nbAttributes update
			addClassAttributes(parse, classesAttributes);

		}
		
		System.out.println("[+] Number of classes: " + Math.ceil(totalClass));
		System.out.println("[+] Number of methods: " + Math.ceil(totalMethods));
		System.out.println("[+] Number of lines: " + Math.ceil(totalLines));
		System.out.println("[+] Number of packages: " + packages.size());
		System.out.println("[+] Average number of methods by class: " + Math.ceil(totalMethods/totalClass));
		System.out.println("[+] Average number of lines by methods: " + Math.ceil(totalLinesByMethods/totalMethods));
		System.out.println("[+] Average number of attributs by class: " + Math.ceil(totalAttributs/totalClass));

		// print all classes with more than X methods
		// X is the second parameter of the function
		// Always call the method classesWithMoreThanXMethods before classesWithMoreMethods
		// because the last remove some key:values entries in the HashMap classesMethod
		List<String> listClassesWithMoreThanXMethods = new ArrayList<>();
		classesWithMoreThanXMethods(listClassesWithMoreThanXMethods, 4, classesMethods);

		// print 10% classes with the more methods
		int cmSize = classesMethods.size();
		int nbToKeepM = (int) Math.ceil(cmSize*0.1);
		List<String> listClassesWithMoreMethods = new ArrayList<>();
		classesWithMoreMethods(listClassesWithMoreMethods, nbToKeepM, classesMethods);

		// print 10% classes with the more attributs
		int caSize = classesAttributes.size();
		int nbToKeepA = (int) Math.ceil(cmSize*0.1);
		List<String> listClassesWithMoreAttributes = new ArrayList<>();
		classesWithMoreAttributes(listClassesWithMoreAttributes, nbToKeepA, classesAttributes);

		// print classes belonging the two last ones
		inBoth(listClassesWithMoreMethods, listClassesWithMoreAttributes);

	}

	// read all java files from specific folder
	public static ArrayList<File> listJavaFilesForFolder(final File folder) {
		ArrayList<File> javaFiles = new ArrayList<File>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				javaFiles.addAll(listJavaFilesForFolder(fileEntry));
			} else if (fileEntry.getName().contains(".java")) {
				// System.out.println(fileEntry.getName());
				javaFiles.add(fileEntry);
			}
		}

		return javaFiles;
	}

	// create AST
	private static CompilationUnit parse(char[] classSource) {
		ASTParser parser = ASTParser.newParser(AST.JLS4); // java +1.6
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		parser.setBindingsRecovery(true);
 
		Map options = JavaCore.getOptions();
		parser.setCompilerOptions(options);
 
		parser.setUnitName("");
 
		String[] sources = { projectSourcePath }; 
		String[] classpath = {jrePath};
 
		parser.setEnvironment(classpath, sources, new String[] { "UTF-8"}, true);
		parser.setSource(classSource);
		
		return (CompilationUnit) parser.createAST(null); // create and parse
	}

	// navigate method information
	public static void printMethodInfo(CompilationUnit parse) {
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		parse.accept(visitor);

		for (MethodDeclaration method : visitor.getMethods()) {
			System.out.println("Method name: " + method.getName()
					+ " Return type: " + method.getReturnType2());
		}

	}

	// navigate variables inside method
	public static void printVariableInfo(CompilationUnit parse) {

		MethodDeclarationVisitor visitor1 = new MethodDeclarationVisitor();
		parse.accept(visitor1);
		for (MethodDeclaration method : visitor1.getMethods()) {

			VariableDeclarationFragmentVisitor visitor2 = new VariableDeclarationFragmentVisitor();
			method.accept(visitor2);

			for (VariableDeclarationFragment variableDeclarationFragment : visitor2
					.getVariables()) {
				System.out.println("variable name: "
						+ variableDeclarationFragment.getName()
						+ " variable Initializer: "
						+ variableDeclarationFragment.getInitializer());
			}

		}
	}
	
	// navigate method invocations inside method
	public static void printMethodInvocationInfo(CompilationUnit parse) {

		MethodDeclarationVisitor visitor1 = new MethodDeclarationVisitor();
		parse.accept(visitor1);
		for (MethodDeclaration method : visitor1.getMethods()) {

			MethodInvocationVisitor visitor2 = new MethodInvocationVisitor();
			method.accept(visitor2);

			for (MethodInvocation methodInvocation : visitor2.getMethods()) {
				System.out.println("method " + method.getName() + " invoc method "
						+ methodInvocation.getName());
			}

		}
	}

	// Return the number of class
	public static int getNumberOfClasses(CompilationUnit parse) {
		TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		parse.accept(visitor);

		return visitor.getTypes().size();
	}

	// Return the number of methods
	public static int getNumberOfMethods(CompilationUnit parse) {
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		parse.accept(visitor);

		return visitor.getMethods().size();
	}

	// Return the number of lines
	public static int getNumberOfLines(CompilationUnit parse) {
		TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		parse.accept(visitor);
		return parse.getLineNumber(visitor.getLength());
	}

	// Return the number of packages
	public static List<String> getPackages(CompilationUnit parse) {
		PackageDeclarationVisitor visitor = new PackageDeclarationVisitor();
		parse.accept(visitor);

		return visitor.getPackages().stream().map(p -> p.toString()).collect(Collectors.toList());
	}

	// Return the number of lines by methods
	public static int getNumberOfLinesByMethods(CompilationUnit parse) {
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		parse.accept(visitor);

		int totalLines = 0;
		for(MethodDeclaration method : visitor.getMethods()) {
			int start = method.getStartPosition(); // first character position
			int end = start + method.getLength(); // last character position

			int lineStart = parse.getLineNumber(start);
			int lineEnd = parse.getLineNumber(end);
			totalLines += lineEnd - lineStart;
		}

		return totalLines;
	}

	public static int getNumberOfAttributs(CompilationUnit parse) {
		TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		parse.accept(visitor);

		return visitor.getAttribus().size();
	}

	// methods to add
	// add KEY: ClassName - VALUE: nbMethods in a Map
	public static void addClassMethods(CompilationUnit parse, Map<String, Integer> classesMethods) {
		TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		parse.accept(visitor);
		for (TypeDeclaration type : visitor.getTypes()) {
			classesMethods.put(String.valueOf(type.getName()), type.getMethods().length);
		}
	}

	public static void addClassAttributes(CompilationUnit parse, Map<String, Integer> classesAttributes) {
		TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		parse.accept(visitor);
		for (TypeDeclaration type : visitor.getTypes()) {
			// System.out.println("########## type name : " + type.getName() + " " + type.getFields().length);
			classesAttributes.put(String.valueOf(type.getName()), type.getFields().length);
		}
	}

	// print 10% classes with more methods
	public static void classesWithMoreMethods(List<String> listClassesWithMoreMethods, int nbToKeep, Map<String, Integer> classesMethods) {
		while (listClassesWithMoreMethods.size() < nbToKeep) {
			int max = Collections.max(classesMethods.values());
			listClassesWithMoreMethods.addAll(
					classesMethods.entrySet().stream()
							.filter(entry -> entry.getValue() == max)
							.map(entry -> entry.getKey())
							.collect(Collectors.toList())
			);
			for (String type : listClassesWithMoreMethods) {
				classesMethods.remove(type);
			}
		}
		System.out.print("[+] Classes with more methods: ");
		for (int i = 0; i < listClassesWithMoreMethods.size(); i++) {
			System.out.print(listClassesWithMoreMethods.get(i) + " ");
		}
		System.out.println();
	}

	// print classes with more than X methods
	public static void classesWithMoreThanXMethods(List<String> listClassesWithMoreThanXMethods, int x, Map<String, Integer> classesMethods) {
		listClassesWithMoreThanXMethods.addAll(
				classesMethods.entrySet().stream()
						.filter(entry -> entry.getValue() > x)
						.map(entry -> entry.getKey())
						.collect(Collectors.toList())
		);
		System.out.print("[+] Classes with more than X methods: ");
		for (int i = 0; i < listClassesWithMoreThanXMethods.size(); i++) {
			System.out.print(listClassesWithMoreThanXMethods.get(i) + " ");
		}
		System.out.println();
	}

	// print 10% classes with more attributes
	public static void classesWithMoreAttributes(List<String> listClassesWithMoreAttributes, int nbToKeepA, Map<String, Integer> classesAttributes) {
		while (listClassesWithMoreAttributes.size() < nbToKeepA) {
			int max = Collections.max(classesAttributes.values());
			listClassesWithMoreAttributes.addAll(
					classesAttributes.entrySet().stream()
							.filter(entry -> entry.getValue() == max)
							.map(entry -> entry.getKey())
							.collect(Collectors.toList())
			);
			for (String type : listClassesWithMoreAttributes) {
				classesAttributes.remove(type);
			}
		}
		System.out.print("[+] Classes with more attibutes: ");
		for (int i = 0; i < listClassesWithMoreAttributes.size(); i++) {
			System.out.print(listClassesWithMoreAttributes.get(i) + " ");
		}
		System.out.println();
	}

	public static void inBoth(List<String> listClassesWithMoreMethods, List<String> listClassesWithMoreAttributes) {
		List<String> listClassesInBoth = new ArrayList<>();
		// we go through the shortest list
		if (listClassesWithMoreMethods.size() <= listClassesWithMoreAttributes.size()) {
			for (int i = 0; i < listClassesWithMoreMethods.size(); i++) {
				if (listClassesWithMoreAttributes.contains(listClassesWithMoreMethods.get(i))) {
					listClassesInBoth.add(listClassesWithMoreMethods.get(i));
				}
			}
		} else {
			for (int i = 0; i < listClassesWithMoreAttributes.size(); i++) {
				if (listClassesWithMoreMethods.contains(listClassesWithMoreAttributes.get(i))) {
					listClassesInBoth.add(listClassesWithMoreAttributes.get(i));
				}
			}
		}
		System.out.print("[+] Classes in both categories: ");
		for (int i = 0; i < listClassesInBoth.size(); i++) {
			System.out.print(listClassesInBoth.get(i) + " ");
		}
		System.out.println();
	}

}
