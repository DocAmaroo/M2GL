package loggers;

/**
 * a LogRequestLevel marker interface that is used as a parent
 * to all LogRequestLevel enumerations.
 * @author anonbnr
 *
 */
public interface LogRequestLevel {
	
	/**
	 * Gets the integer value of this LogRequestLevel.
	 * @return the integer value of this LogRequestLevel.
	 */
	int value();
}