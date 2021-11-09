package processors;

public abstract class Processor<T> {
	/* ATTRIBUTES */
	protected T parser;
	
	/* CONSTRUCTOR */
	public Processor(String projectPath) {
		setParser(projectPath);
	}
	
	/* METHODS */
	public T getParser() {
		return parser;
	}
	
	public abstract void setParser(String projectPath);
}
