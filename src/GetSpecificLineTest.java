import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class GetSpecificLineTest extends TextBuddy {

	@Test
	public void testGetSpecificLine1() throws IOException, Exception {
		ArrayList<String> testArray = new ArrayList<String>(10);
		testArray.add("line 1");
		testArray.add("line 2");
		testArray.add("line 3");
		testArray.add("line 4");
		testArray.add("line 5");
		testArray.add("line 6");
		testArray.add("line 7");
		testArray.add("line 8");
		testArray.add("line 9");
		testArray.add("line 10");

		int lineToBeDeleted = 1;
		String result = getSpecificLine(testArray, lineToBeDeleted);
		assertEquals("line 1", result);
	}

	@Test
	public void testGetSpecificLine2() throws IOException, Exception {
		ArrayList<String> testArray = new ArrayList<String>(10);
		testArray.add("line 1");
		testArray.add("line 2");
		testArray.add("line 3");
		testArray.add("line 4");
		testArray.add("line 5");
		testArray.add("line 6");
		testArray.add("line 7");
		testArray.add("line 8");
		testArray.add("line 9");
		testArray.add("line 10");

		int lineToBeDeleted = 2;
		String result = getSpecificLine(testArray, lineToBeDeleted);
		assertEquals("line 2", result);
	}

	@Test
	public void testGetSpecificLine3() throws IOException, Exception {
		ArrayList<String> testArray = new ArrayList<String>(10);
		testArray.add("line 1");
		testArray.add("line 2");
		testArray.add("line 3");
		testArray.add("line 4");
		testArray.add("line 5");
		testArray.add("line 6");
		testArray.add("line 7");
		testArray.add("line 8");
		testArray.add("line 9");
		testArray.add("line 10");

		int lineToBeDeleted = 3;
		String result = getSpecificLine(testArray, lineToBeDeleted);
		assertEquals("line 3", result);
	}

	@Test
	public void testGetSpecificLine4() throws IOException, Exception {
		ArrayList<String> testArray = new ArrayList<String>(10);
		testArray.add("line 1");
		testArray.add("line 2");
		testArray.add("line 3");
		testArray.add("line 4");
		testArray.add("line 5");
		testArray.add("line 6");
		testArray.add("line 7");
		testArray.add("line 8");
		testArray.add("line 9");
		testArray.add("line 10");

		int lineToBeDeleted = 4;
		String result = getSpecificLine(testArray, lineToBeDeleted);
		assertEquals("line 4", result);
	}

	@Test
	public void testGetSpecificLine10() throws IOException, Exception {
		ArrayList<String> testArray = new ArrayList<String>(10);
		testArray.add("line 1");
		testArray.add("line 2");
		testArray.add("line 3");
		testArray.add("line 4");
		testArray.add("line 5");
		testArray.add("line 6");
		testArray.add("line 7");
		testArray.add("line 8");
		testArray.add("line 9");
		testArray.add("line 10");

		int lineToBeDeleted = 10;
		String result = getSpecificLine(testArray, lineToBeDeleted);
		assertEquals("line 10", result);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetSpecificLineExceptionThrown1() throws FileNotFoundException, IOException {
		ArrayList<String> testArray = new ArrayList<String>(10);
		testArray.add("line 1");
		testArray.add("line 2");
		testArray.add("line 3");
		testArray.add("line 4");
		testArray.add("line 5");
		testArray.add("line 6");
		testArray.add("line 7");
		testArray.add("line 8");
		testArray.add("line 9");
		testArray.add("line 10");

		int lineToBeDeleted = 11;
		String result = getSpecificLine(testArray, lineToBeDeleted);
		assertEquals("line 10", result);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetSpecificLineExceptionThrown2() throws FileNotFoundException, IOException {
		ArrayList<String> testArray = new ArrayList<String>(10);
		testArray.add("line 1");
		testArray.add("line 2");
		testArray.add("line 3");
		testArray.add("line 4");
		testArray.add("line 5");
		testArray.add("line 6");
		testArray.add("line 7");
		testArray.add("line 8");
		testArray.add("line 9");
		testArray.add("line 10");

		int lineToBeDeleted = -1;
		String result = getSpecificLine(testArray, lineToBeDeleted);
		assertEquals("line 10", result);
	}

}
