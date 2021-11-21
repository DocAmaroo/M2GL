package loggers;

/**
 * an ErrorLogger concrete class that plays the role of
 * ConcreteHandler in the Chain of Responsibility Design pattern.<br/>
 * It provides an interface for all error loggers.
 * @author anonbnr
 *
 */
public class ErrorLogger extends Logger {

	/* CONSTRUCTORS */
	/**
	 * Creates an ErrorLogger with an associated request level
	 * level.
	 * @param level The log request level associated with the
	 * ErrorLogger to create.
	 */
	public ErrorLogger(LogRequestLevel level) {
		super(level);
	}

	/* METHODS */
	/**
	 * Writes request's message in the standard error console.
	 * In practice, it writes it in the standard console to avoid
	 * display issues between both consoles.
	 */
	@Override
	protected void write(LogRequest request) {
		System.err.println(request.getMessage());
	}
}