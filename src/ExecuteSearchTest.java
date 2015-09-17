import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;

public class ExecuteSearchTest {
	
	@Test
	public void testOperationSearch() throws IOException {
		File testFile = new File("test.txt");
		TextBuddy.clearAllContent(testFile);
		TextBuddy.writeToFile(testFile, "A B C");
		TextBuddy.writeToFile(testFile, "D E A");
		TextBuddy.writeToFile(testFile, "X Y Z");
		TextBuddy test = new TextBuddy();
		
		test.operationSearch(testFile, "A");
		
		
	}

}
