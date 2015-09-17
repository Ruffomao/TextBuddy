import static org.junit.Assert.*;

import org.junit.Test;

public class GetOperationTest {

	@Test
	public void testGetOperation1() {
		String result = TextBuddy.getOperation("add abc");
		assertEquals("add", result);
	}
	
	@Test
	public void testGetOperation2() {
		String result = TextBuddy.getOperation("addabc");
		assertEquals("addabc", result);
	}
	
	@Test
	public void testGetOperation3() {
		String result = TextBuddy.getOperation("");
		assertEquals("", result);
	}
	
	@Test
	public void testGetOperation4() {
		String result = TextBuddy.getOperation("add.123 abc");
		assertEquals("add.123", result);
	}

}
