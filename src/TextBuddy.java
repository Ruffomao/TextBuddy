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
	private static TextBuddy textBuddy;

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
	private static final String MESSAGE_SORT_SUCCESS = " has been sorted successfully";
	private static final String MESSAGE_SEARCH_FAIL = " not found in ";

	/*
	 * OPERATIONS
	 */
	enum OPERATION_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SORT, SEARCH
	};

	public static void main(String[] args) throws IOException {
		String fileName = args[0];
		textBuddy = new TextBuddy();
		File file = textBuddy.createFile(fileName);
		printWelcome(fileName);
		textBuddy.runProgram(file);
	}

	/*
	 * CONSTRUCTORS
	 */
	public TextBuddy() {
	}

	public File createFile(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	public void runProgram(File file) throws IOException {
		while (IS_ACCEPTING_COMMAND) {
			printCommandPrompt();
			executeCommand(file, parseCommand());
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
	public static void executeCommand(File file, ArrayList<String> command) throws IOException {
		OPERATION_TYPE operationType = getOperationType(command.get(0));
		String operationContent = command.get(1);

		switch (operationType) {
		case ADD:
			textBuddy.operationAdd(file, operationContent);
			return;
		case DISPLAY:
			textBuddy.operationDisplay(file);
			return;
		case CLEAR:
			textBuddy.operationClear(file);
			return;
		case DELETE:
			textBuddy.operationDelete(file, operationContent);
			return;
		case SORT:
			textBuddy.operationSort(file);
			return;
		case SEARCH:
			textBuddy.operationSearch(file, operationContent);
			return;
		case EXIT:
			IS_ACCEPTING_COMMAND = false;
			return;
		default:
			textBuddy.operationInvalid();
			return;
		}
	}

	/*
	 * Casts a string type into an OPERATION_TYPE
	 */
	private static OPERATION_TYPE getOperationType(String userOperation) {
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

	/*
	 * OPERATIONS
	 */
	public void operationInvalid() {
		printCommandInvalid();
	}

	public void operationAdd(File file, String operationContent) throws IOException {
		writeToFile(file, operationContent);
		printAddSuccess(file, operationContent);
	}

	public void operationDisplay(File file) throws IOException {
		int numOfLines = countNumOfLines(file);
		if (numOfLines > 0) {
			ArrayList<String> sentencesArray = new ArrayList<String>();
			sentencesArray = contentToArray(file, numOfLines);
			printOrderedList(sentencesArray);
		} else {
			printDisplayEmpty(file);
		}
	}

	public void operationClear(File file) throws IOException {
		clearAllContent(file);
		printClearSuccess(file);
	}

	public void operationDelete(File file, String content) throws IOException {
		int lineToDelete = new Integer(content);
		int numOfLines = countNumOfLines(file);
		if (lineToDelete > numOfLines) {
			printDeleteFail(file, lineToDelete);
		} else {
			String textToBeDeleted = deleteLine(file, lineToDelete, numOfLines);
			printDeleteSuccess(file, textToBeDeleted);
		}
	}

	public void operationSort(File file) throws IOException {
		ArrayList<String> contentArray = new ArrayList<String>();
		contentArray = contentToArray(file, countNumOfLines(file));
		contentArray = sortArray(contentArray);
		clearAllContent(file);
		arrayToFile(file, contentArray);
		printMessageSuccess(file);
	}

	public void operationSearch(File file, String stringToSearch) throws IOException {
		ArrayList<String> contentArray = new ArrayList<String>();
		contentArray = contentToArray(file, countNumOfLines(file));
		ArrayList<String> searchResults = new ArrayList<String>();
		searchResults = searchForWord(contentArray, stringToSearch);
		if (searchResults.size() > 0) {
			printOrderedList(searchResults);
		} else {
			printSearchFail(file, stringToSearch);
		}
	}

	/*
	 * Writes a string onto the file
	 * 
	 * @param content: the string which is to be written onto the file
	 */
	public static void writeToFile(File file, String content) throws IOException {
		PrintWriter printWriter = createWriter(file);
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
	public static String deleteLine(File file, int lineToDelete, int numOfLines) throws IOException {
		ArrayList<String> sentencesArray = new ArrayList<String>();
		sentencesArray = contentToArray(file, numOfLines);
		String textToBeDeleted = getSpecificLine(sentencesArray, lineToDelete);
		clearAllContent(file);
		sentencesArray.remove(lineToDelete - 1);
		arrayToFile(file, sentencesArray);
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
	public static void arrayToFile(File file, ArrayList<String> textArray) throws IOException {
		String stringToAdd;
		for (int i = 0; i < textArray.size(); i++) {
			stringToAdd = textArray.get(i);
			writeToFile(file, stringToAdd);
		}
	}

	// This method clears all the contents on a file
	public static void clearAllContent(File file) throws FileNotFoundException {
		PrintWriter printWriter = new PrintWriter(file);
		printWriter.print("");
		printWriter.close();
	}

	/*
	 * This method retrieves a specific string in the ArrayList
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
	public static int countNumOfLines(File file) throws IOException {
		int numberOfLines = 0;
		BufferedReader bufferedReader = createReader(file);
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
	public static ArrayList<String> contentToArray(File file, int numOfLines) throws IOException {
		BufferedReader bufferedReader = createReader(file);
		ArrayList<String> textArray = new ArrayList<String>();
		for (int i = 0; i < numOfLines; i++) {
			textArray.add(bufferedReader.readLine());
		}
		bufferedReader.close();
		return textArray;
	}

	/*
	 * Sorts the array in alphabetical order
	 * 
	 * @param contentArray: the array to be sorted
	 * 
	 * @return the sorted ArrayList
	 */
	public static ArrayList<String> sortArray(ArrayList<String> contentArray) {
		Collections.sort(contentArray);
		return contentArray;
	}

	private ArrayList<String> searchForWord(ArrayList<String> contentArray, String stringToSearch) {
		ArrayList<String> searchArray = new ArrayList<String>();
		for (String string : contentArray) {
			if (string.contains(stringToSearch)) {
				searchArray.add(string);
			}
		}
		searchArray = removeDuplicates(searchArray);
		searchArray = sortArray(searchArray);
		return searchArray;
	}

	private ArrayList<String> removeDuplicates(ArrayList<String> searchArray) {
		ArrayList<String> resultArray = new ArrayList<String>();
		HashSet<String> uniqueSet = new HashSet<String>();
		for (String uniqueString : searchArray) {
			if (!uniqueSet.contains(uniqueString)) {
				resultArray.add(uniqueString);
				uniqueSet.add(uniqueString);
			}
		}
		return resultArray;
	}

	/*
	 * Instantiates the FileReader and BufferedReader objects
	 */
	public static BufferedReader createReader(File file) throws FileNotFoundException {
		FileReader reader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(reader);
		return bufferedReader;
	}

	/*
	 * Instantiates the FileWriter, BufferedWriter and PrintWriter objects
	 */
	public static PrintWriter createWriter(File file) throws IOException {
		FileWriter writer = new FileWriter(file, true);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		PrintWriter printWriter = new PrintWriter(bufferedWriter);
		return printWriter;
	}

	/*
	 * PRINT METHODS
	 */
	private static void printWelcome(String content) {
		System.out.println(MESSAGE_WELCOME_1 + content + MESSAGE_WELCOME_2);
	}

	private static void printCommandPrompt() {
		System.out.print(MESSAGE_COMMAND_PROMPT);
	}

	private static void printCommandInvalid() {
		System.out.println(MESSAGE_INVALID);
	}

	private static void printAddSuccess(File file, String content) {
		System.out.println(MESSAGE_INVERTED_COMMAS + content + MESSAGE_INVERTED_COMMAS + MESSAGE_ADD_SUCCESS + file);
	}

	private static void printClearSuccess(File file) {
		System.out.println(MESSAGE_CLEAR_SUCCESS + file);
	}

	private static void printDeleteFail(File file, Integer lineToDelete) {
		System.out.println(MESSAGE_DELETE_FAIL_1 + lineToDelete + MESSAGE_DELETE_FAIL_2 + file);
	}

	private static void printDeleteSuccess(File file, String textToBeDeleted) {
		System.out.println(
				MESSAGE_INVERTED_COMMAS + textToBeDeleted + MESSAGE_INVERTED_COMMAS + MESSAGE_DELETE_SUCCESS + file);
	}

	private void printMessageSuccess(File file) {
		System.out.println(file + MESSAGE_SORT_SUCCESS);
	}

	private void printSearchFail(File file, String stringToSearch) {
		System.out.println(MESSAGE_INVERTED_COMMAS + stringToSearch + MESSAGE_INVERTED_COMMAS + MESSAGE_SEARCH_FAIL
				+ MESSAGE_INVERTED_COMMAS + file + MESSAGE_INVERTED_COMMAS);
	}

	private static void printDisplayEmpty(File file) {
		System.out.println(file + MESSAGE_DISPLAY_EMPTY);
	}

	private static void printOrderedList(ArrayList<String> stringArray) {
		int index = 1;
		for (int i = 0; i < stringArray.size(); i++) {
			System.out.print(index + ". ");
			System.out.print(stringArray.get(i));
			System.out.println();
			index++;
		}
	}
}