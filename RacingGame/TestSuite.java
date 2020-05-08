
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(value=Suite.class)
@Suite.SuiteClasses(value={ TestSaveCheckPoint.class,TestCricket.class, TestSnake.class, TestVulture.class, TestTrampoline.class, TestGameWinner.class})
public class TestSuite { }