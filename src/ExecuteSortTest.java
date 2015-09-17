import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class ExecuteSortTest {

	@Test
	public void testOperationSort() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortArray() throws IOException {
		File file = new File("test.txt");
		TextBuddy.clearAllContent(file);
		TextBuddy.writeToFile(file, "d");
		TextBuddy.writeToFile(file, "c");
		TextBuddy.writeToFile(file, "b");
		TextBuddy.writeToFile(file, "a");
		ArrayList<String> contentArray = new ArrayList<String>();
		contentArray = TextBuddy.contentToArray(file, TextBuddy.countNumOfLines(file));
		TextBuddy.sortArray(contentArray);
		Object[] resultArray = new String[contentArray.size()];
		resultArray = contentArray.toArray();
		String[] expectedArray = {"a", "b", "c", "d"};
		
		assertArrayEquals(expectedArray, resultArray);
	}

}
