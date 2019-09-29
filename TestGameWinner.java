import org.junit.Test;

public class TestGameWinner
{
    @Test(expected=GameWinnerException.class)
    public void GameWinnerTest() throws GameWinnerException, Game_25_Exception, Game_50_Exception, Game_75_Exception
    {
        Game G = new Game(100, new User("|<Tester>|"));
        G.CheckPoint(G.getLength());
    }
}