import com.sun.javafx.PlatformUtil;
import com.sun.tools.javac.util.Log;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlightBookingTest {

    static Logger logger = Logger.getLogger(FlightBookingTest.class.getName());
    WebDriver driver = new ChromeDriver();

    @FindBy(id = "OneWay")
    private WebElement journeyType;

    @FindBy(id = "FromTag")
    private WebElement fromLocation;

    @FindBy(id = "toTag")
    private WebElement toLocation;

    @FindBy(xpath = "//*[@id='ui-datepicker-div']/div[1]/table/tbody/tr[3]/td[7]/a")
    private WebElement datePicker;

    @FindBy(id = "SearchBtn")
    private WebElement search;



    @Test
    public void testThatResultsAppearForAOneWayJourney() {

        CommonHelper.setDriverPath();
        driver.get("https://www.cleartrip.com/");
        CommonHelper.waitForPageLoad(driver);
        journeyType.click();

        fromLocation.clear();
        fromLocation.sendKeys("Bangalore");

        //wait for the auto complete options to appear for the origin

        waitFor(2000);
        try {
            List<WebElement> originOptions = driver.findElement(By.id("ui-id-1")).findElements(By.tagName("li"));
            originOptions.get(0).click();

            toLocation.clear();
            toLocation.sendKeys("Delhi");

            //wait for the auto complete options to appear for the destination

            waitFor(2000);
            //select the first item from the destination auto complete list
            List<WebElement> destinationOptions = driver.findElement(By.id("ui-id-2")).findElements(By.tagName("li"));
            destinationOptions.get(0).click();

            datePicker.click();

            //all fields filled in. Now click on search
            search.click();

            CommonHelper.waitForPageLoad(driver);
            //verify that result appears for the provided journey search
            Assert.assertTrue(isElementPresent(By.className("searchSummary")), "No results for search");
        }

        catch (NoSuchElementException e){

              logger.log(Level.ALL, "Element not found");
        }
        //close the browser
        driver.quit();

    }


    private void waitFor(int durationInMilliSeconds) {
        try {
            Thread.sleep(durationInMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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


}
