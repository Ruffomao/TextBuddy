import java.io.*;
import java.util.*;

/*
 * This class allows user to create text files (.txt) and manipulate the contents within them on the command line.
 * Four basic operations are provided by this class to be used in manipulating a file's contents.
 * It is assumed that the name of the file is indicated immediately after the execution of the .class file
 */

public class TextBuddy {

	private static boolean IS_ACCEPTING_COMMAND = true;
	private static Scanner sc = new Scanner(System.in);
	private static File file;
	private static FileWriter writer;
	private static BufferedWriter bufferedWriter;
	private static FileReader reader;
	private static BufferedReader bufferedReader;
	private static PrintWriter printWriter;

	/*
	 * MESSAGES
	 */
	private static final char MESSAGE_INVERTED_COMMAS = (char) 34;
	private static final String MESSAGE_WELCOME_1 = "Welcome to TextBuddy++. ";
	private static final String MESSAGE_WELCOME_2 = " is ready for use";
	private static final String MESSAGE_COMMAND_PROMPT = "command: ";
	private static final String MESSAGE_INVALID = "Invalid command. Please enter a valid command";
	private static final String MESSAGE_ADD_SUCCESS = " successfully added to ";
	private static final String MESSAGE_DISPLAY_EMPTY = " is empty";
	private static final String MESSAGE_CLEAR_SUCCESS = "All content cleared from ";
	private static final String MESSAGE_DELETE_FAIL_1 = "Failed to delete line ";
	private static final String MESSAGE_DELETE_FAIL_2 = " from ";
	private static final String MESSAGE_DELETE_SUCCESS = " successfully deleted from ";
	//private static final String MESSAGE_SORT_SUCCESS = " has been sorted successfully";

