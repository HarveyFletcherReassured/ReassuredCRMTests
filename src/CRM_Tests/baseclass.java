package CRM_Tests;

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
import java.util.Date;

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
        //Make sure we are able to call this class from other test scripts
        baseclass BC = new baseclass();

        //Set the message to a starting point.
        String message = "Initial.";

        //Tests for the login page
        //login_page_tests LoginPage = new login_page_tests();
        //login_page_tests.MainClass(driver, url, message, BC);

        //Tests for the add user and list user page
        dev_380_list_create_new_user ListCreateNewUser = new dev_380_list_create_new_user();
        dev_380_list_create_new_user.MainClass(driver, url, message, BC);

        //Close any remaining webdriver sessions
        try{
            driver.quit();
        } catch (Exception Exception) {
            message = "[INFO]  There was no open driver session to close.";
            BC.writeout(message);
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

    public String usernameToFirstname(String usernameSearch){
        //Initialise variable
        String FullName = "";

        //What username we entered into this function affects what the full name should contain.
        //This function is used for username searches.
        if(usernameSearch == "hfletcher"){
            FullName = "Harvey Fletcher";
        }else if(usernameSearch == "dwilkins"){
            FullName = "Dawn Wilkins";
        }else if(usernameSearch == "dharris"){
            FullName = "Dean Harris";
        }else if(usernameSearch == "sloadwickmgr"){
            FullName = "Sherrie Loadwick";
        }else if(usernameSearch == "enewell"){
            FullName = "Emer Newell";
        }else if(usernameSearch == "dpescottrl"){
            FullName = "Dave Pescott";
        }else if(usernameSearch == "bgallop"){
            FullName = "Becky Gallop";
        }else if(usernameSearch == "swilkin"){
            FullName = "Simon Wilkin";
        }else if(usernameSearch == "apougher"){
            FullName = "Amy Pougher";
        }else if(usernameSearch == "aweber"){
            FullName = "Amy Weber";
        }else if(usernameSearch == "mwatts"){
            FullName = "Matt Watts";
        }else if(usernameSearch == "lwatts"){
            FullName = "Luke Watts";
        }

        //Return the searched user's full name.
        return FullName;
    }

    static void writeout(String message) throws IOException{
        //What's the date?
        Date currentdate = new Date();

        //Write to the log file with what we put into this function.
        try{
            System.out.println(message);
            Files.write(Paths.get("C:/CRM_Tests/src/CRM_Tests/logfile.txt"), (currentdate + ":" + "    " + message.replace("\n", System.getProperty("line.separator")) +  System.getProperty("line.separator") +  System.getProperty("line.separator")).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException WriteError){
            System.out.println("[WARN]  Failed to write the log file. Continuing anyway...");
        }
    }

    public String screenshot(WebDriver driver, baseclass BC) throws IOException{
        //What's the date
        Date datetime = new Date();

        //What do we want to save the file as?
        String FileName = "C:/CRM_Tests/src/CRM_Tests/screenshots/shot_" + datetime.toString().replace(" ","_").replace(":","_") + ".png";

        //Take the screenshot
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(FileName));

        //Return a message so it gets added to the log.
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
