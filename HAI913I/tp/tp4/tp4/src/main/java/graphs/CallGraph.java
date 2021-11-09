package graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.LongStream;

import loggers.ConsoleLogger;
import loggers.FileLogger;
import loggers.LogRequest;
import loggers.StandardLogRequestLevel;
import processors.ASTProcessor;

public abstract class CallGraph extends ASTProcessor {
	/* ATTRIBUTES */
	private Set<String> methods = new HashSet<>();
	private Map<String, Map<String, Integer>> invocations = new HashMap<>();
	private FileLogger loggerChain;
	
	/* CONSTRUCTOR */
	public CallGraph(String projectPath) {
		super(projectPath);
		setLoggerChain();
	}
	
	/* METHODS */
	protected void setLoggerChain() {
		loggerChain = new FileLogger(StandardLogRequestLevel.DEBUG);
		loggerChain.setNextLogger(new ConsoleLogger(StandardLogRequestLevel.INFO));
	}
	
	public Set<String> getMethods() {
		return methods;
	}
	
	public long getNbMethods() {
		return methods.size();
	}
	
	public long getNbInvocations() {
		return invocations.keySet()
		.stream()
		.map(source -> invocations.get(source))
		.map(destination -> destination.values())
		.flatMap(Collection::stream)
		.flatMapToLong(value -> LongStream.of((long) value))
		.sum();
	}
	
	public Map<String, Map<String, Integer>> getInvocations() {
		return invocations;
	}
	
	public boolean addMethod(String method) {
		return methods.add(method);
	}
	
	public boolean addMethods(Set<String> methods) {
		return methods.addAll(methods);
	}
	
	public void addInvocation(String source, String destination) {
		
		if (invocations.containsKey(source)) {
			
			if (invocations.get(source).containsKey(destination)) {
				int numberOfArrows = invocations.get(source).get(destination);
				invocations.get(source).put(destination, numberOfArrows + 1);
			}
			
			else {
				methods.add(destination);
				invocations.get(source).put(destination, 1);
			}
		}
		
		else {
			methods.add(source);
			methods.add(destination);
			invocations.put(source, new HashMap<String, Integer>());
			invocations.get(source).put(destination, 1);
		}
	}
	
	public void addInvocation(String source, String destination, int occurrences) {
		methods.add(source);
		methods.add(destination);
		
		if (!invocations.containsKey(source))
			invocations.put(source, new HashMap<String, Integer>());
		
		invocations.get(source).put(destination, occurrences);
	}
	
	public void addInvocations(Map<String, Map<String, Integer>> map) {
		for (String source: map.keySet())
			for (String destination: map.get(source).keySet())
				this.addInvocation(source, destination, map.get(source).get(destination));
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Static Call Graph");
		builder.append("\nMethods: "+methods.size()+".");
		builder.append("\nInvocations: "+getNbInvocations()+".");
		builder.append("\n");
		
		for (String source: invocations.keySet()) {
			builder.append(source + ":\n");
			
			for (String destination: invocations.get(source).keySet())
				builder.append("\t---> " + destination + 
						" (" + invocations.get(source).get(destination) + " time(s))\n");
			builder.append("\n");
		}
		
		return builder.toString();
	}
	
	public void print() {
		loggerChain.log(new LogRequest(this.toString(), 
				StandardLogRequestLevel.INFO));
	}
	
	public void log() {
		loggerChain.setFilePath(parser.getProjectPath()+"static-callgraph.info");
		loggerChain.log(new LogRequest(this.toString(), 
				StandardLogRequestLevel.DEBUG));
	}
}
