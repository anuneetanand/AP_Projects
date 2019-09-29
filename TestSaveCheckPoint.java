import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class TestSaveCheckPoint
{
    @Test
    public void SaveCheckPointTest() throws IOException, ClassNotFoundException
    {
        Control C = new Control();
        C.deserialize();
        Game X = C.getX();
        assertEquals(X.getS(), 1);
    }
}