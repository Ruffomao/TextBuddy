import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class ExecuteSortTest {

	@Test
	public void testOperationSort1() throws IOException {
		File testFile = new File("test.txt");
		TextBuddy.clearAllContent(testFile);
		TextBuddy.writeToFile(testFile, "d");
		TextBuddy.writeToFile(testFile, "c");
		TextBuddy.writeToFile(testFile, "b");
		TextBuddy.writeToFile(testFile, "a");
		TextBuddy test = new TextBuddy();
		
		test.operationSort(testFile);
		
		ArrayList<String> contentArray = new ArrayList<String>();
		contentArray = TextBuddy.contentToArray(testFile, TextBuddy.countNumOfLines(testFile));
		
		Object[] resultArray = new String[contentArray.size()];
		resultArray = contentArray.toArray();
		String[] expectedArray = {"a", "b", "c", "d"};
		
		assertArrayEquals(expectedArray, resultArray);
	}
	
	@Test
	public void testOperationSort2() throws IOException {
		File testFile = new File("test.txt");
		TextBuddy.clearAllContent(testFile);
		TextBuddy.writeToFile(testFile, "e");
		TextBuddy.writeToFile(testFile, "c");
		TextBuddy.writeToFile(testFile, "z");
		TextBuddy.writeToFile(testFile, "a");
		TextBuddy test = new TextBuddy();
		
		test.operationSort(testFile);
		
		ArrayList<String> contentArray = new ArrayList<String>();
		contentArray = TextBuddy.contentToArray(testFile, TextBuddy.countNumOfLines(testFile));
		
		Object[] resultArray = new String[contentArray.size()];
		resultArray = contentArray.toArray();
		String[] expectedArray = {"a", "c", "e", "z"};
		
		assertArrayEquals(expectedArray, resultArray);
	}

	@Test
	public void testSortArray() throws IOException {
		ArrayList<String> contentArray = new ArrayList<String>();
		contentArray.add("d");
		contentArray.add("c");
		contentArray.add("b");
		contentArray.add("a");
		contentArray = TextBuddy.sortArray(contentArray);
		
		Object[] resultArray = new String[contentArray.size()];
		resultArray = contentArray.toArray();
		String[] expectedArray = {"a", "b", "c", "d"};
		
		assertArrayEquals(expectedArray, resultArray);
	}

}
