package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import graphs.CallGraph;
import graphs.StaticCallGraph;
import processors.ASTProcessor;

public class CallGraphMain extends AbstractMain {

	@Override
	protected void menu() {
		StringBuilder builder = new StringBuilder();
		builder.append("1. Static call graph.");
		builder.append("\n2. Dynamic call graph.");
		builder.append("\n3. Help menu.");
		builder.append("\n"+QUIT+". To quit.");
		
		System.out.println(builder);
	}

	public static void main(String[] args) {	
		CallGraphMain main = new CallGraphMain();
		BufferedReader inputReader;
		CallGraph callGraph = null;
		try {
			inputReader = new BufferedReader(new InputStreamReader(System.in));
			if (args.length < 1)
				setTestProjectPath(inputReader);
			else
				verifyTestProjectPath(inputReader, args[0]);
			String userInput = "";
			
			do {	
				main.menu();			
				userInput = inputReader.readLine();
				main.processUserInput(userInput, callGraph);
				Thread.sleep(3000);
				
			} while(!userInput.equals(QUIT));
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	protected void processUserInput(String userInput, ASTProcessor processor) {
		CallGraph callGraph = (CallGraph) processor;
		try {
			switch(userInput) {
				case "1":
					callGraph = StaticCallGraph.createCallGraph(TEST_PROJECT_PATH);
					callGraph.log();
					break;
				
				case "2":
					System.err.println("Not implemented yet");
					break;
				
				case "3":
					return;
					
				case QUIT:
					System.out.println("Bye...");
					return;
				
				default:
					System.err.println("Sorry, wrong input. Please try again.");
					return;
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
