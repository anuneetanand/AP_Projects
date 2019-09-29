import org.junit.Test;

public class TestTrampoline
{
    @Test(expected=TrampolineException.class)
    public void TrampolineTest() throws SnakeBiteException, VultureBiteException, CricketBiteException, TrampolineException
    {
        Game G = new Game(100, new User("|<Tester>|"));
        Trampoline X = new Trampoline(10);
        G.Shake(X);
    }
}