import static org.junit.Assert.*;

import org.junit.Test;

public class GetCommandTest extends TextBuddy {

	@Test
	public void testGetCommand1() {
		String result = getCommand("add abc");
		assertEquals("abc", result);
	}
	
	@Test
	public void testGetCommand2() {
		String result = getCommand("addabc");
		assertEquals("", result);
	}
	
	@Test
	public void testGetCommand3() {
		String result = getCommand("add 123");
		assertEquals("123", result);
	}
	
	@Test
	public void testGetCommand4() {
		String result = getCommand("add       abc       ");
		assertEquals("abc", result);
	}
	
	@Test
	public void testGetCommand5() {
		String result = getCommand("add a b c 1 2 3");
		assertEquals("a b c 1 2 3", result);
	}

}
