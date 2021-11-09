package loggers;

/**
 * an ILogger interface that plays the behavior role of Handler
 * in the Chain of Responsibility Design pattern.<br/>
 * It provides an interface for a logger that must either handle
 * a logging request, if it can, or forward it to its successor
 * in the chain of responsibility;
 * @author anonbnr
 *
 */
public interface ILogger {
	/* METHODS */
	/**
	 * Sets nextLogger as this logger's successor in the
	 * chain of responsibility.
	 * @param nextLogger The ILogger successor of this
	 * ILogger in the chain of responsibility.
	 */
	void setNextLogger(ILogger nextLogger);
	
	/**
	 * Logs request if it's responsible for handling it,
	 * or forwards it to its ILogger successor in the chain
	 * of responsibility otherwise.
	 * @param request The request to handle by this ILogger.
	 */
	void log(LogRequest request);
}