package CRM_Tests;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hfletcher on 07/09/2017.
 */
public class baseclass {
    public WebDriver driver;

    @SuppressWarnings("resource")
    @Test(threadPoolSize = 5, invocationCount = 10)
    public void main() throws  Exception{
        String url = "http://test.reassuredpensions.co.uk";

        try {
            WebDriver driver = setUp();
            thetest(driver, url);
        } catch (MalformedURLException var3) {
            var3.printStackTrace();
        }
    }

    static void thetest(WebDriver driver, String url) throws Exception{
        //Set up a converter
        SimpleDateFormat sdf = new SimpleDateFormat("dd'/'MM'/'yyyy");

        //Set up some variables
        Date ValidDate = new Date();
        String Yesterday = sdf.format(((ValidDate.getTime() / 1000) - 86400) * 1000);
        String Today = sdf.format(ValidDate.getTime());
        String Tomorrow = sdf.format(((ValidDate.getTime() / 1000) + 86400) * 1000);
        String ThreeMonths = sdf.format(((ValidDate.getTime() / 1000) + 786400) * 1000);
        String ThreeMonthsAndDay = sdf.format(((ValidDate.getTime() / 1000) + 7884000 + 86400) * 1000);
        String daterequired = "12/01/1999";
        baseclass WL = new baseclass();
        String message = "Initial.";

        //Tests you want to run while creating new leads
        //lead_creation_method Method1 = new lead_creation_method();
        //lead_creation_method.TheTest(driver, url, message, WL, daterequired);
        driver.quit();
    }

    static void writeout(String message) throws IOException{
        Date currentdate = new Date();
        try{
            System.out.println(message);
            Files.write(Paths.get("C:/CRM_Tests/src/CRM_Tests/logfile.txt"), (currentdate + " - " + message +  System.getProperty("line.separator")).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException WriteError){
            System.out.println("[WARN]  Failed to write the log file. Continuing anyway...");
        }
    }

    static void switchusr(String username, WebDriver driver, String url, baseclass WL, String message){
        try{
            try{
                driver.findElement(By.className("logout")).click();
            } catch (NoSuchElementException NSEE){
                message = "[WARN]  Couldn't find the logout button... User might already be signed out or was not signed in in the first place. Additional info code: " + NSEE.getClass().getSimpleName();
                WL.writeout(message);
            }
            driver.get(url);
            driver.findElement(By.id("UserUsername")).clear();
            driver.findElement(By.id("UserUsername")).sendKeys(username);
            driver.findElement(By.id("UserPassword")).clear();
            driver.findElement(By.id("UserPassword")).sendKeys("Tstpasswd@Adrian");
            new Select(driver.findElement(By.id("ghostuser"))).selectByVisibleText("Selenium");
            driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            };

            message = "[PASS]  Signed in / Switched user to the " + username + " user, ghost selenium password.";
            WL.writeout(message);
        } catch (Exception E){
            try {
                message = "[FAIL]    Failed to sign in / switch user to the " + username + " user, ghost selenium password. Error: " + E;
                WL.writeout(message);
            } catch (Exception e){
                System.out.println("[WARN]  Failed to write the log file. Continuing anyway...");
            }

        }
    }

    static WebDriver setUp() throws MalformedURLException{
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setCapability(ChromeOptions.CAPABILITY, options);

        String nodeURL = "http://10.168.206.234:4445/wd/hub";
        System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
        capability.setPlatform(Platform.WINDOWS);
        WebDriver driver = new RemoteWebDriver(new URL(nodeURL), capability);
        return driver;
    }
}
