package loggers;

/**
 * a ConsoleLogger concrete class that plays the role of
 * ConcreteHandler in the Chain of Responsibility Design pattern.<br/>
 * It provides an interface for all console loggers.
 * @author anonbnr
 *
 */
public class ConsoleLogger extends Logger {

	/* CONSTRUCTORS */
	/**
	 * Creates a ConsoleLogger with an associated request level
	 * level.
	 * @param level The log request level associated with the
	 * ConsoleLogger to create.
	 */
	public ConsoleLogger(LogRequestLevel level) {
		super(level);
	}
	
	/* METHODS */
	/**
	 * Writes request's message in the standard console.
	 */
	@Override
	protected void write(LogRequest request) {
		System.out.println(request.getMessage());
	}
}