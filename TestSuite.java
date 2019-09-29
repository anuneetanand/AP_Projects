import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(value=Suite.class)
@Suite.SuiteClasses(value={TestCricket.class, TestSnake.class, TestVulture.class, TestTrampoline.class, TestGameWinner.class, TestSaveCheckPoint.class})
public class TestSuite { }