package CRM_Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;


/**
 * Created by hfletcher on 11/12/2017.
 */
public class dev_380_list_create_new_user {

    static void MainClass(WebDriver driver, String url, String message, baseclass WL) {
        //Write the new class to the log
        try{
            message = "[INFO]  Started the tests in \"DEV_380\"_list_create_new_user";
            WL.writeout(message);
        } catch (Exception Exception){
            System.out.println(message);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }

        //Open the new CRM at the login page
        driver.get(url);

        //Set up variables to generate credentials variables, these are invalid
        String[] Credentials = WL.userCredentials(12, 1);

        //Set up admin login credentials
        Credentials = WL.userCredentials(0, 0);
        String AdminUser = Credentials[0];
        String AdminPass = Credentials[1];

        //Set up normal login credentials
        Credentials = WL.userCredentials(1, 0);
        String NormUser = Credentials[0];
        String NormPass = Credentials[1];

        //Positive Testing
        //Sign in as an admin user
        login_page_tests.LCValidUserAndPass(driver, url, message, WL, AdminUser, AdminPass);
        //Start the test by opening the form
        AdminUserOpenForm(driver, url, message, WL);
        //Now do field validation checks
        AddUserFieldValidationsBlankForm(driver, url, message, WL);
        AddUserFieldValidationsWhiteSpaceAllFields(driver, url, message, WL);
        AddUserFieldValidationsSpecialChars(driver, url, message, WL);
        AddUserFieldValidationsEmailAddress(driver, url, message, WL);
        AddUserFieldValidationsPhoneExtension(driver, url, message, WL);
        AddUserFieldValidationsValid(driver, url, message, WL);
        //Open the user list form and run the required tests.
        AdminViewUserListSearchOptions(driver, url, message, WL);
        AdminViewUserListCheckTableHeadings(driver, url, message, WL);
        AdminViewUserListSearchFirstname(driver, url, message, WL);
        AdminViewUserListSearchLastname(driver, url, message, WL);
        AdminViewUserListSearchFirstnameLastname(driver, url, message, WL);
        AdminViewUserListSearchUsername(driver, url, message, WL);

        //Tests are done! Log message
        try{
            message = "[INFO]  Finished the tests in \"DEV_380\"_list_create_new_user";
            BC.writeout(message);
        } catch (Exception Exception){
            System.out.println(message);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AdminUserOpenForm(WebDriver driver, String url, String message, baseclass BC){
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Open the [Users] drop-down menu
        try{
            driver.findElement(By.linkText("Users")).click();
            //Add success message to the consistent log
            consistent = consistent + "\n[PASS]  Opened the [Users] drop down menu";
        } catch (Exception E){
            //Add failure message to the consistent log
            consistent = consistent + "\n[FAIL]  Couldn't select the [Users] drop down menu";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                E.printStackTrace();
            }
        }

        //Take a screenshot for evidence
        try{
            consistent = consistent + (BC.screenshot(driver, BC) + " passed but keeping this for evidence or later review");
        } catch (Exception E){
            E.printStackTrace();
        }

        //Select the [Add User] link
        try{
            //Select the link
            driver.findElement(By.linkText("Add User")).click();

            //Wait for the page to load, shouldn't take more than 2 seconds.
            try {
                Thread.sleep(500);
            } catch (Exception Exception) {
                consistent = consistent + "\n[INFO]  There was an issue with waiting for .5 seconds.";
            };

            //Log the URL that loaded
            String PageLoadedURL = driver.getCurrentUrl();

            //Verify the URL is correct
            if(PageLoadedURL.matches(url + "add-user")){
                //Log the result
                consistent = consistent + "\n[PASS]  \"Register\" link is OK on the login page, loaded page: " + PageLoadedURL;
            } else {
                //Log the result
                consistent = consistent + "\n[FAIL]  The page load failed, page loaded was actually " + PageLoadedURL + ". This has completely failed the test, can't continue.";

                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception E){
                    E.printStackTrace();
                }

                //Write to the log file, if it fails, print out to System.out and add a warning message
                try{
                    message = consistent;
                    BC.writeout(message);
                } catch (Exception Exception){
                    System.out.println(consistent);
                    System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
                }

                //It will not be possible to continue. Stop this test.
                return;
            }

            consistent = consistent + "\n[PASS]  Test passed.";

            //Write to the log file, if it fails, print out to System.out and add a warning message
            try{
                message = consistent;
                BC.writeout(message);
            } catch (Exception Exception){
                System.out.println(consistent);
                System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
            }

        } catch (Exception E){
            //Add failure message to the consistent log
            consistent = consistent + "\n[FAIL]  Couldn't select the [Users] drop down menu";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                E.printStackTrace();
            }

            consistent = consistent + "\n[FAIL]  Test failed.";

            //Write to the log file, if it fails, print out to System.out and add a warning message
            try{
                message = consistent;
                BC.writeout(message);
            } catch (Exception Exception){
                System.out.println(consistent);
                System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
            }
        }
    }

    static void AddUserFieldValidationsBlankForm(WebDriver driver, String url, String message, baseclass BC) {
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object() {}.getClass().getEnclosingMethod().getName();

        //Try and submit the form before putting anything in it
        try {
            driver.findElement(By.className("btn-primary")).click();

            if (driver.findElements(By.className("alert-danger")).size() == 0) {
                consistent = consistent + "\n[FAIL]  The alert box was not displayed. Can't continue this test.";

                //Exit this test but take a screenshot first.
                try {
                    consistent = consistent + (BC.screenshot(driver, BC));
                    message = consistent;
                    BC.writeout(message);
                    return;
                } catch (Exception Exception) {
                    System.out.println(consistent);
                    System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
                }
            } else {
                consistent = consistent + "\n[PASS]  The alert box was displayed";
            }

            //Get the exact validation error message displayed
            String errorText = driver.findElement(By.className("alert-danger")).getText();

            //Information about first name?
            if (errorText.contains("The first name field is required")) {
                consistent = consistent + "\n[PASS]  The error contained information on the first name field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the first name field, it should do.";
                //Take a screenshot for evidence
                try {
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception) {
                    Exception.printStackTrace();
                }
            }

            //Information about surname?
            if (errorText.contains("The surname field is required")) {
                consistent = consistent + "\n[PASS]  The error contained information on the surname field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the surname field, it should do.";
                //Take a screenshot for evidence
                try {
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception) {
                    Exception.printStackTrace();
                }
            }

            //Information about username?
            if (errorText.contains("The username field is required")) {
                consistent = consistent + "\n[PASS]  The error contained information on the username field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the username field, it should do.";
                //Take a screenshot for evidence
                try {
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception) {
                    Exception.printStackTrace();
                }
            }

            //Information about email?
            if (errorText.contains("The email field is required")) {
                consistent = consistent + "\n[PASS]  The error contained information on the email field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the email field, it should do.";
                //Take a screenshot for evidence
                try {
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception) {
                    Exception.printStackTrace();
                }
            }

            //Information about ldap username?
            if (errorText.contains("The ldap username field is required")) {
                consistent = consistent + "\n[PASS]  The error contained information on the ldap username field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the ldap username field, it should do.";
                //Take a screenshot for evidence
                try {
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception) {
                    Exception.printStackTrace();
                }
            }

            //Information about phone extension?
            if (errorText.contains("The phone extension field is required")) {
                consistent = consistent + "\n[PASS]  The error contained information on the phone extension field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the phone extension field, it should do.";
                //Take a screenshot for evidence
                try {
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception) {
                    Exception.printStackTrace();
                }
            }

            //Information about ddi?
            if (errorText.contains("The ddi field is required")) {
                consistent = consistent + "\n[PASS]  The error contained information on the ddi field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the ddi field, it should do.";
                //Take a screenshot for evidence
                try {
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception) {
                    Exception.printStackTrace();
                }
            }

            //Information about ddi?
            if (errorText.contains("The mobile phone number field is required")) {
                consistent = consistent + "\n[PASS]  The error contained information on the mobile phone number field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the mobile phone number field, it should do.";
                //Take a screenshot for evidence
                try {
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception) {
                    Exception.printStackTrace();
                }
            }

            //Information about job description?
            if (errorText.contains("The job description field is required")) {
                consistent = consistent + "\n[PASS]  The error contained information on the job description field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the job description field, it should do.";
                //Take a screenshot for evidence
                try {
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception) {
                    Exception.printStackTrace();
                }
            }
        } catch (Exception E) {
            consistent = consistent + "\n[FAIL]  Add user form was submitted when it was blank, or the error messages displayed were incorrect.";
            //Take a screenshot for evidence
            try {
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception) {
                Exception.printStackTrace();
            }
        }

        consistent = consistent + "\n[PASS]  Test passed.";

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            BC.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AddUserFieldValidationsWhiteSpaceAllFields(WebDriver driver, String url, String message, baseclass BC){
        //Re-load the form
        driver.get(url + "add-user");

        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Put whitespaces into every field.
        driver.findElement(By.id("first_name")).sendKeys(" ");
        driver.findElement(By.id("surname")).sendKeys(" ");
        driver.findElement(By.id("username")).sendKeys(" ");
        driver.findElement(By.id("email")).sendKeys(" ");
        driver.findElement(By.id("ldap_username")).sendKeys(" ");
        driver.findElement(By.id("phone_extension")).sendKeys(" ");
        driver.findElement(By.id("ddi")).sendKeys(" ");
        driver.findElement(By.id("job_description")).sendKeys(" ");
        driver.findElement(By.id("mobile_phone_number")).sendKeys(" ");

        //Try and submit the form before putting anything in it
        try{
            driver.findElement(By.className("btn-primary")).click();

            if(driver.findElements(By.className("alert-danger")).size() == 0){
                consistent = consistent + "\n[FAIL]  The alert box was not displayed. Can't continue this test.";

                //Exit this test but take a screenshot first.
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                    message = consistent;
                    BC.writeout(message);
                    return;
                } catch (Exception Exception){
                    System.out.println(consistent);
                    System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
                }
            } else {
                consistent = consistent + "\n[PASS]  The alert box was displayed";
            }

            //Get the exact validation error message displayed
            String errorText = driver.findElement(By.className("alert-danger")).getText();

            //Information about first name?
            if(errorText.contains("The first name field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the first name field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the first name field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about surname?
            if(errorText.contains("The surname field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the surname field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the surname field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about username?
            if(errorText.contains("The username field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the username field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the username field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about email?
            if(errorText.contains("The email field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the email field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the email field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about ldap username?
            if(errorText.contains("The ldap username field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the ldap username field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the ldap username field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about phone extension?
            if(errorText.contains("The phone extension field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the phone extension field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the phone extension field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about ddi?
            if(errorText.contains("The ddi field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the ddi field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the ddi field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about ddi?
            if(errorText.contains("The mobile phone number field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the mobile phone number field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the mobile phone number field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about job description?
            if(errorText.contains("The job description field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the job description field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the job description field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }
        } catch (Exception E){
            consistent = consistent + "\n[FAIL]  Add user form was submitted when it was blank, or the error messages displayed were incorrect.";
            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }
        }

        consistent = consistent + "\n[PASS]  Test passed.";

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            BC.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AddUserFieldValidationsSpecialChars(WebDriver driver, String url, String message, baseclass BC){
        //Re-load the form
        driver.get(url + "add-user");

        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Put whitespaces into every field.
        driver.findElement(By.id("first_name")).sendKeys("[<./>]<?php*\"%$£!78;'|~@");
        driver.findElement(By.id("surname")).sendKeys("[<./>]<?php*\"%$£!78;'|~@");
        driver.findElement(By.id("username")).sendKeys("[<./>]<?php*\"%$£!78;'|~@");
        driver.findElement(By.id("email")).sendKeys("[<./>]<?php*\"%$£!78;'|~@");
        driver.findElement(By.id("ldap_username")).sendKeys("[<./>]<?php*\"%$£!78;'|~@");
        driver.findElement(By.id("phone_extension")).sendKeys("[<./>]<?php*\"%$£!78;'|~@");
        driver.findElement(By.id("ddi")).sendKeys("[<./>]<?php*\"%$£!78;'|~@");
        driver.findElement(By.id("job_description")).sendKeys("[<./>]<?php*\"%$£!78;'|~@");
        driver.findElement(By.id("mobile_phone_number")).sendKeys("[<./>]<?php*\"%$£!78;'|~@");

        //Try and submit the form before putting anything in it
        try{
            driver.findElement(By.className("btn-primary")).click();

            if(driver.findElements(By.className("alert-danger")).size() == 0){
                consistent = consistent + "\n[FAIL]  The alert box was not displayed. Can't continue this test.\n[INFO]  Database check required to see if the user was added.";

                //Exit this test but take a screenshot first.
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                    message = consistent;
                    BC.writeout(message);
                    return;
                } catch (Exception Exception){
                    System.out.println(consistent);
                    System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
                }
            } else {
                consistent = consistent + "\n[PASS]  The alert box was displayed";
            }

            //Get the exact validation error message displayed
            String errorText = driver.findElement(By.className("alert-danger")).getText();

            //Information about first name?
            if(errorText.contains("The first name field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the first name field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the first name field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about surname?
            if(errorText.contains("The surname field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the surname field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the surname field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about username?
            if(errorText.contains("The username field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the username field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the username field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about email?
            if(errorText.contains("The email field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the email field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the email field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about ldap username?
            if(errorText.contains("The ldap username field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the ldap username field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the ldap username field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about phone extension?
            if(errorText.contains("The phone extension field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the phone extension field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the phone extension field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about ddi?
            if(errorText.contains("The ddi field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the ddi field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the ddi field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about ddi?
            if(errorText.contains("The mobile phone number field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the mobile phone number field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the mobile phone number field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about job description?
            if(errorText.contains("The job description field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the job description field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the job description field, it should do.";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }
        } catch (Exception E){
            consistent = consistent + "\n[FAIL]  Add user form was submitted when it was blank, or the error messages displayed were incorrect.";
            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }
        }

        consistent = consistent + "\n[PASS]  Test passed.";

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            BC.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AddUserFieldValidationsEmailAddress(WebDriver driver, String url, String message, baseclass BC){
        //Re-load the form
        driver.get(url + "add-user");

        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Enter valid values except email address
        driver.findElement(By.id("first_name")).sendKeys("Harvey");
        driver.findElement(By.id("surname")).sendKeys("Fletcher");
        driver.findElement(By.id("username")).sendKeys("hfletcher");
        driver.findElement(By.id("email")).sendKeys("harvey.fletcher");
        driver.findElement(By.id("ldap_username")).sendKeys("hfletcher");
        driver.findElement(By.id("phone_extension")).sendKeys("525");
        driver.findElement(By.id("ddi")).sendKeys("01420383177");
        driver.findElement(By.id("job_description")).sendKeys("Software Tester");
        driver.findElement(By.id("mobile_phone_number")).sendKeys("07511749870");

        //Submit the form
        driver.findElement(By.className("btn-primary")).click();

        //Is the text on the page
        if(!driver.getPageSource().contains("New user successfully added!")){
            consistent = consistent + "\n[PASS]  The new user added text was not displayed on the page";
            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }

            consistent = consistent + "\n[PASS]  Test Passed.";
        } else {
            consistent = consistent + "\n[FAIL]  The new user added text was displayed on the page when it should not have been.";
            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }

            consistent = consistent + "\n[FAIL]  Test Failed.";
        };

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            BC.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AddUserFieldValidationsPhoneExtension(WebDriver driver, String url, String message, baseclass BC){
        //Re-load the form
        driver.get(url + "add-user");

        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Enter valid values except email address
        driver.findElement(By.id("first_name")).sendKeys("Harvey");
        driver.findElement(By.id("surname")).sendKeys("Fletcher");
        driver.findElement(By.id("username")).sendKeys("hfletcher");
        driver.findElement(By.id("email")).sendKeys("harvey.fletcher@reassured.co.uk");
        driver.findElement(By.id("ldap_username")).sendKeys("hfletcher");
        driver.findElement(By.id("phone_extension")).sendKeys("phonenumbershouldbevalid");
        driver.findElement(By.id("ddi")).sendKeys("phonenumbershouldbevalid");
        driver.findElement(By.id("job_description")).sendKeys("Software Tester");
        driver.findElement(By.id("mobile_phone_number")).sendKeys("phonenumbershouldbevalid");

        //Submit the form
        driver.findElement(By.className("btn-primary")).click();

        if(driver.findElements(By.className("alert-danger")).size() == 0){
            consistent = consistent + "\n[FAIL]  The alert box was not displayed when the phone numbers weren't valid. Can't continue this test.\n[INFO]  Database check required to see if the user was added.";

            //Exit this test but take a screenshot first.
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
                message = consistent;
                BC.writeout(message);
                return;
            } catch (Exception Exception){
                System.out.println(consistent);
                System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
            }
        } else {
            consistent = consistent + "\n[PASS]  The alert box was displayed";

            //Get the exact validation error message displayed
            String errorText = driver.findElement(By.className("alert-danger")).getText();

            //Information about phone extension?
            if(errorText.contains("The phone extension field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the phone extension field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the phone extension field, it should do - The phone extension field should only accept numbers";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about DDI?
            if(errorText.contains("The ddi field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the DDI field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the DDI field, it should do - The DDI field should only accept numbers";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }

            //Information about Mobile Phone Number?
            if(errorText.contains("The mobile phone number field is required")){
                consistent = consistent + "\n[PASS]  The error contained information on the Mobile Phone Number field";
            } else {
                consistent = consistent + "\n[FAIL]  The error did not contain information on the Mobile Phone Number field, it should do - The Mobile Phone Number field should only accept numbers";
                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }
        }

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            BC.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AddUserFieldValidationsValid(WebDriver driver, String url, String message, baseclass BC){
        //Re-load the form
        driver.get(url + "add-user");

        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Enter valid values except email address
        driver.findElement(By.id("first_name")).sendKeys("Harvey");
        driver.findElement(By.id("surname")).sendKeys("Fletcher");
        driver.findElement(By.id("username")).sendKeys("hfletcher");
        driver.findElement(By.id("email")).sendKeys("harvey.fletcher");
        driver.findElement(By.id("ldap_username")).sendKeys("hfletcher");
        driver.findElement(By.id("phone_extension")).sendKeys("525");
        driver.findElement(By.id("ddi")).sendKeys("01420383177");
        driver.findElement(By.id("job_description")).sendKeys("Software Tester");
        driver.findElement(By.id("mobile_phone_number")).sendKeys("07511749870");

        //Submit the form
        driver.findElement(By.className("btn-primary")).click();

        //Is the text on the page
        if(driver.getPageSource().contains("New user successfully added!")){
            consistent = consistent + "\n[PASS]  The new user added text was displayed on the page";
            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }

            consistent = consistent + "\n[PASS]  Test Passed.";
        } else {
            consistent = consistent + "\n[FAIL]  The new user added text was NOT displayed on the page when it should not have been.";
            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }

            consistent = consistent + "\n[FAIL]  Test Failed.";
        };

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            BC.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AdminViewUserListSearchOptions(WebDriver driver, String url, String message, baseclass BC){
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Go to the dash board
        driver.get(url + "all-users");

        //Wait for the page to load, shouldn't take more than 2 seconds.
        try {
            Thread.sleep(500);
        } catch (Exception Exception) {
            consistent = consistent + "\n[INFO]  There was an issue with waiting for .5 seconds.";
        };

        //Log the URL that loaded
        String PageLoadedURL = driver.getCurrentUrl();

        if(PageLoadedURL.matches(url + "all-users")){
            consistent = consistent + "\n[PASS]  Loaded the all-users list";
        } else {
            consistent = consistent + "\n[FAIL]  Failed to load the all-users list. Page " + PageLoadedURL + " was opened instead.";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }
        }

        //Check the panel heading text is OK
        String PanelTitlePage = driver.findElement(By.className("panel-heading")).getText();
        if(PanelTitlePage.matches("All Users")){
            consistent = consistent + "\n[PASS]  Panel heading is OK \"All Users\"";
        } else {
            consistent = consistent + "\n[FAIL]  The panel heading was incorrect, it was \"" + PanelTitlePage + "\" when it should have been \"All Users\"";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }
        }

        //Check that there is actually a search box.
        if(driver.findElements(By.id("filter")).size() >= 0){
            consistent = consistent + "\n[PASS]  Search box is present";

            //Check the search box has a title.
            String PanelSearchBox = driver.findElement(By.className("col-md-10")).getText();
            if(PanelSearchBox.contains("Quick search current results")){
                consistent = consistent + "\n[PASS]  Search box is titled correctly";
            } else {
                consistent = consistent + "\n[FAIL]  Search box is NOT titled correctly";

                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }
        } else {
            consistent = consistent + "\n[FAIL]  Search box is NOT present";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }
        }

        //Check the search box has a title.
        String FilterRecords = driver.findElement(By.className("col-md-2")).getText();
        if(FilterRecords.contains("Filter records")){
            consistent = consistent + "\n[PASS]  There is a title for [Filter Records]";

            //Check that there is actually a search box.
            if(driver.findElements(By.id("recordFilter")).size() >= 0){
                consistent = consistent + "\n[PASS]  Search box is present";

                String[] options = new String[3];

                BC.readDropDown("recordFilter", driver, options);
                System.out.println(options[0] + options[1] + options[2]);

                //Check that the expected options are in the drop down and in the right order
                if(options[0].matches("All Users") && options[1].matches("Live Users") && options[2].matches("Disabled Users")){
                    consistent = consistent + "\n[PASS]  The \"Filter Records\" drop-down matched what was expected.";
                } else {
                    consistent = consistent + "\n[FAIL]  The \"Filter Records\" drop-down did not match what was expected. Options were:";

                    int i = 0;
                    while(i < 3){
                        consistent = consistent + "\n    [" + options[i] + "]";
                        i++;
                    }
                    consistent = consistent + "\n[INFO]  It was not possible to take a screenshot of this bug. Manual check required.";
                }

            } else {
                consistent = consistent + "\n[FAIL]  Search box is NOT present";

                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }
        } else {
            consistent = consistent + "\n[FAIL]  There is NOT a title for [Filter Records] or it is not correctly worded.";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }
        }

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            BC.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AdminViewUserListCheckTableHeadings(WebDriver driver, String url, String message, baseclass BC){
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Re load the page
        driver.get(url + "all-users");

        //Get the section of the table that has the headings.
        String col1 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div/table/thead/tr/th[1]")).getText();
        String col2 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div/table/thead/tr/th[2]")).getText();
        String col3 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div/table/thead/tr/th[3]")).getText();
        String col4 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div/table/thead/tr/th[4]")).getText();
        String col5 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div/table/thead/tr/th[5]")).getText();
        String col6 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div/table/thead/tr/th[6]")).getText();

        int fail = 0;

        if(!col1.matches("User")){
            consistent = consistent + "\n[FAIL]  \"User\" option didn't match what was expected";
            consistent = consistent + "\n    Options Received:";
            consistent = consistent + "\n        \"" + col1 + "\"";
            fail = 1;
        } else if(!col2.matches("Created")){
            consistent = consistent + "\n[FAIL]  \"Created\" option didn't match what was expected";
            consistent = consistent + "\n    Options Received:";
            consistent = consistent + "\n        \"" + col2 + "\"";
            fail = 1;
        } else if(!col3.matches("Extension")){
            consistent = consistent + "\n[FAIL]  \"Extension\" option didn't match what was expected";
            consistent = consistent + "\n    Options Received:";
            consistent = consistent + "\n        \"" + col3 + "\"";
            fail = 1;
        } else if(!col4.matches("Context")){
            consistent = consistent + "\n[FAIL]  \"Context\" option didn't match what was expected";
            consistent = consistent + "\n    Options Received:";
            consistent = consistent + "\n        \"" + col4 + "\"";
            fail = 1;
        } else if(!col5.matches("Disabled")){
            consistent = consistent + "\n[FAIL]  \"Disabled\" option didn't match what was expected";
            consistent = consistent + "\n    Options Received:";
            consistent = consistent + "\n        \"" + col5 + "\"";
            fail = 1;
        } else if(!col6.matches("Action")){
            consistent = consistent + "\n[FAIL]  \"Action\" option didn't match what was expected";
            consistent = consistent + "\n    Options Received:";
            consistent = consistent + "\n        \"" + col5 + "\"";
            fail = 1;
        }

        if(fail == 1){
            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }
        } else {
            consistent = consistent + "\n[PASS]  All table headings matched the expected titles.";
        }

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            BC.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AdminViewUserListSearchFirstname(WebDriver driver, String url, String message, baseclass BC){
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Re load the page
        driver.get(url + "all-users");

        //Search for "Andrew"
        driver.findElement(By.id("filter")).sendKeys("Andrew");

        //Simplify results output
        String results = "";

        //Check results that are returned - assume we have at least 16 andrews in the company, row 17 is for andy smith.
        int i = 1;
        int fail = 0;
        String Result = "";
        while(i <= 16){
            Result = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div/table/tbody/tr[" + i + "]/td[1]")).getText();

            if(!Result.contains("Andrew")){
                consistent = consistent + "\n[FAIL]  Search filter returned an " + Result + ", which is not an \"Andrew\"";
                results = results + "\n    " + i + " -> " + Result;

                fail = 1;

                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            } else {
                consistent = consistent + "\n[PASS]  Search filter returned only names which contain \"Andrew\"";
                results = results + "\n    " + i + " -> " + Result;
            }

            i++;
        }

        consistent = consistent + "\n[INFO]  Results:" + results;

        Result = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div/table/tbody/tr[17]/td[1]")).getText();
        if(!Result.contains("Andy Smith (Mgr)")){
            consistent = consistent + "\n[FAIL]  Search filter returned " + Result + ", which is not \"Andy Smith (Mgr)\"";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }

            fail = 1;
        } else {
            consistent = consistent + "\n[PASS]  Search filter returned names which contain \"Andrew\" as firstname, including Andy Smith Mgr";
            consistent = consistent + "\n    17 -> " + Result;
        }

        if(fail == 0){
            consistent = consistent + "\n[PASS]  Firstname as search term complete, passed.";
        } else {
            consistent = consistent + "\n[FAIL]  Firstname as search term complete, failed.";
        }

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            BC.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AdminViewUserListSearchLastname(WebDriver driver, String url, String message, baseclass BC){
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Re load the page
        driver.get(url + "all-users");

        //Search for "Andrew"
        driver.findElement(By.id("filter")).sendKeys("Smith");

        //For simplicity, I want to store results in a string
        String results = "";

        //Check results that are returned - assume we have at least 16 andrews in the company, row 17 is for andy smith.
        int i = 1;
        int fail = 0;
        String Result = "";
        while(i <= 11){
            Result = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div/table/tbody/tr[" + i + "]/td[1]")).getText();

            if(!Result.contains("Smith")){
                consistent = consistent + "\n[FAIL]  Search filter returned an " + Result + ", which is not a \"Smith\"";

                //Take a screenshot for evidence
                try{
                    consistent = consistent + (BC.screenshot(driver, BC));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }

                results = results + "\n    " + i + " -> " + Result;

                fail = 1;
            } else {
                consistent = consistent + "\n[PASS]  Search filter returned only names which contain \"Smith\"";
                results = results + "\n    " + i + " -> " + Result;
            }

            i++;
        }

        consistent = consistent + "\n[INFO]  Results:" + results;

        if(fail == 0){
            consistent = consistent + "\n[PASS]  Lastname as search term complete, passed.";
        } else {
            consistent = consistent + "\n[FAIL]  Lastname as search term complete, failed.";
        }

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            BC.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AdminViewUserListSearchFirstnameLastname(WebDriver driver, String url, String message, baseclass BC){
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Re load the page
        driver.get(url + "all-users");

        //Search for "Andrew"
        driver.findElement(By.id("filter")).sendKeys("Andrew Smith");

        //Check results that are returned - assume we have at least 16 andrews in the company, row 17 is for andy smith.
        int i = 1;
        String Result = "";

        try{
            Result = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div/table/tbody/tr[1]/td[1]")).getText();
        } catch (NoSuchElementException Exception){
            consistent = consistent + "\n[FAIL]  Search filter returned only names which contain \"Andrew Smith\", or did not return any results.";
            consistent = consistent + "\n    " + i + " -> " + Result;
        }

        if(!Result.contains("Andrew Smith")){
            consistent = consistent + "\n[FAIL]  Search filter returned" + Result + ", which is not a \"Andrew Smith\", or did not return any results.";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (BC.screenshot(driver, BC));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }
        } else {
            consistent = consistent + "\n[PASS]  Search filter returned only names which match \"Andrew Smith\"";
            consistent = consistent + "\n    " + i + " -> " + Result;
        }

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            BC.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AdminViewUserListSearchUsername(WebDriver driver, String url, String message, baseclass BC){
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object(){}.getClass().getEnclosingMethod().getName();

        //Re load the page
        driver.get(url + "all-users");

        //Setup a list for usernames search terms
        String[] usernamesSearch;
        usernamesSearch = new String[12];

        //Simplify the output in a results string
        String results = "";

        //Build a list of usernames to search for
        int i = 0;
        while(i < 12){
            String[] Credentials = BC.userCredentials(i, 1);
            usernamesSearch[i] = Credentials[0];
            i++;
        }

        i = 0;

        //For each user group, search.
        while (i < usernamesSearch.length){
            //Search for "Username"
            driver.findElement(By.id("filter")).sendKeys(usernamesSearch[i]);

            //Is there a result?
            String Result = "";

            try{
                Result = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div/table/tbody/tr[1]/td[1]")).getText();

                //What is the fullname of the user
                String fullname = BC.usernameToFirstname(usernamesSearch[i]);

                if(Result.contains(fullname) == true){
                    consistent = consistent + "\n[PASS]  Search filter returned only names which match \"" + usernamesSearch[i] + "\"";
                    results = results + "\n    " + i + " -> " + Result;
                } else {
                    consistent = consistent + "\n[FAIL]  Search filter returned " + Result + ", which is not a \"" + usernamesSearch[i] + "\", or did not return any results.";

                    //Take a screenshot for evidence
                    try{
                        consistent = consistent + (BC.screenshot(driver, BC));
                    } catch (Exception Exception){
                        Exception.printStackTrace();
                    }

                    //Write what we got to the results anyway
                    results = results + "\n    " + i + " -> " + Result;
                }
            } catch (NoSuchElementException Exception){
                consistent = consistent + "\n[FAIL]  Search filter did not return any results.";
                consistent = consistent + "\n    " + i + " -> " + Result;
            }

            //Clear the field
            driver.findElement(By.id("filter")).clear();

            //Increment i
            i++;
        }

        //Add the results to the output
        consistent = consistent + "\n[INFO]    Results:" + results;

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            BC.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }
}
