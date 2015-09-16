import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;
import org.junit.runners.model.InitializationError;

public class ParseCommandTest {

	@Before
	public void setUp() {
		String[] args = new String[1];
		args[0] = "test.txt";
	}
	
	@Test
	public final void testParseCommand() throws InitializationError, IOException {
		TextBuddy test = new TextBuddy();
		ArrayList<String> resultArrayList = new ArrayList<String>();
		resultArrayList.add("add");
		resultArrayList.add("123");
		
		assertNotNull(test.parseCommand());
	}

}
