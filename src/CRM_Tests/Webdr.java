package com.example.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class Webdr {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setPlatform(Platform.WINDOWS);
        capability.setCapability(ChromeOptions.CAPABILITY, options);
        String nodeURL = "http://10.168.206.234:4445/wd/hub";
        System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
        driver = new RemoteWebDriver(new URL(nodeURL), capability);
        baseUrl = "http://test.reassuredpensions.co.uk/";
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void test1(){
        driver.get(baseUrl);
        driver.findElement(By.id("UserUsername")).clear();
        driver.findElement(By.id("UserUsername")).sendKeys("swilkin");
        driver.findElement(By.id("UserPassword")).clear();
        driver.findElement(By.id("UserPassword")).sendKeys("Tstpasswd@Adrian");
        new Select(driver.findElement(By.id("ghostuser"))).selectByVisibleText("Selenium");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        };

        Actions action = new Actions(driver);
        WebElement qa = driver.findElement(By.xpath("//*[@id='mainmenulist']/li[3]"));

        action.moveToElement(qa).perform();

        try {
            Thread.sleep(200);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        };

        driver.findElement(By.xpath("//*[@id='mainmenulist']/li[3]/ul/li[1]/a")).click();


        try {
            Thread.sleep(5000);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        };
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
