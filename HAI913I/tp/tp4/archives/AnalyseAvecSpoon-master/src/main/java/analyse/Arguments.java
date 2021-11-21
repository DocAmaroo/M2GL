package analyse;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Arguments {

	// Path of the source monolithic project 
	@Parameter(names = {"--source" , "-s"}, description = "provide path of the source code of the monolith.")
	private static String source;
	
	// Path of the target code for the microservice-based project
	@Parameter(names = {"--target" , "-t"}, description = "provide path for the printing of the target code of the monolith.")
	private static String target;
	
//	// Path of the target code for the microservice-based project
//	@Parameter(names = {"--adl" , "-a"}, description = "provide path of the xml of the architecture description file.")
//	private static String description;
	
	// help parameter for displaying the required arguments
	@Parameter(names = {"--help" , "-h"}, help = true)
	private static boolean help;
	
	// whether the project is using Maven for dependency management
	@Parameter(names = {"--maven" , "-m"}, description = "Included when the project is using Maven for dependency management.")
	private static boolean isMavenProject = false;
	
	// set to true after parsing
	private static boolean isParsed = false;

	public static void main(String[] args) {
		Arguments arguments = new Arguments();
		JCommander.newBuilder()
		  .addObject(arguments)
		  .build()
		  .parse(args);
		
		System.out.println("Args.main()");
		System.err.println(arguments.getSource());
		System.err.println(arguments.getTarget());
//		System.err.println(arguments.getDescription());
	}
	
	/**
	 * Parses the arguments of a CLI and checks that each path is correct.
	 * @param args
	 * @return
	 */
	public boolean parseArguments(String[] args) {
		JCommander.newBuilder()
		  .addObject(this)
		  .build()
		  .parse(args);
		
		isParsed = true;
		return this.testArguments();
	}

	public String getSource() {
		return source;
	}

	public String getTarget() {
		return target;
	}
	
//	public String getDescription() {
//		return description;
//	}
	
	public boolean isMavenProject() {
		return isMavenProject;
	}

	private boolean testArguments() {
		
		// if any of the mandatory arguments are null then signal
		if(source == null) { // || target == null || description == null) {
			System.err.println("Missing mandatory arguments. Please provide them or add the --help argument for details.");
			return false;
		}
		
		return true;
	}
}
