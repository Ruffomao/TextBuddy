import java.io.*;
import java.util.*;

public class TextBuddy_v2 {

	/*
	 * VARIABLES
	 */
	private static boolean IS_ACCEPTING_COMMAND = true;
	private static Scanner sc = new Scanner(System.in);
	private static File file;
	private static FileWriter writer;
	private static BufferedWriter bufferedWriter;
	private static FileReader reader;
	private static BufferedReader bufferedReader;
	private static PrintWriter printWriter;
	private static ArrayList<String> sentencesArray;
	private static ArrayList<String> commandArray;
	// private static ArrayList<String> searchArray;

	/*
	 * MESSAGES
	 */
	private static final char MESSAGE_INVERTED_COMMAS = (char)34;
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

	private static void initiateProgram(String[] args) throws IOException {
		file = new File(args[0]);
		if (!file.exists()) {
			file.createNewFile();
		}
		printWelcome(args);
	}

	private static void runProgram() throws IOException {
		while (IS_ACCEPTING_COMMAND) {
			printCommandPrompt();
			executeCommand(parseCommand());
		}
	}

	/*
	 * Parses a user entered string into the command and its content
	 */

	private static ArrayList<String> parseCommand() {
		String userCommand = sc.nextLine();
		commandArray = new ArrayList<String>();
		commandArray.add(getOperation(userCommand));
		commandArray.add(getCommand(userCommand));
		return commandArray;
	}

	private static String getCommand(String userCommand) {
		return userCommand.replace(getOperation(userCommand), "").trim();
	}

	private static String getOperation(String userCommand) {
		return userCommand.trim().split("\\s++")[0];
	}

	private static void executeCommand(ArrayList<String> command) throws IOException {
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
		case SORT:
			operationSort();
			return;
		/*
		 * case SEARCH: operationSearch(); return;
		 */
		case EXIT:
			IS_ACCEPTING_COMMAND = false;
			return;
		default:
			operationInvalid();
			return;
		}
	}

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

	private static void operationInvalid() {
		printCommandInvalid();
	}

	private static void operationAdd(String operationContent) throws IOException {
		writeToFile(operationContent);
		printAddSuccess(operationContent);
	}

	private static void operationDisplay() throws IOException {
		int numOfLines = countNumOfLines();
		if (numOfLines > 0) {
			sentencesArray = new ArrayList<String>();
			printOrderedList(sentencesArray, numOfLines);
		} else {
			printDisplayEmpty();
		}
	}

	static void operationClear() throws IOException {
		clearAllContent();
		printClearSuccess();
	}

	private static void operationDelete(String content) throws IOException {
		int lineToDelete = new Integer(content);
		int numOfLines = countNumOfLines();
		if (lineToDelete > numOfLines) {
			printDeleteFail(lineToDelete);
		} else {
			String textToBeDeleted = deleteLine(lineToDelete, numOfLines);
			printDeleteSuccess(textToBeDeleted);
		}
	}

	private static void operationSort() throws IOException {
		sortFile();
		printSortSuccess();
	}

	/*
	 * OTHER METHODS
	 */

	private static void sortFile() throws IOException {
		sentencesArray = new ArrayList<String>();
		contentToArray(countNumOfLines());
		Collections.sort(sentencesArray);
		clearAllContent();
		arrayToFile();
	}

	private static String deleteLine(int lineToDelete, int numOfLines) throws IOException {
		String textToBeDeleted = getSpecificLine(lineToDelete, numOfLines);
		sentencesArray = new ArrayList<String>();
		contentToArray(numOfLines);
		clearAllContent();
		sentencesArray.remove(lineToDelete - 1);
		arrayToFile();
		return textToBeDeleted;
	}

	private static void clearAllContent() throws IOException {
		PrintWriter printWriter = new PrintWriter(file);
		printWriter.print("");
		printWriter.close();
	}

	private static void writeToFile(String content) throws IOException {
		PrintWriter printWriter = createWriter();
		printWriter.println(content);
		printWriter.close();
	}

	private static int countNumOfLines() throws IOException {
		BufferedReader bufferedReader = createReader();
		int numOfLines = 0;
		while (bufferedReader.readLine() != null) {
			numOfLines++;
		}
		bufferedReader.close();
		return numOfLines;
	}

	private static String getSpecificLine(int lineIndex, int numOfLines) throws IOException {
		sentencesArray = new ArrayList<String>();
		contentToArray(numOfLines);
		return sentencesArray.get(lineIndex - 1);
	}

	private static void contentToArray(int numOfLines) throws IOException {
		bufferedReader = createReader();
		for (int i = 0; i < numOfLines; i++) {
			sentencesArray.add(bufferedReader.readLine());
		}
		bufferedReader.close();
	}

	private static void arrayToFile() throws IOException {
		String stringToTransfer;
		for (int i = 0; i < sentencesArray.size(); i++) {
			stringToTransfer = sentencesArray.get(i);
			writeToFile(stringToTransfer);
		}
	}

	private static BufferedReader createReader() throws FileNotFoundException {
		reader = new FileReader(file);
		bufferedReader = new BufferedReader(reader);
		return bufferedReader;
	}

	private static PrintWriter createWriter() throws IOException {
		writer = new FileWriter(file, true);
		bufferedWriter = new BufferedWriter(writer);
		printWriter = new PrintWriter(bufferedWriter);
		return printWriter;
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

	private static void printDisplayEmpty() {
		System.out.println(file + MESSAGE_DISPLAY_EMPTY);
	}

	private static void printClearSuccess() {
		System.out.println(MESSAGE_CLEAR_SUCCESS + file);
	}

	private static void printDeleteFail(Integer lineToDelete) {
		System.out.println(MESSAGE_DELETE_FAIL_1 + lineToDelete + MESSAGE_DELETE_FAIL_2 + file);
	}

	private static void printDeleteSuccess(String textToBeDeleted) {
		System.out.println(MESSAGE_INVERTED_COMMAS + textToBeDeleted + MESSAGE_INVERTED_COMMAS + MESSAGE_DELETE_SUCCESS + file);
	}

	private static void printSortSuccess() {
		System.out.println(file + MESSAGE_SORT_SUCCESS);
	}

	private static void printOrderedList(ArrayList<String> stringArray, int numOfLines) throws IOException {
		contentToArray(numOfLines);
		int index = 1;
		for (int i = 0; i < numOfLines; i++) {
			System.out.print(index + ". ");
			System.out.print(stringArray.get(i));
			System.out.println();
			index++;
		}
	}
}
