import static org.junit.Assert.*;

import java.io.*;
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
		
	}

}
