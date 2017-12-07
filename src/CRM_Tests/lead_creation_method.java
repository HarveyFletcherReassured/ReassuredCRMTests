package CRM_Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

/**
 * Created by hfletcher on 07/09/2017.
 */
public class lead_creation_method {

    static void TheTest(WebDriver driver, String url, String message, baseclass WL, String daterequired){
        //Close any open chrome windows maybe from previous tests.
        try{
            driver.quit();
            driver = baseclass.setUp();
            message = "[INFO]  Started a new chrome window.";
        } catch (Exception E){
            System.out.println("Something went wrong.");
        }

        //Set the user type to a user that can add leads.
        String username = "hfletcher";

        //Sign in to CRM
        WL.switchusr(username, driver, url, WL, message);

        //Select the "Add lead" link
        try{
            driver.findElement(By.xpath("//*[@id=\"mainmenulist\"]/li[11]/a")).click();
            message = "[PASS]  Selected 'Add Lead' link from the main menu bar.";
        } catch (Exception E){
            message = "[FAIL]  Failed to select 'Add Lead' link from the main menu bar.";
        }

        try{
            WL.writeout(message);
        } catch (IOException E){
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }

        //Fill out the lead details form
        try{
            message = "title_1";
            driver.findElement(By.id("title_1")).sendKeys("MR");
            message = "forename_1";
            driver.findElement(By.id("forename_1")).sendKeys("Boaty");
            message = "surname_1";
            driver.findElement(By.id("surname_1")).sendKeys("McBoatface");
            message = "dob_1";
            driver.findElement(By.id("dob_1")).sendKeys(daterequired);
            message = "postcode";
            driver.findElement(By.id("postcode")).sendKeys("RG214hg");
            message = "sum_assured";
            driver.findElement(By.id("sum_assured")).sendKeys("10000");
            message = "term";
            driver.findElement(By.id("term")).sendKeys("30");

            try{
                message = "[PASS]  Filled out the add lead form OK.";
                WL.writeout(message);
            } catch (IOException WriteError){
                System.out.println("Failed to write to logfile" + System.getProperty("line.separator"));
            }
        } catch (Exception FormFilloutExcept){
            try{
                message = ("[FAIL]  Error filling out the " + message + " field on the leads form");
                WL.writeout(message);
            } catch (IOException E){
                System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
            }
        }

        try {
            //Set the lead gender
            Select select = new Select(driver.findElement(By.id("gender_1")));
            select.selectByVisibleText("Male");
            message = "[PASS]  Successfully set the lead gender on add leads form.";
        } catch (Exception E){
            message = "[FAIL]  Setting lead gender failed on add leads form.";
        }

        try{
            WL.writeout(message);
        } catch (IOException E){
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }

        try {
            //Set the lead smoker status
            Select select = new Select(driver.findElement(By.id("smoker_1")));
            select.selectByVisibleText("No");
            message = "[PASS]  Successfully set the lead smoker_status on add leads form.";
        } catch (Exception E){
            message = "[FAIL]  Setting lead smoker_status failed on add leads form.";
        }

        try{
            WL.writeout(message);
        } catch (IOException E){
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }

        try {
            //Set the lead life
            Select select = new Select(driver.findElement(By.id("life")));
            select.selectByVisibleText("Yes");
            message = "[PASS]  Successfully set the lead life on add leads form.";
        } catch (Exception E){
            message = "[FAIL]  Setting lead life failed on add leads form.";
        }

        try{
            WL.writeout(message);
        } catch (IOException E){
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }

        try {
            //Set the lead cic
            Select select = new Select(driver.findElement(By.id("cic")));
            select.selectByVisibleText("No");
            message = "[PASS]  Successfully set the lead cic on add leads form.";
        } catch (Exception E){
            message = "[FAIL]  Setting lead cic failed on add leads form.";
        }

        try{
            WL.writeout(message);
        } catch (IOException E){
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }

        try {
            driver.findElement(By.xpath("//*[@id=\"LeadAddForm\"]/table/tbody/tr[7]/td/div/input")).click();
            message = "[PASS]  Submitted the form";
        } catch (Exception E){
            message = "[FAIL]  Submit button was missing from the form.";
        }

        try{
            WL.writeout(message);
        } catch (IOException E){
            System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
        }

        //Wait a second so we are sure that everything on the page loads OK.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        };

        try{
            //Get the new lead ID and log it
            String NewLead = driver.findElement(By.xpath("//*[@id=\"body-column\"]/h2")).getText();
            NewLead = NewLead.substring(16, 23);

            try{
                message = "[INFO]  Added new lead. Lead ID is: " + NewLead;
                WL.writeout(message);
            } catch (IOException E){
                System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
            }

            try{
                Files.write(Paths.get("C:/CRM_Tests/src/CRM_Tests/leads.txt"), (NewLead + System.getProperty("line.separator")).getBytes(), StandardOpenOption.APPEND);
            } catch (IOException WriteError){
                System.out.println("Failed to write to leadfile" + System.getProperty("line.separator"));
            }

            driver.quit();
        } catch (Exception GetLeadIDFail){
            try{
                message = "[FAIL]  Filling out the add lead form failed for an unexpected error. Error code: " + GetLeadIDFail.getClass().getSimpleName();
                WL.writeout(message);
            } catch (IOException E){
                System.out.println("[WARN]  Couldn't write to the log file, continuing anyway...");
            }
        }
    }
}
