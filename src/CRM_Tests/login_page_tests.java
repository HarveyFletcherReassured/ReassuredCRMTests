package CRM_Tests;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/**
 * Created by hfletcher on 07/12/2017.
 */
public class login_page_tests{

    static void MainClass(WebDriver driver, String url, String message, baseclass WL, int userGroup){
        driver.get(url);

        //Initial username and password
        String username = "";
        String password = "";

        //What usergroup are we trying to open?
        if(userGroup == 1){
            username = "hfletcher";
            password = "*HArGB@@1979";
        }

        //Just print a success message in the terminal
        message = "[INFO]  Started the login page tests";
        System.out.println(message);

        //Tests that check text on the page
        PageText(driver, url, message, WL);

        //Tests that check all the links on the page
        LinkChecks(driver, url, message, WL);

        //Test a set of credentials with a valid username, invalid password
        LCValidUserNotPass(driver, url, message, WL, username, password);

        //Test a set of credentials with an ivalid username, valid password
        LCValidPassNotUser(driver, url, message, WL, username, password);

        //Test a set of credentials with a no username, no password
        LCNoUserAndPass(driver, url, message, WL, username, password);

        //Test a set of credentials with a no username, no password
        LCUserAndNoPass(driver, url, message, WL, username, password);

        //Test a set of credentials with a valid username, valid password
        LCValidUserAndPass(driver, url, message, WL, username, password);
    }

