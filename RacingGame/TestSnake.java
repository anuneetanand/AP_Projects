import org.junit.Test;

public class TestSnake
{
    @Test(expected=SnakeBiteException.class)
    public void SnakeTest() throws SnakeBiteException, VultureBiteException, CricketBiteException, TrampolineException
    {
        System.out.println("Running SnakeTest...");
        Game G = new Game(100, new User("|<Tester>|"));
        Snake X = new Snake(10);
        G.Shake(X);
    }
}