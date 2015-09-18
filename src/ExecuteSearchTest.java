import static org.junit.Assert.*;

import java.io.*;

import org.junit.Test;

public class ExecuteSearchTest {

	@Test
	public void testOperationSearch() throws IOException {
		File testFile = new File("test.txt");
		TextBuddy.clearAllContent(testFile);
		TextBuddy.writeToFile(testFile, "A B C");
		TextBuddy.writeToFile(testFile, "D E F");
		TextBuddy.writeToFile(testFile, "X Y Z");
		TextBuddy test = new TextBuddy();
		
		test.operationSearch(testFile, "A");
		
		ByteArrayOutputStream resultContent = new ByteArrayOutputStream();
		
		System.setOut(new PrintStream(resultContent));
		assertEquals("1. A B C", resultContent.toString());
		
	}

}
