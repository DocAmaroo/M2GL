package loggers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * a FileLogger concrete class that plays the role of
 * ConcreteHandler in the Chain of Responsibility Design pattern.<br/>
 * It provides an interface for all file loggers.
 * @author anonbnr
 *
 */
public class FileLogger extends Logger {
	
	/* ATTRIBUTES */
	/**
	 * This FileLogger's associated file path.
	 */
	protected String filePath = "errors.log";

	/* CONSTRUCTORS */
	/**
	 * Creates a FileLogger with an associated request level
	 * level.
	 * @param level The log request level associated with the
	 * FileLogger to create.
	 */
	public FileLogger(LogRequestLevel level) {
		super(level);
	}
	
	/**
	 * Creates a FileLogger that logs in a file having a path <code>filePath</code>
	 * with an associated request level <code>level</code>.
	 * @param filePath The path of the file in which the FileLogger to create
	 * will log
	 * @param level THe log request level associated with the FileLogger to create
	 */
	public FileLogger(String filePath, LogRequestLevel level) {
		super(level);
		this.filePath = filePath;
	}

	/* METHODS */
	/**
	 * Gets this FileLogger's associated file path.
	 * @return
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * Sets filePath as this FileLogger's associated file path. 
	 * @param filePath The value to set this FileLogger's 
	 * associated file path.
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * Appends request's message in this FileLogger's associated
	 * file.
	 */
	@Override
	protected void write(LogRequest request) {		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(filePath, true))) {
			writer.append(request.getMessage() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("File " + filePath + ":\n" 
				+ request.getMessage());
	}
}