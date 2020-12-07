import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass
{
    @Test
    public void testGetLocalNumber()
    {
        int correctLocalNumber = 14;
        Assert.assertTrue("Wrong local number by getLocalNumber() function!",getLocalNumber()==correctLocalNumber);
    }
}
