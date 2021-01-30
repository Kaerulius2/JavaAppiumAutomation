package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver){
        this.driver = driver;
    }

    public void clickElementToTheRightUpperCorner(String locator, String error_message)
    {
        WebElement element = this.waitForElementPresent(locator + "/..", error_message);
        int right_x = element.getLocation().getX();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;
        int width = element.getSize().getWidth();

        int point_to_click_x = (right_x + width) - 3;
        int point_to_click_y = middle_y;

        TouchAction action = new TouchAction(driver);
        action.tap(PointOption.point(point_to_click_x,point_to_click_y)).perform();
    }

    public void testTextSearchResults(By by, String text, String error_text)
    {
        List<WebElement> list = driver.findElements(by);
        for(WebElement el : list)
        {
            System.out.println(el.getAttribute("text"));
            assertTrue(error_text,el.getAttribute("text").contains(text));
        }
    }

    public int countOfElements(String locator)
    {
        int count = driver.findElements(this.getLocatorByString(locator)).size();
        return count;
    }

    public void swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width/2;
        int start_y = (int)(size.height*0.8);
        int end_y = (int)(size.height*0.2);

        action.press(PointOption.point(x,start_y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe))).moveTo(PointOption.point(x,end_y)).release().perform();
    }

    public void swipeUpQuick()
    {
        swipeUp(200);
    }

    public void swipeUpToFindElement(String locator, String error_message, int max_swipes)
    {
        int already_swiped = 0;
        while(driver.findElements(this.getLocatorByString(locator)).size()==0)
        {
            if(already_swiped>max_swipes){
                waitForElementPresent(locator,"Cannot find element by swiping up. \n" + error_message,0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public void swipeUpTillElementAppear(String locator, String error_message, int max_swipes)
    {
        int already_swiped = 0;

        while(!this.isElementLocatedOntheScreen(locator)){
            if(already_swiped>max_swipes){
                Assert.assertTrue(error_message,this.isElementLocatedOntheScreen(locator));
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public boolean isElementLocatedOntheScreen(String locator)
    {
        int element_location_by_y = this.waitForElementPresent(locator,"Cannot find element by locator",2).getLocation().getY();
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        return element_location_by_y < screen_size_by_y;
    }

    public void assertElementHasText(String locator, String text, String error_message)
    {
        WebElement element = waitForElementPresent(locator,"Element " + locator + " not found");
        String element_text = element.getAttribute("text");
        Assert.assertEquals(error_message,text,element_text);
    }

    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSec)
    {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.withMessage(error_message+"\n");

        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSec)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSec);
        element.click();
        return element;
    }



    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSec)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSec);
        element.sendKeys(value);
        return element;
    }

    public  WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSec)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSec);
        element.clear();
        return element;
    }

    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSec)
    {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.withMessage(error_message+"\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
    public WebElement waitForElementPresent(String locator, String error_message)
    {
        return waitForElementPresent(locator, error_message, 5);
    }

    public void swipeElementToLeft(String locator, String error_message)
    {
        WebElement element = waitForElementPresent(locator, error_message, 10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);


        action.press(PointOption.point(right_x,middle_y));
        action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)));
        if(Platform.getInstance().isAndroid()){
            action.moveTo(PointOption.point(left_x,middle_y));
        }else{
            int offset_x = (-1 * element.getSize().getWidth());
            action.moveTo((PointOption.point(offset_x, 0)));
        }

        action.release();
        action.perform();
    }

    public int getAmountOfElements(String locator)
    {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(String locator, String error_message)
    {
        int amount_of_elements = getAmountOfElements(locator);
        if(amount_of_elements>0){
            String default_message = "An element " + locator + " supposed to be not present.";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public void assertElementPresent(String locator, String error_message)
    {
        int amount_of_elements = getAmountOfElements(locator);
        if(amount_of_elements==0){
            String default_message = "An element " + locator + " supposed to be present.";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public String waitForElementAndGetAttr(String locator, String attr, String error_message, long timeoutInSec)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSec);
        return element.getAttribute(attr);
    }

    private By getLocatorByString(String locator_with_type)
    {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"),2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if(by_type.equals("xpath")){
            return By.xpath(locator);
        }else if(by_type.equals("id")){
            return By.id(locator);
        }else{
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator);
        }
    }
}
