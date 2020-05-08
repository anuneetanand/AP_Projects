// Anuneet Anand
// 2018022
// CSE
// Lab-6-Testing

import org.junit.runner.notification.Failure;
import org.junit.runner.*;

public class TestRunner
{
    public static void main(String[] args)
    {
        System.out.println(">>>--- TESTING ---<<<");
        Result result = JUnitCore.runClasses((Class[])new Class[]{TestSuite.class});
        for (Failure failure : result.getFailures())
        { System.out.println(failure.toString()); }
        if(result.wasSuccessful())
        { System.out.println("All Tests Passed"); }
    }
}