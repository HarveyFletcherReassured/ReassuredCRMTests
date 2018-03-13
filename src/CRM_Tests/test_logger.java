package CRM_Tests;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by hfletcher on 13/03/2018.
 */
public class test_logger extends ClassGlobals {

    public String WriteOut(String message){
        String output = "";

        try{
            //This is what will be written out to the log file
            byte[] WriteContents = (
                ClassGlobals.currentdate
                + ":    "
                + message.replace("\n", System.getProperty("line.separator"))
                + System.getProperty("line.separator")
                + System.getProperty("line.separator")
            ).getBytes();

            //Write to the log file
            Files.write(Paths.get(ClassGlobals.OutputFolder + "logfile.txt"), WriteContents, StandardOpenOption.APPEND);

            output = "Wrote to log file.";
        } catch (Exception e){
            output = "An unexpected error has occured. Details: " + e.getClass().getSimpleName();
        }

        return output;
    }
}
