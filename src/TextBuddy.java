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
		/*case CLEAR:
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
	
	private static void writeToFile(String content) throws IOException {
		PrintWriter printWriter = createWriter();
		printWriter.println(content);
		printWriter.close();
	}

	/*
	 * This method executes different actions based on the user's input ADD:
	 * appends a string to the end of the file beginning on a new line CLEAR:
	 * clears all the contents within the file DISPLAY: prints a numbered list
	 * of the contents within the file DELETE: deletes a specified line of text
	 * within the file EXIT: terminates the program on the command line
	 * 
	 * @param file
	 */
	/*private static void executeCommands(File file) throws IOException, FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		while (IS_ACCEPTING_COMMAND) {
			String userCommand = askForUserCommand(sc);

			if (userCommand.equalsIgnoreCase("exit")) {
				IS_ACCEPTING_COMMAND = false;

			} else if (userCommand.equalsIgnoreCase("add")) {
				String userInputString = askForUserInput(sc);
				addStringToFile(file, userInputString);
				printAddAction(file, userInputString);

			} else if (userCommand.equalsIgnoreCase("display")) {
				BufferedReader br = readFile(file);
				int numOfLines = countNumOfLines(file);
				displayContent(file, br, numOfLines);
				br.close();

			} else if (userCommand.equalsIgnoreCase("clear")) {
				clearAllContent(file);
				printClearAction(file);

			} else if (userCommand.equalsIgnoreCase("delete")) {
				int lineToBeDeleted = sc.nextInt();
				int numOfLines = countNumOfLines(file);
				BufferedReader br = readFile(file);

				if (lineToBeDeleted > numOfLines) {
					printDeleteFailedAction(file, lineToBeDeleted);
				} else {
					// Find the string to be deleted
					String textToBeDeleted = getSpecificLine(file, lineToBeDeleted);
					ArrayList<String> textArray = contentToArrayList(numOfLines, br);
					clearAllContent(file);
					// removes the line to be deleted and copies the contents of
					// the ArrayList back onto the file
					textArray.remove(lineToBeDeleted - 1);
					arrayListToFile(file, textArray);
					printDeleteAction(file, textToBeDeleted);
					br.close();
				}

			} else {
				printNonValidAction();
			}
		}
		sc.close();
	}*/

	/*
	 * This method copies the contents in the given arrayList onto the file.
	 * Contents are copied in the order they are stored on the arrayList.
	 * 
	 * @param file: the target file 
	 * 		  textArray: the arrayList object used in storing the contents of the file
	 * 
	 */
	private static void arrayListToFile(File file, ArrayList<String> textArray) throws IOException {
		String stringToAdd;
		for (int i = 0; i < textArray.size(); i++) {
			stringToAdd = textArray.get(i);
			addStringToFile(file, stringToAdd);
		}
	}

	// This method scans for the user's input and returns it as a string
	private static String askForUserInput(Scanner sc) {
		String userInputString = sc.nextLine();
		return userInputString;
	}

	// This method asks for the user's command and returns the input as a string
	private static String askForUserCommand(Scanner sc) {
		System.out.print("command: ");
		String userCommand = sc.next();
		return userCommand;
	}

	/* This method appends a string to the end of a file beginning on a new line
	 * 
	 * @param file: the target file
	 * 		  inputString: the string which the user wants added to the file
	 */
	private static void addStringToFile(File file, String inputString) throws IOException {
		FileWriter fw = new FileWriter(file, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		pw.println(inputString.trim());
		pw.close();
	}

	// This method clears all the contents on a file
	private static void clearAllContent(File file) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(file);
		pw.print("");
		pw.close();
	}

	/* This method retrieves the string on a specific line in the file
	 * 
	 * @param file: the target file
	 * 		  lineToBeDeleted: the line of text which the user wants deleted from the file
	 * 
	 * @return the string in that line
	 */
	private static String getSpecificLine(File file, int lineToBeDeleted) throws FileNotFoundException, IOException {
		BufferedReader br = readFile(file);
		// Assign a temporary variable to be used for counting
		int lineIndex = lineToBeDeleted;
		String textToBeDeleted = null;
		while (lineIndex > 0) {
			textToBeDeleted = br.readLine();
			lineIndex--;
		}
		br.close();
		return textToBeDeleted;
	}

	/* This method counts the number of lines of text in a file and returns it as an integer
	 * 
	 * @param file: the target file
	 * 
	 * @return the number of lines in the file
	 */
	private static int countNumOfLines() throws IOException {
		int numberOfLines = 0;
		BufferedReader bufferedReader = createReader();
		while (bufferedReader.readLine() != null) {
			numberOfLines++;
		}
		bufferedReader.close();
		return numberOfLines;
	}
	
	private static void contentToArray(int numOfLines) throws IOException {
		bufferedReader = createReader();
		for (int i = 0; i < numOfLines; i++) {
			sentencesArray.add(bufferedReader.readLine());
		}
		bufferedReader.close();
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