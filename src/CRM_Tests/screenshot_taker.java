package CRM_Tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Date;

/**
 * Created by hfletcher on 13/03/2018.
 */
public class screenshot_taker extends ClassGlobals{
    public String screenshot(WebDriver driver){

        String output = "";

        try{
            //What's the date
            Date datetime = new Date();

            //What do we want to save the file as?
            String FileName = ClassGlobals.OutputFolder + "screenshots/shot_" + datetime.toString().replace(" ","_").replace(":","_") + ".png";

            //Take the screenshot
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(FileName));

            output = "Screenshot taken and stored at " + FileName;
        }catch (Exception e){
            output = "An unexpected error has occured. Details: " + e.getClass().getSimpleName();
        }

        System.out.println(output);
        return output;
    }
}
