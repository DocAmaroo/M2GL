package qengine.program.logs;

import qengine.program.models.ExecutionTime;

import java.io.*;

public class Log {

    /**
     * Output configuration
     */
    private static final String FOLDER = "output";
    private static final String FILENAME = "logs";
    private static final String FILE_EXTENSION = "csv";


    /**
     * Return the relative path to the log file
     */
    public static String getOutputPath() {
        return FOLDER + "/" + FILENAME + "." + FILE_EXTENSION;
    }

    /**
     * Init file|buffer|print writer
     */
    static FileWriter FILE_WRITER;
    static {
        try {
            // First create the output folder if it doesn't exist
            File file = new File(FOLDER);
            if (!file.exists()) {

                // if an error occurred, else the folder has been created
                if (!file.mkdirs()) System.out.println("[!] Cannot created output folder");

            } else {

                // Verification done, we can initialize our file writer
                FILE_WRITER = new FileWriter(getOutputPath());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static BufferedWriter BUFFERED_WRITER = new BufferedWriter(FILE_WRITER);
    static PrintWriter OUTPUT_FILE = new PrintWriter(BUFFERED_WRITER);

    /**
     * The execution time for te dictionary instantiation, by default is -1
     */
    public static ExecutionTime EXEC_TIME_DICTIONARY = new ExecutionTime("Dictionary");;

    /**
     * The execution time for te indexation instantiation, by default is -1
     */
    public static ExecutionTime EXEC_TIME_INDEXATION = new ExecutionTime("Indexation");

    /**
     * Default value if the data value is not available
     */
    public static String UNAVAILABLE = "NOT_AVAILABLE";


    public static void setExecTimeDictionary(long execTimeDictionary) {
        EXEC_TIME_DICTIONARY.setValue(execTimeDictionary);
    }

    public static void setExecTimeIndexation(long execTimeIndexation) {
        EXEC_TIME_INDEXATION.setValue(execTimeIndexation);
    }
    // ----------------------------------------------------------------------

    /**
     * Save all the logs to the output file
     */
    public static void save() throws IOException {
        displayAllLogs();

        OUTPUT_FILE.println(csvHeader());
        OUTPUT_FILE.println(EXEC_TIME_DICTIONARY.toCSV());
        OUTPUT_FILE.println(EXEC_TIME_INDEXATION.toCSV());

        System.out.println("\n[+] Logs have been successfully saved on: " + getOutputPath());
        closeFileBuffer();
    }

    public static String csvHeader() {
        return "Type,Key,Value";
    }

    /**
     * Display all the logs on the console
     */
    public static void displayAllLogs() {
        System.out.println("\n\n# LOGS ----------------------------------------");
        System.out.println(EXEC_TIME_DICTIONARY);
        System.out.println(EXEC_TIME_INDEXATION);
    }

    /**
     * Close properly all file reader
     */
    public static void closeFileBuffer() throws IOException {
        OUTPUT_FILE.close();
        BUFFERED_WRITER.close();
        FILE_WRITER.close();
    }
}
