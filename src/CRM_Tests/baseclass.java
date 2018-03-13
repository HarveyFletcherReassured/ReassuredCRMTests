package CRM_Tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

/**
 * Created by hfletcher on 07/09/2017.
 */
public class baseclass{
    public static WebDriver driver = setUp();

    static WebDriver setUp(){
        try{
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");

            DesiredCapabilities capability = DesiredCapabilities.chrome();
            capability.setCapability(ChromeOptions.CAPABILITY, options);

            String nodeURL = "http://10.168.206.234:4445/wd/hub";
            System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
            capability.setPlatform(Platform.WINDOWS);
            WebDriver driver = new RemoteWebDriver(new URL(nodeURL), capability);
            return driver;
        } catch (Exception e){
            e.printStackTrace();
        }

        return driver;
    }
}
