import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * This class allows user to create text files (.txt) and manipulate the contents within them on the command line.
 * Four basic operations are provided by this class to be used in manipulating a file's contents.
 * It is assumed that the name of the file is indicated immediately after the execution of the .class file
 */

public class TextBuddy {

	private static boolean IS_ACCEPTING_COMMAND = true;

	public static void main(String[] args) throws IOException {
		String fileName = args[0];
		File file = createFile(fileName);
		executeCommands(file);

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
	private static void executeCommands(File file) throws IOException, FileNotFoundException {
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
	}

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

	/* This method copies the contents on a file into an arrayList.
	 * 
	 * @param numOfLines: the number of lines in the file
	 * 		  br: BufferedReader object name
	 * 
	 * @return an arrayList holding the contents of the target file
	 */
	private static ArrayList<String> contentToArrayList(int numOfLines, BufferedReader br) throws IOException {
		ArrayList<String> textArray = new ArrayList<String>();
		for (int i = 0; i < numOfLines; i++) {
			textArray.add(br.readLine());
		}
		return textArray;
	}

	// Prints a message when the user has input an invalid command
	private static void printNonValidAction() {
		System.out.println("please input a valid command: ADD, DISPLAY, DELETE, CLEAR");
	}

	/* Prints a message when a line of text has been successfully deleted
	 * 
	 * @param file: the target file
	 * 		  textToBeDeleted: the string which is to be deleted
	 */
	private static void printDeleteAction(File file, String textToBeDeleted) {
		System.out.println("deleted from " + file + ": " + (char) 34 + textToBeDeleted + (char) 34);
	}

	/* Prints a message when a line of text cannot be deleted
	 * 
	 * @param file: the target file
	 * 		  lineToBeDeleted: the line number which the user wants to be deleted
	 */
	private static void printDeleteFailedAction(File file, int lineToBeDeleted) {
		System.out.println("unable to delete line " + lineToBeDeleted + " from " + file);
	}

	/* Prints a message when all contents in a file has been successfully
	 * deleted
	 * 
	 * @param file: the target file
	 */
	private static void printClearAction(File file) {
		System.out.println("all content deleted from " + file);
	}

	/* Prints a message when a string has been successfully appended onto the 
	 * file
	 * 
	 * @param file: the target file
	 * 		  userInputString: the string which the user wants added to the file
	 */
	private static void printAddAction(File file, String userInputString) {
		System.out.println("added to " + file + ": " + (char) 34 + userInputString.trim() + (char) 34);
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

	/* This method prints the contents within the file in a numbered list
	 * 
	 * @param file: the target file
	 * 		  br: BufferedReader object name
	 * 		  numOfLines: the number of lines of text in a file
	 */
	private static void displayContent(File file, BufferedReader br, int numOfLines) throws IOException {
		if (numOfLines > 0) {
			for (int i = 1; i <= numOfLines; i++) {
				System.out.print(i + ". ");
				printLine(br);
				System.out.println("");
			}
		} else {
			System.out.println(file + " is empty");
		}
	}

	// This method prints a string within the file, beginning from the top
	private static void printLine(BufferedReader br) throws IOException {
		String lineToPrint = br.readLine();
		System.out.print(lineToPrint);
	}

	// This method prepares a file to be read
	private static BufferedReader readFile(File file) throws FileNotFoundException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		return br;
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

	/* This method creates a new file if a file with a similar name has not been created yet
	 * 
	 * @param fileName: the name of the file the user wants to create
	 * 
	 * @return returns a newly created file or an existing file if the name specified already exists
	 */
	private static File createFile(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		System.out.println("Welcome to Textbuddy. " + file + " is ready for use");
		return file;
	}

	/* This method counts the number of lines of text in a file and returns it as an integer
	 * 
	 * @param file: the target file
	 * 
	 * @return the number of lines in the file
	 */
	private static int countNumOfLines(File file) throws IOException {
		int numberOfLines = 0;
		BufferedReader br = readFile(file);
		while (br.readLine() != null) {
			numberOfLines++;
		}
		br.close();
		return numberOfLines;
	}

}