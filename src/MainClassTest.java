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

    @Test
    public void testGetClassNumber()
    {
        int threshold = 45;
        Assert.assertTrue("Value getClassNumber() is less than " + threshold, getClassNumber()>threshold);
    }

    @Test
    public void testGetClassString()
    {
        String first = "Hello";
        String second = "hello";
        Assert.assertTrue("getClassString not contains " + first + " or "+second, getClassString().contains(first) || getClassString().contains(second));
    }

}
