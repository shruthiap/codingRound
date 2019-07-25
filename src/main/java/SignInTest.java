import com.sun.javafx.PlatformUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignInTest {

    WebDriver driver = new ChromeDriver();

    @FindBy(linkText = "Your trips")
    private WebElement yourTrips;

    @FindBy(id = "SignIn")
    private WebElement signIn;

    @FindBy(id = "signInButton")
    private WebElement signInButton;

    @FindBy(id = "errors1")
    private WebElement formErrors;

    @Test
    public void shouldThrowAnErrorIfSignInDetailsAreMissing() {

        CommonHelper.setDriverPath();

        driver.get("https://www.cleartrip.com/");
        CommonHelper.waitForPageLoad(driver);

        yourTrips.click();
        signIn.click();

        signInButton.click();

        String errors1 = formErrors.getText();
        Assert.assertTrue(errors1.contains("There were errors in your submission"));
        driver.quit();
    }

}