	/*
	 * OPERATIONS
	 */
	enum OPERATION_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SORT, SEARCH
	};

	public static void main(String[] args) throws IOException {
		initiateProgram(args);
		runProgram();
	}

	public static void initiateProgram(String[] args) throws IOException {
		file = new File(args[0]);
		if (!file.exists()) {
			file.createNewFile();
		}
		printWelcome(args);
	}

	public static void runProgram() throws IOException {
		while (IS_ACCEPTING_COMMAND) {
			printCommandPrompt();
			executeCommand(parseCommand());
		}
	}

	/*
	 * Parses the user entered string and returns an ArrayList<String>
	 * containing the command and the relevant contents
	 */
	public static ArrayList<String> parseCommand() {
		String userCommand = sc.nextLine();
		ArrayList<String> commandArray = new ArrayList<String>();
		commandArray.add(getOperation(userCommand));
		commandArray.add(getCommand(userCommand));
		return commandArray;
	}

	public static String getCommand(String userCommand) {
		return userCommand.replace(getOperation(userCommand), "").trim();
	}

	public static String getOperation(String userCommand) {
		return userCommand.trim().split("\\s++")[0];
	}

	/*
	 * Executes the relevant methods depending on the command entered by the
	 * user
	 * 
	 * @param command: An ArrayList containing the command and its relevant
	 * content
	 */
	public static void executeCommand(ArrayList<String> command) throws IOException {
		OPERATION_TYPE operationType = getOperationType(command.get(0));
		String operationContent = command.get(1);

		switch (operationType) {
		case ADD:
			operationAdd(operationContent);
			return;
		case DISPLAY:
			operationDisplay();
			return;
		case CLEAR:
			operationClear();
			return;
		case DELETE:
			operationDelete(operationContent);
			return;
		/*
		 * case SORT: operationSort(); return; /* case SEARCH:
		 * operationSearch(); return;
		 */
		case EXIT:
			IS_ACCEPTING_COMMAND = false;
			return;
		default:
			operationInvalid();
			return;
		}
	}

	/*
	 * Casts a string type into an OPERATION_TYPE
	 */
	public static OPERATION_TYPE getOperationType(String userOperation) {
		if (userOperation.equalsIgnoreCase("add")) {
			return OPERATION_TYPE.ADD;
		} else if (userOperation.equalsIgnoreCase("display")) {
			return OPERATION_TYPE.DISPLAY;
		} else if (userOperation.equalsIgnoreCase("delete")) {
			return OPERATION_TYPE.DELETE;
		} else if (userOperation.equalsIgnoreCase("clear")) {
			return OPERATION_TYPE.CLEAR;
		} else if (userOperation.equalsIgnoreCase("exit")) {
			return OPERATION_TYPE.EXIT;
		} else if (userOperation.equalsIgnoreCase("sort")) {
			return OPERATION_TYPE.SORT;
		} else if (userOperation.equalsIgnoreCase("search")) {
			return OPERATION_TYPE.SEARCH;
		} else {
			return OPERATION_TYPE.INVALID;
		}
	}

	public static void operationInvalid() {
		printCommandInvalid();
	}

	public static void operationAdd(String operationContent) throws IOException {
		writeToFile(operationContent);
		printAddSuccess(operationContent);
	}

	public static void operationDisplay() throws IOException {
		int numOfLines = countNumOfLines();
		if (numOfLines > 0) {
			ArrayList<String> sentencesArray = new ArrayList<String>();
			printOrderedList(sentencesArray, numOfLines);
		} else {
			printDisplayEmpty();
		}
	}

	public static void operationClear() throws IOException {
		clearAllContent();
		printClearSuccess();
	}

	public static void operationDelete(String content) throws IOException {
		int lineToDelete = new Integer(content);
		int numOfLines = countNumOfLines();
		if (lineToDelete > numOfLines) {
			printDeleteFail(lineToDelete);
		} else {
			String textToBeDeleted = deleteLine(lineToDelete, numOfLines);
			printDeleteSuccess(textToBeDeleted);
		}
	}

	/*
	 * Writes a string onto the file
	 * 
	 * @param content: the string which is to be written onto the file
	 */
	public static void writeToFile(String content) throws IOException {
		createWriter();
		printWriter.println(content);
		printWriter.close();
	}

	/*
	 * Deletes a single line of text on the file
	 * 
	 * @param lineToDelete: the line which is to be deleted
	 * 
	 * @param numOfLines: the number of lines on the file
	 * 
	 * @return the string which has been deleted
	 */
	public static String deleteLine(int lineToDelete, int numOfLines) throws IOException {
		ArrayList<String> sentencesArray = new ArrayList<String>();
		sentencesArray = contentToArray(numOfLines);
		String textToBeDeleted = getSpecificLine(sentencesArray, lineToDelete);
		clearAllContent();
		sentencesArray.remove(lineToDelete - 1);
		arrayToFile(sentencesArray);
		return textToBeDeleted;
	}

	/*
	 * This method copies the contents in the given arrayList onto the file.
	 * Contents are copied in the order they are stored on the arrayList.
	 * 
	 * @param file: the target file textArray: the arrayList object used in
	 * storing the contents of the file
	 * 
	 */
	public static void arrayToFile(ArrayList<String> textArray) throws IOException {
		String stringToAdd;
		for (int i = 0; i < textArray.size(); i++) {
			stringToAdd = textArray.get(i);
			writeToFile(stringToAdd);
		}
	}

	// This method clears all the contents on a file
	public static void clearAllContent() throws FileNotFoundException {
		printWriter = new PrintWriter(file);
		printWriter.print("");
		printWriter.close();
	}

	/*
	 * This method retrieves the string on a specific line in the file
	 * 
	 * @param textArray: the ArrayList which contains the line of text to be
	 * deleted
	 * 
	 * @param lineIndex: the line of text which the user wants deleted from the
	 * file
	 * 
	 * @return the line of text
	 */
	public static String getSpecificLine(ArrayList<String> textArray, int lineIndex)
			throws FileNotFoundException, IOException {
		return textArray.get(lineIndex - 1);
	}

	/*
	 * This method counts the number of lines of text in a file and returns it
	 * as an integer
	 * 
	 * @param file: the target file
	 * 
	 * @return the number of lines in the file
	 */
	public static int countNumOfLines() throws IOException {
		int numberOfLines = 0;
		createReader();
		while (bufferedReader.readLine() != null) {
			numberOfLines++;
		}
		bufferedReader.close();
		return numberOfLines;
	}

	/*
	 * Copies the content on the file onto an ArrayList<String>. Each line of
	 * text will occupy an index
	 * 
	 * @param numOfLines: the number of lines in the file
	 * 
	 * @return an ArrayList<String> with the contents of the file
	 */
	public static ArrayList<String> contentToArray(int numOfLines) throws IOException {
		createReader();
		ArrayList<String> textArray = new ArrayList<String>();
		for (int i = 0; i < numOfLines; i++) {
			textArray.add(bufferedReader.readLine());
		}
		bufferedReader.close();
		return textArray;
	}

	/*
	 * Instantiates the FileReader and BufferedReader objects
	 */
	public static void createReader() throws FileNotFoundException {
		reader = new FileReader(file);
		bufferedReader = new BufferedReader(reader);
	}

	/*
	 * Instantiates the FileWriter, BufferedWriter and PrintWriter objects
	 */
	public static void createWriter() throws IOException {
		writer = new FileWriter(file, true);
		bufferedWriter = new BufferedWriter(writer);
		printWriter = new PrintWriter(bufferedWriter);
	}

	/*
	 * PRINT METHODS
	 */
	private static void printWelcome(String[] args) {
		System.out.println(MESSAGE_WELCOME_1 + args[0] + MESSAGE_WELCOME_2);
	}

	private static void printCommandPrompt() {
		System.out.print(MESSAGE_COMMAND_PROMPT);
	}

	private static void printCommandInvalid() {
		System.out.println(MESSAGE_INVALID);
	}

	private static void printAddSuccess(String content) {
		System.out.println(MESSAGE_INVERTED_COMMAS + content + MESSAGE_INVERTED_COMMAS + MESSAGE_ADD_SUCCESS + file);
	}

	private static void printClearSuccess() {
		System.out.println(MESSAGE_CLEAR_SUCCESS + file);
	}

	private static void printDeleteFail(Integer lineToDelete) {
		System.out.println(MESSAGE_DELETE_FAIL_1 + lineToDelete + MESSAGE_DELETE_FAIL_2 + file);
	}

	private static void printDeleteSuccess(String textToBeDeleted) {
		System.out.println(
				MESSAGE_INVERTED_COMMAS + textToBeDeleted + MESSAGE_INVERTED_COMMAS + MESSAGE_DELETE_SUCCESS + file);
	}

	private static void printDisplayEmpty() {
		System.out.println(file + MESSAGE_DISPLAY_EMPTY);
	}

	private static void printOrderedList(ArrayList<String> stringArray, int numOfLines) throws IOException {
		stringArray = contentToArray(numOfLines);
		int index = 1;
		for (int i = 0; i < numOfLines; i++) {
			System.out.print(index + ". ");
			System.out.print(stringArray.get(i));
			System.out.println();
			index++;
		}
	}
}