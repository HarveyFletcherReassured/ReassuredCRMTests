package CRM_Tests;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

/**
 * Created by hfletcher on 13/03/2018.
 */
public class Login_Page_No_Username_No_Password extends ClassGlobals {

    @Test
    public void main(){
        try{
            //Open the login page.
            driver.get(environment);

            //Ensure that the GhostUser drop down has not been selected.
            Select GhostUser = new Select(driver.findElement(By.id("ghostuser")));
            GhostUser.selectByVisibleText("");

            //Select login
            driver.findElement(By.id("UserLoginForm")).submit();

            //Fail if the page has changed from the login page.
            if(!driver.getCurrentUrl().equals(environment + "/users/login?url=users%2Flogin")){
                throw new Exception();
            }

            //Is there an "Incorrect Username Or Password" error?
            if(driver.findElements(By.id("flashMessage")).size() <= 0){
                throw new Exception();
            }

            //Close the window
            driver.close();
        } catch (Exception e){
            System.out.println(take_screenshot.screenshot(driver));
            System.out.println(write_log.WriteOut(e.getClass().getSimpleName()));

            //Close the window so we don't have an orphaned session
            driver.close();
        }
    }
}
