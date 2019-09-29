import org.junit.Test;

public class TestCricket
{
    @Test(expected=CricketBiteException.class)
    public void CricketTest() throws SnakeBiteException, VultureBiteException, CricketBiteException, TrampolineException
    {
        Game G = new Game(100, new User("|<Tester>|"));
        Cricket X = new Cricket(10);
        G.Shake(X);
    }
}