package CRM_Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by hfletcher on 11/12/2017.
 */
public class dev_380_list_create_new_user {

    static void MainClass(WebDriver driver, String url, String message, baseclass WL) {
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
    }

    static void AdminUserOpenForm(WebDriver driver, String url, String message, baseclass WL){
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
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception Exception){
                E.printStackTrace();
            }
        }

        //Take a screenshot for evidence
        try{
            consistent = consistent + (WL.screenshot(driver, WL) + " passed but keeping this for evidence or later review");
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
                    consistent = consistent + (WL.screenshot(driver, WL));
                } catch (Exception E){
                    E.printStackTrace();
                }

                //Write to the log file, if it fails, print out to System.out and add a warning message
                try{
                    message = consistent;
                    WL.writeout(message);
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
                WL.writeout(message);
            } catch (Exception Exception){
                System.out.println(consistent);
                System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
            }

        } catch (Exception E){
            //Add failure message to the consistent log
            consistent = consistent + "\n[FAIL]  Couldn't select the [Users] drop down menu";

            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception Exception){
                E.printStackTrace();
            }

            consistent = consistent + "\n[FAIL]  Test failed.";

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

    static void AddUserFieldValidationsBlankForm(WebDriver driver, String url, String message, baseclass WL) {
        //Declare that the new test was started, write the test name
        String consistent = "\n[INFO]  Started test " + new Object() {
        }.getClass().getEnclosingMethod().getName();

        //Try and submit the form before putting anything in it
        try {
            driver.findElement(By.className("btn-primary")).click();

            if (driver.findElements(By.className("alert-danger")).size() == 0) {
                consistent = consistent + "\n[FAIL]  The alert box was not displayed. Can't continue this test.";

                //Exit this test but take a screenshot first.
                try {
                    consistent = consistent + (WL.screenshot(driver, WL));
                    message = consistent;
                    WL.writeout(message);
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
                } catch (Exception Exception) {
                    Exception.printStackTrace();
                }
            }
        } catch (Exception E) {
            consistent = consistent + "\n[FAIL]  Add user form was submitted when it was blank, or the error messages displayed were incorrect.";
            //Take a screenshot for evidence
            try {
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception Exception) {
                Exception.printStackTrace();
            }
        }

        consistent = consistent + "\n[PASS]  Test passed.";

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            WL.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AddUserFieldValidationsWhiteSpaceAllFields(WebDriver driver, String url, String message, baseclass WL){
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
                    consistent = consistent + (WL.screenshot(driver, WL));
                    message = consistent;
                    WL.writeout(message);
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }
        } catch (Exception E){
            consistent = consistent + "\n[FAIL]  Add user form was submitted when it was blank, or the error messages displayed were incorrect.";
            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }
        }

        consistent = consistent + "\n[PASS]  Test passed.";

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            WL.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AddUserFieldValidationsSpecialChars(WebDriver driver, String url, String message, baseclass WL){
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
                    consistent = consistent + (WL.screenshot(driver, WL));
                    message = consistent;
                    WL.writeout(message);
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
            }
        } catch (Exception E){
            consistent = consistent + "\n[FAIL]  Add user form was submitted when it was blank, or the error messages displayed were incorrect.";
            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }
        }

        consistent = consistent + "\n[PASS]  Test passed.";

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            WL.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AddUserFieldValidationsEmailAddress(WebDriver driver, String url, String message, baseclass WL){
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
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }

            consistent = consistent + "\n[PASS]  Test Passed.";
        } else {
            consistent = consistent + "\n[FAIL]  The new user added text was displayed on the page when it should not have been.";
            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }

            consistent = consistent + "\n[FAIL]  Test Failed.";
        };

        //Write to the log file, if it fails, print out to System.out and add a warning message
        try{
            message = consistent;
            WL.writeout(message);
        } catch (Exception Exception){
            System.out.println(consistent);
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }
    }

    static void AddUserFieldValidationsPhoneExtension(WebDriver driver, String url, String message, baseclass WL){
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
                consistent = consistent + (WL.screenshot(driver, WL));
                message = consistent;
                WL.writeout(message);
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
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
                    consistent = consistent + (WL.screenshot(driver, WL));
                } catch (Exception Exception){
                    Exception.printStackTrace();
                }
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

    static void AddUserFieldValidationsValid(WebDriver driver, String url, String message, baseclass WL){
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
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }

            consistent = consistent + "\n[PASS]  Test Passed.";
        } else {
            consistent = consistent + "\n[FAIL]  The new user added text was NOT displayed on the page when it should not have been.";
            //Take a screenshot for evidence
            try{
                consistent = consistent + (WL.screenshot(driver, WL));
            } catch (Exception Exception){
                Exception.printStackTrace();
            }

            consistent = consistent + "\n[FAIL]  Test Failed.";
        };

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
