package pages;

import libs.ConfigData;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class HomePage {
    private final WebDriverWait webDriverWait;
    WebDriver driver;
    Logger logger;
    final String errorElement = "Can't work with element ";
    final String errorCoordinate = "Can't get coordinate of element ";
    final String errorSubScreenshot = "Can't get sub screenshot of element ";
    final String errorCopySubScreenshot = "Can't copy sub screenshot of element to disk ";
    final String baseUrl = "https://en.wikipedia.org/wiki/Main_Page";



    public HomePage(WebDriver exterDriver) throws IOException {
        this.driver = exterDriver;
        logger = Logger.getLogger(getClass());
        webDriverWait = new WebDriverWait(driver, 30);
        PageFactory.initElements(driver, this);

    }

    /**
     * Method opens Browser And HomePage
     */
    public void openBrowserAndHomePage() {
        try {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            driver.get(ConfigData.getCfgValue("BASE_URL"));
            if (driver.getCurrentUrl().toString().equals(baseUrl) == true) {
                logger.info("Home page was opened");
            } else logger.error("Home page wasn't opened");
        } catch (Exception e) {
            logger.error("Can not work with HomePage");
            Assert.fail("Can not work with HomePage");
        }
    }

    /**
     * Method gets page screenshot, gets element coordinates, gets element screenshot
     * by coordinates and copies element screenshot to disk
     * @param xPathElement
     * @param nameScreenshot
     * @throws IOException
     */
    public void getOnlyElementScreenshot(String xPathElement, String nameScreenshot) throws IOException {

            // Get entire page screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage fullImg = ImageIO.read(screenshot);

            // Get the location of element on the page
            Point point= new Point(0,0);
            try {
                point= driver.findElement(By.xpath(xPathElement)).getLocation();
                logger.info("Location was got: " + point);
            }catch (Exception e){
                logger.error("Can't work with element ");
                Assert.fail(errorElement);
            }

            // Get width of the element
            int elWidth=0;
            try {
                elWidth=driver.findElement(By.xpath(xPathElement)).getSize().getWidth();
                logger.info("Element width was got: " + elWidth);
            }catch (Exception e){
                logger.error(errorCoordinate);
                Assert.fail(errorCoordinate);
            }

            // Get height of the element
            int elHeight =0;
            try {
                elHeight=driver.findElement(By.xpath(xPathElement)).getSize().getHeight();
                logger.info("Element height was got: " + elHeight);
            }catch (Exception e){
                logger.error(errorCoordinate);
                Assert.fail(errorCoordinate);
            }
            // Crop the entire page screenshot to get only element screenshot
            try {
                BufferedImage eleScreenshot=fullImg.getSubimage(point.getX(), point.getY(), elWidth, elHeight);
                ImageIO.write(eleScreenshot, "png", screenshot);
                logger.info("Element screenshot was got: " + screenshot);
            }catch (Exception e){
                logger.error(errorSubScreenshot);
                Assert.fail(errorSubScreenshot);
            }
            // Copy the element screenshot to disk
            try {
                File screenshotLocation = new File("D:\\images\\" + nameScreenshot + ".png");
                FileUtils.copyFile(screenshot, screenshotLocation);
                logger.info("Element screenshot was copied to disk: " + screenshotLocation);
            }catch (Exception e){
                logger.error(errorCopySubScreenshot);
                Assert.fail(errorCopySubScreenshot);
            }
    }

    /**
     * Method does Close Browser
     */
    public void closeBrowser() {
        driver.quit();
        logger.info("Browser was closed");
    }
}
