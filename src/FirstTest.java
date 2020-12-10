import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","D:\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void firstTest() throws InterruptedException {

        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),"Cannot find search element",3);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),"Java","Cannot find search input",3);

        waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),"Error finding JAVA", 10);

        Thread.sleep(2000);
    }

    @Test
    public void testCompareArticleTitle() throws InterruptedException {

        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Cannot find search element", 3);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), "Java", "Cannot find search input", 3);

        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"), "Error finding JAVA", 3);
        WebElement title_element = waitForElementPresent(By.id("org.wikipedia:id/view_page_title_text"),"Cannot find article title",10);
        String article_title = title_element.getAttribute("text");

        Assert.assertEquals("We see unexpected Article title","Java (programming language)", article_title);
    }
    @Test
    public void testCancelSearch()
    {
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"), "Cannot find search element", 3);

        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),"Java","Cannot find search input",3);

        waitForElementAndClear(By.id("org.wikipedia:id/search_src_text"),"Cannot find search field",5);

        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"), "Cannot find close cross element", 3);

        waitForElementNotPresent(By.id("org.wikipedia:id/search_close_btn"),"X still visible on page",3);
    }

    @Test
    public void testSearchFieldText()
    {
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"), "Cannot find search element", 3);
        assertElementHasText(By.id("org.wikipedia:id/search_src_text"),"Search…","Unexpected search hint");

    }

    @Test
    public void testSearchSeveralArtAndCancel()
    {
        //ткнём в поиск
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"), "Cannot find search element", 3);
        //поищем слово apple
        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),"apple","Cannot find search input",3);
        //убедимся, что контейнеров для статей хотя бы больше 1
        Assert.assertTrue("Few articles found", countOfElements(By.id("org.wikipedia:id/page_list_item_container"))>1);
        //ткнём крест
        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"), "Cannot find close cross element", 3);
        //убедимся, что все контейнеры исчезли
        Assert.assertTrue("Articles still visible", countOfElements(By.id("org.wikipedia:id/page_list_item_container")) == 0);
    }

    @Test
    public void testSearchResultsText()
    {
        String search_word="Java";
        //ткнём в поиск
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"), "Cannot find search element", 3);
        //поищем слово
        waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"),search_word,"Cannot find search input",3);
        //проверим, что в каждом результате есть слово
        testTextSearchResults(By.id("org.wikipedia:id/page_list_item_title"),search_word,"Not all articles contains search text");
    }

    private void testTextSearchResults(By by, String text, String error_text)
    {
        List<WebElement> list = driver.findElements(by);
        for(WebElement el : list)
        {
            System.out.println(el.getAttribute("text"));
            Assert.assertTrue(error_text,el.getAttribute("text").contains(text));
        }
    }

    private int countOfElements(By by)
    {
        int count = driver.findElements(by).size();
        return count;
    }


    private void assertElementHasText(By by, String text, String error_message)
    {
        WebElement element = waitForElementPresent(by,"Element " + by + " not found");
        String element_text = element.getAttribute("text");
        Assert.assertEquals(error_message,text,element_text);
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSec)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.withMessage(error_message+"\n");

        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private  WebElement waitForElementAndClick(By by, String error_message, long timeoutInSec)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSec);
        element.click();
        return element;
    }



    private  WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSec)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSec);
        element.sendKeys(value);
        return element;
    }

    private  WebElement waitForElementAndClear(By by, String error_message, long timeoutInSec)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSec);
        element.clear();
        return element;
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSec)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.withMessage(error_message+"\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
    private WebElement waitForElementPresent(By by, String error_message)
    {
        return waitForElementPresent(by, error_message, 5);
    }
}
