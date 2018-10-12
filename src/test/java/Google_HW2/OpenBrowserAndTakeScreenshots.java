package Google_HW2;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;

import java.io.IOException;


public class OpenBrowserAndTakeScreenshots {
    WebDriver driver = new ChromeDriver();
    HomePage homePage = new HomePage(driver);

    public OpenBrowserAndTakeScreenshots() throws IOException {
    }

    @Test
    public void OpenBrowserAndTakeScreenshots() throws IOException {

        homePage.openBrowserAndHomePage();
        homePage.getOnlyElementScreenshot("//div[@class='dyk-img']/div","screenshot1");
        homePage.getOnlyElementScreenshot("//div[@class='itn-img']/div","screenshot2");
    }

    @After
    public void tearDown() { homePage.closeBrowser();
    }
}