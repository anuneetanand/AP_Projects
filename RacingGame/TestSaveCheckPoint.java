import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class TestSaveCheckPoint
{
    private Control C;
    private Game A;
    private Game B;
    private Game Z;

    @Before
    public void CreatTestFile() throws IOException
    {
        C = new Control();
        A = new Game(100,new User("|<<Tester>>|"));
        C.X = A;
        C.serialize();
    }

    @Test
    public void SerializeTest() throws IOException, ClassNotFoundException
    {
        C.deserialize("|<<Tester>>|");
        B = C.X;
        System.out.println("Running SerializeTest...");
        assertEquals(A,B);
    }

    @Test
    public void SaveCheckPointTest() throws IOException, ClassNotFoundException
    {
        C.deserialize("|<Tester>|");
        Z = C.X;
        System.out.println("Running SaveCheckPointTest...");
        assertEquals(Z.S, 1);
    }
}
