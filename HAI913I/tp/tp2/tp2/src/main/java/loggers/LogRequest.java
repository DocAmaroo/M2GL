package loggers;

/**
 * a LogRequest class that factors the common state
 * and default behavior of all log requests handled by loggers.
 * It associates a log request to level and a message. The
 * level is defined by the marker interface LogRequestLevel,
 * whereas the message consists of a simple String.
 * @author anonbnr
 * @see ILogger
 */
public class LogRequest {
	
	/* ATTRIBUTES */
	/**
	 * This LogRequest's message.
	 */
	protected String message;
	
	/**
	 * This LogRequest's level.
	 */
	protected LogRequestLevel level;
	
	/* CONSTRUCTORS */
	/**
	 * Creates a LogRequest having a message message and a level
	 * level.
	 * @param message The message of the LogRequest to create.
	 * @param level The log request level of the LogRequest to create.
	 */
	public LogRequest(String message, LogRequestLevel level) {
		this.message = message;
		this.level = level;
	}
	
	/* METHODS */
	/**
	 * Gets this LogRequest's message.
	 * @return this LogRequest's message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets message as this LogRequest's message.
	 * @param message The value to set this LogRequest's message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets this LogRequest's level.
	 * @return this LogRequest's level.
	 */
	public LogRequestLevel getLevel() {
		return level;
	}

	/**
	 * Sets level as this LogRequest's level.
	 * @param level The value to set this LogRequest's level.
	 */
	public void setLevel(LogRequestLevel level) {
		this.level = level;
	}
}