import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GetCommandTest.class, GetOperationTest.class, GetSpecificLineTest.class, ExecuteSortTest.class, ExecuteSearchTest.class })
public class AllTests {

}
