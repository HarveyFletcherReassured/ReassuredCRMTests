package CRM_Tests;

import com.mysql.fabric.xmlrpc.base.Array;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hfletcher on 07/09/2017.
 */
public class baseclass {
    public WebDriver driver;

    @SuppressWarnings("resource")
    @Test(threadPoolSize = 5, invocationCount = 1)
    public void main() throws  Exception{
        String url = "http://10.128.128.10/";

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

        //Tests for the login page
        //login_page_tests LoginPage = new login_page_tests();
        //login_page_tests.MainClass(driver, url, message, WL);

        //Tests for the add user and list user page
        dev_380_list_create_new_user ListCreateNewUser = new dev_380_list_create_new_user();
        dev_380_list_create_new_user.MainClass(driver, url, message, WL);

        //Close any remaining webdriver sessions
        try{
            driver.quit();
        } catch (Exception Exception) {
            message = "[INFO]  There was no open driver session to close.";
            WL.writeout(message);
        }
    }

    public String[] readDropDown(String DropDownId, WebDriver driver, String[] options){
        //What options are in the drop down menu
        Select menu = new Select(driver.findElement(By.id(DropDownId)));

        //Set I to 0
        int i = 0;

        //How long is the list
        int length = menu.getOptions().size();

        while(i < length){
            options[i] = menu.getOptions().get(i).getText();
            i++;
        }

        return options;
    }

    public String[] userCredentials(int Username, int Password){
        String[][] Credentials = {
                {"hfletcher","dwilkins","dharris","sloadwickmgr","enewell","dpescottrl","bgallop","swilkin","apougher","aweber","mwatts","lwatts","thisisnotavalidusername"},
                {"*HArGB@@1979","thisisnotavalidpassword"}
        };

        String[] result = { Credentials[0][Username] , Credentials[1][Password]};

        return result;
    }

    static void writeout(String message) throws IOException{
        Date currentdate = new Date();
        try{
            System.out.println(message);
            Files.write(Paths.get("C:/CRM_Tests/src/CRM_Tests/logfile.txt"), (currentdate + ":" + "    " + message.replace("\n", System.getProperty("line.separator")) +  System.getProperty("line.separator") +  System.getProperty("line.separator")).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException WriteError){
            System.out.println("[WARN]  Failed to write the log file. Continuing anyway...");
        }
    }

    public String screenshot(WebDriver driver, baseclass WL) throws IOException{
        Date datetime = new Date();

        String FileName = "C:/CRM_Tests/src/CRM_Tests/screenshots/shot_" + datetime.toString().replace(" ","_").replace(":","_") + ".png";

        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(FileName));

        return "\n[INFO]  Screenshot saved as " + FileName;
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
