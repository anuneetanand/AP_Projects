import org.junit.Test;

public class TestVulture
{
    @Test(expected=VultureBiteException.class)
    public void VultureTest() throws SnakeBiteException, VultureBiteException, CricketBiteException, TrampolineException
    {
        Game G = new Game(100, new User("|<Tester>|"));
        Vulture X = new Vulture(10);
        G.Shake(X);
    }
}