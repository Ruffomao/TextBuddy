import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ExecuteSearchTest {
	
	@Test
	public void testSearchForWord1() {
		ArrayList<String> testArray = new ArrayList<String>();
		testArray.add("ABC");
		testArray.add("DEF");
		testArray.add("GHI");
		
		ArrayList<String> resultArray = new ArrayList<String>();
		resultArray = TextBuddy.searchForWord(testArray, "A");
		
		Object[] result = new String[resultArray.size()];
		result = resultArray.toArray();
		String[] expected = {"ABC"};
		assertArrayEquals(expected, result);
	}
	
	@Test
	public void testSearchForWord2() {
		ArrayList<String> testArray = new ArrayList<String>();
		testArray.add("EFI");
		testArray.add("ABI");
		testArray.add("CDI");
		
		ArrayList<String> resultArray = new ArrayList<String>();
		resultArray = TextBuddy.searchForWord(testArray, "I");
		
		Object[] result = new String[resultArray.size()];
		result = resultArray.toArray();
		String[] expected = {"ABI", "CDI", "EFI"};
		assertArrayEquals(expected, result);
	}
	
	@Test
	public void testSearchForWord3() {
		ArrayList<String> testArray = new ArrayList<String>();
		testArray.add("ABC");
		testArray.add("ABC");
		testArray.add("ABC");
		
		ArrayList<String> resultArray = new ArrayList<String>();
		resultArray = TextBuddy.searchForWord(testArray, "A");
		
		Object[] result = new String[resultArray.size()];
		result = resultArray.toArray();
		String[] expected = {"ABC"};
		assertArrayEquals(expected, result);
	}

}
