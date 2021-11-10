package qengine.program.logs;

import java.io.*;

public class Log {

    /**
     * Output configuration
     */
    private static final String FOLDER = "output";
    private static final String FILENAME = "logs";
    private static final String FILE_EXTENSION = "txt";


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
    public static long EXEC_TIME_DICTIONARY = -1;

    /**
     * The execution time for te indexation instantiation, by default is -1
     */
    public static long EXEC_TIME_INDEXATION = -1;

    /**
     * Default value if the data value is not available
     */
    public static String UNAVAILABLE = "NOT_AVAILABLE";


    public static void setExecTimeDictionary(long execTimeDictionary) {
        EXEC_TIME_DICTIONARY = execTimeDictionary;
        System.out.println(toStringExecTimeDictionary());
    }

    public static void setExecTimeIndexation(long execTimeIndexation) {
        EXEC_TIME_DICTIONARY = execTimeIndexation;
        System.out.println(toStringExecTimeIndexation());
    }

    public static String toStringExecTimeDictionary() {
        return "[i] EXECUTION TIME | dictionary: " +
                (EXEC_TIME_DICTIONARY != -1 ? EXEC_TIME_DICTIONARY : UNAVAILABLE) + "ms";
    }

    public static String toStringExecTimeIndexation() {
        return "[i] EXECUTION TIME | indexation: " +
                (EXEC_TIME_INDEXATION != -1 ? EXEC_TIME_INDEXATION : UNAVAILABLE) + "ms";
    }

    // ----------------------------------------------------------------------

    /**
     * Write all the logs to output
     * @throws IOException
     */
    public static void writeOutput() throws IOException {
        System.out.println("# Logs ----------------------------------------");

        System.out.println(toStringExecTimeDictionary());
        OUTPUT_FILE.println(toStringExecTimeDictionary());

        System.out.println("[+] output successfully saved on: " + getOutputPath());
        closeFileBuffer();
    }

    /**
     * Close properly all file reader
     * @throws IOException
     */
    public static void closeFileBuffer() throws IOException {
        OUTPUT_FILE.close();
        BUFFERED_WRITER.close();
        FILE_WRITER.close();
    }
}
