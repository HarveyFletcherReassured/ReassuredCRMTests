package CRM_Tests;

import org.openqa.selenium.WebDriver;

import java.util.Date;

/**
 * Created by hfletcher on 13/03/2018.
 */
public class ClassGlobals extends baseclass{
    //Important webdriver stuff that is used to execute tests
    public static WebDriver driver = baseclass.driver;

    //This is the environment that the tests will be run on.
    public static String environment = "http://test.reassuredpensions.co.uk";
    //public static String environment = "http://staging.reassuredpensions.co.uk";
    //public static String environment = "http://yellow.reassuredpensions.co.uk";
    //public static String environment = "http://blue.reassuredpensions.co.uk";
    //public static String environment = "http://aviva.reassuredpensions.co.uk";

    //Strings that are used for testing
    public static String OutputFolder = "C:/CRM_Tests/src/CRM_Tests/output/";

    //Dates that get used for testing.
    public static Date currentdate = new Date();

    //Additional methods that are used for testing.
    public static screenshot_taker take_screenshot = new screenshot_taker();
    public static test_logger write_log = new test_logger();
}