    static void PageText(WebDriver driver, String url, String message, baseclass WL){
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Do we have the text "Reassured CRM" present on the page?
        try{
            //Get the text of the place the header text "Reassured CRM" is expected to be.
            String HeaderText = driver.findElement(By.className("navbar-header")).getText();
            Assert.assertTrue("Text not found!", HeaderText.contains("Reassured CRM"));

            //Add success message to the consistent log
            consistent = consistent + "\n[PASS]  Text \"" + HeaderText + "\" was present on the page.";
        } catch (AssertionError Exception){
            //Add failure message to the consistent log
            consistent = consistent + "\n[FAIL]  Text was not present on the page.";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        //Do we have the text "Login" present on the page?
        try{
            //Get the text of the place the header text "Login" is expected to be.
            String HeaderText = driver.findElement(By.className("navbar-right")).getText();
            Assert.assertTrue("Text not found!", HeaderText.contains("Login"));

            //Add success message to the consistent log
            consistent = consistent + "\n[PASS]  Text \"" + HeaderText.replace("\n", "\" along with \"") + "\" was present on the page.";
        } catch (AssertionError Exception){
            //Add failure message to the consistent log
            consistent = consistent + "\n[FAIL]  Text was not present on the page.";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        //Do we have the text "Register" present on the page?
        try{
            //Get the text of the place the header text "Register" is expected to be.
            String HeaderText = driver.findElement(By.className("navbar-right")).getText();
            Assert.assertTrue("Text not found!", HeaderText.contains("Register"));

            //Add success message to the consistent log
            consistent = consistent + "\n[PASS]  Text \"" + HeaderText.replace("\n", "\" along with \"") + "\" was present on the page.";
        } catch (AssertionError Exception){
            //Add failure message to the consistent log
            consistent = consistent + "\n[FAIL]  Text was not present on the page.";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            WL.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void LinkChecks(WebDriver driver, String url, String message, baseclass WL){
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //What happens if we click the link "Reassured CRM"?
        try{
            //Click the "Reassured CRM" text
            driver.findElement(By.linkText("Reassured CRM")).click();

            //Wait for the page to load, shouldn't take more than 2 seconds.
            try {
                Thread.sleep(2000);
            } catch (Exception Exception) {
                consistent = consistent + "\n[INFO]  There was an issue with waiting for 2 seconds.";
            };

            //Check the URL that has now loaded.
            //First, store it as a var to reduce calls made to webdriver
            String PageLoadedURL = driver.getCurrentUrl();
            //Then compare the string for the index/landing page with what was loaded.
            if(PageLoadedURL.matches(url)){
                //Log the result
                consistent = consistent + "\n[PASS]  \"Reassured CRM\" link is ok on the login page, loaded page: " + PageLoadedURL;
            } else {
                //Log the result
                consistent = consistent + "\n[FAIL]  The page load failed, page loaded was actually " + PageLoadedURL;

                //Take a screenshot for evidence
                try{
                    consistent = consistent + (WL.screenshot(driver, WL));
                } catch (Exception E){
                    E.printStackTrace();
                }
            }
        } catch (Exception Exception){
            //Log the result
            consistent =  consistent + "\n[FAIL]  Couldn't click the link for some reason. Is the link present?";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        //What happens if we click the link "Login"?
        try{
            //Click the "Login" text
            driver.findElement(By.linkText("Login")).click();

            //Wait for the page to load, shouldn't take more than 2 seconds.
            try {
                Thread.sleep(2000);
            } catch (Exception Exception) {
                consistent = consistent + "\n[INFO]  There was an issue with waiting for 2 seconds.";
            };

            //Check the URL that has now loaded.
            //First, store it as a var to reduce calls made to webdriver
            String PageLoadedURL = driver.getCurrentUrl();
            //Then compare the string for the index/landing page with what was loaded.
            if(PageLoadedURL.matches(url)){
                //Log the result
                consistent = consistent + "\n[PASS]  \"Login\" link is OK on the login page, loaded page: " + PageLoadedURL;
            } else {
                //Log the result
                consistent = consistent + "\n[FAIL]  The page load failed, page loaded was actually " + PageLoadedURL;

                //Take a screenshot for evidence
                try{
                    consistent = consistent + (WL.screenshot(driver, WL));
                } catch (Exception E){
                    E.printStackTrace();
                }
            }
        } catch (Exception Exception){
            //Log the result
            consistent = consistent + "\n[FAIL]  Couldn't click the link for some reason. Is the link present?";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        //What happens if we click the link "Register"?
        try{
            //Click the "Reassured CRM" text
            driver.findElement(By.linkText("Register")).click();

            //Wait for the page to load, shouldn't take more than 2 seconds.
            try {
                Thread.sleep(2000);
            } catch (Exception Exception) {
                consistent = consistent + "\n[INFO]  There was an issue with waiting for 2 seconds.";
            };

            //Check the URL that has now loaded.
            //First, store it as a var to reduce calls made to webdriver
            String PageLoadedURL = driver.getCurrentUrl();
            //Then compare the string for the index/landing page with what was loaded.
            if(PageLoadedURL.matches(url + "add-user")){
                //Log the result
                consistent = consistent + "\n[PASS]  \"Register\" link is OK on the login page, loaded page: " + PageLoadedURL;

                //But this isn't the page we are testing, so go back to the login page.
                driver.get(url);
            } else {
                //Log the result
                consistent = consistent + "\n[FAIL]  The page load failed, page loaded was actually " + PageLoadedURL;

                //Take a screenshot for evidence
                try{
                    consistent = consistent + (WL.screenshot(driver, WL));
                } catch (Exception E){
                    E.printStackTrace();
                }

                //But this isn't the page we are testing, so go back to the login page.
                driver.get(url);
            }
        } catch (Exception Exception){
            //Log the result
            consistent =  consistent + "\n[FAIL]  Couldn't click the link for some reason. Is the link present?";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            WL.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void LCValidUserNotPass(WebDriver driver, String url, String message, baseclass WL, String username, String password){
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Log the result
        consistent =  consistent + "\n[INFO]  Attempting to log in as user " + username + " with password " + password;

        //Enter a username that works, password that doesn't
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys("thisisnotaworkingpassword1234");

        //Select the login button
        try{
            driver.findElement(By.className("btn-primary")).click();
            consistent = consistent + "\n[PASS]  Clicked login button, hoping for the best with credentials " + username + " with password " + password;
        } catch (Exception Exception){
            consistent = consistent + "\n[FAIL]  There was an error with clicking the login button";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        try{
            //Get the text of the place the header text "Register" is expected to be.
            String ErrorText = driver.findElement(By.className("alert-danger")).getText();
            Assert.assertTrue("Text not found!", ErrorText.contains("Invalid username or password, try again"));

            //Add success message to the consistent log
            consistent = consistent + "\n[PASS]  Text \"" + ErrorText + "\" was present on the page.";
        } catch (AssertionError Exception){
            //Add failure message to the consistent log
            consistent = consistent + "\n[FAIL]  The text for incorrect username or password was not displayed, did not match the specified text, or the user got signed in when they should not have been. Further investigation is required.";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            WL.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void LCValidPassNotUser(WebDriver driver, String url, String message, baseclass WL, String username, String password){
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Log the result
        consistent =  consistent + "\n[INFO]  Attempting to log in as user thisisnotaworkingusername1234 with password " + password;

        //Enter a username that works, password that doesn't
        driver.findElement(By.id("username")).sendKeys("thisisnotaworkingusername1234");
        driver.findElement(By.id("password")).sendKeys(password);


        //Select the login button
        try{
            driver.findElement(By.className("btn-primary")).click();
            consistent = consistent + "\n[PASS]  Clicked login button, hoping for the best with credentials thisisnotaworkingusername1234 with password " + password;
        } catch (Exception Exception){
            consistent = consistent + "\n[FAIL]  There was an error with clicking the login button";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        try{
            //Get the text of the place the header text "Register" is expected to be.
            String ErrorText = driver.findElement(By.className("alert-danger")).getText();
            Assert.assertTrue("Text not found!", ErrorText.contains("Invalid username or password, try again"));

            //Add success message to the consistent log
            consistent = consistent + "\n[PASS]  Text \"" + ErrorText + "\" was present on the page.";
        } catch (AssertionError Exception){
            //Add failure message to the consistent log
            consistent = consistent + "\n[FAIL]  The text for incorrect username or password was not displayed, did not match the specified text, or the user got signed in when they should not have been. Further investigation is required.";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            WL.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void LCNoUserAndPass(WebDriver driver, String url, String message, baseclass WL, String username, String password){
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Log the result
        consistent =  consistent + "\n[INFO]  Attempting to log in without a username, but with a password";

        //Enter a username that works, password that doesn't
        driver.findElement(By.id("username")).sendKeys("");
        driver.findElement(By.id("password")).sendKeys("esggfgdfgddgdrg");

        //Select the login button
        try{
            driver.findElement(By.className("btn-primary")).click();
            consistent = consistent + "\n[PASS]  Clicked login button";
        } catch (Exception Exception){
            consistent = consistent + "\n[FAIL]  There was an error with clicking the login button";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        try{
            //Get the text of the place the header text "Register" is expected to be.
            String ErrorText = driver.findElement(By.className("help-block")).getText();
            Assert.assertTrue("Text not found!", ErrorText.contains("The username field is required."));

            if(ErrorText.matches("The username field is required.")){
                //Add success message to the consistent log
                consistent = consistent + "\n[PASS]  Text \"" + ErrorText + "\" was present on the page.";
            } else {
                //Add fail message to the consistent log
                consistent = consistent + "\n[FAIL]  Text \"" + ErrorText + "\" was present on the page. Not Correct";

                //Take a screenshot for evidence
                try{
                    consistent = consistent + (WL.screenshot(driver, WL));
                } catch (Exception E){
                    E.printStackTrace();
                }
            }
        } catch (AssertionError Exception){
            //Add failure message to the consistent log
            consistent = consistent + "\n[FAIL]  The text for missing username was not displayed, did not match the specified text, or the user got signed in when they should not have been. Further investigation is required.";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            WL.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void LCUserAndNoPass(WebDriver driver, String url, String message, baseclass WL, String username, String password) {
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Log the result
        consistent = consistent + "\n[INFO]  Attempting to log in with a username, but without a password";

        //Clear the username and password fields
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("password")).clear();

        //Enter a username that works, password that doesn't
        driver.findElement(By.id("username")).sendKeys("username");
        driver.findElement(By.id("password")).sendKeys("");

        //Select the login button
        try {
            driver.findElement(By.className("btn-primary")).click();
            consistent = consistent + "\n[PASS]  Clicked login button";
        } catch (Exception Exception) {
            consistent = consistent + "\n[FAIL]  There was an error with clicking the login button";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        try {
            //Get the text of the place the header text "Register" is expected to be.
            String ErrorText = driver.findElement(By.className("help-block")).getText();
            Assert.assertTrue("Text not found!", ErrorText.contains("The password field is required."));

            if (ErrorText.matches("The password field is required.")) {
                //Add success message to the consistent log
                consistent = consistent + "\n[PASS]  Text \"" + ErrorText + "\" was present on the page.";
            } else {
                //Add fail message to the consistent log
                consistent = consistent + "\n[FAIL]  Text \"" + ErrorText + "\" was present on the page. Not Correct";

                //Take a screenshot for evidence
                try{
                    consistent = consistent + (WL.screenshot(driver, WL));
                } catch (Exception E){
                    E.printStackTrace();
                }
            }
        } catch (AssertionError Exception) {
            //Add failure message to the consistent log
            consistent = consistent + "\n[FAIL]  The text for missing password was not displayed, did not match the specified text, or the user got signed in when they should not have been. Further investigation is required.";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try {
            message = consistent;
            WL.writeout(message);
        } catch (Exception Exception) {
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void LCValidUserAndPass(WebDriver driver, String url, String message, baseclass WL, String username, String password){
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Log the result
        consistent =  consistent + "\n[INFO]  Attempting to log in as user " + username + " with password " + password + " expecting it to be successful";

        //Clear the username and password fields
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("password")).clear();

        //Enter a username that works, password that doesn't
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);

        //Select the login button
        try{
            driver.findElement(By.className("btn-primary")).click();
            consistent = consistent + "\n[PASS]  Clicked login button, hoping for the best with credentials " + username + " with password " + password + " expecting it to be successful";
        } catch (Exception Exception){
            consistent = consistent + "\n[FAIL]  There was an error with clicking the login button";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        //Wait for the page to load, shouldn't take more than 2 seconds.
        try {
            Thread.sleep(2000);
        } catch (Exception Exception) {
            consistent = consistent + "\n[INFO]  There was an issue with waiting for 2 seconds.";
        };

        try{
            Assert.assertTrue(driver.findElements(By.className("alert-danger")).isEmpty());
            consistent = consistent + "\n[PASS]  The user was logged in and the error was not displayed.";
        } catch (AssertionError Exception){
            //Add failure message to the consistent log
            consistent = consistent + "\n[FAIL]  The text for incorrect username or password was displayed. It should not have been.";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception E){
                E.printStackTrace();
            }
        }

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            WL.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }
}
