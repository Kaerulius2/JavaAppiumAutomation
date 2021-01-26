package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends  MainPageObject{

    private static final String
            STEP_LEARN_MORE_LINK = "id:Learn more about Wikipedia",
            STEP_NEW_WAYS_TO_EXPLORE_TEXT = "id:New ways to explore",
            STEP_ADD_OR_EDIT_LANG_LINK = "id:Add or edit preferred languages",
            STEP_LEARN_MORE_DATA_COLL_LINK = "id:Learn more about data collected",
            NEXT_LINK = "id:Next",
            GET_STARTED_BUTTON = "id:Get started",
            SKIP = "id:Skip";


    public WelcomePageObject(AppiumDriver driver)
    {
        super(driver);
    }
    //в текущей версии нет accessibility id  у этого элемента, но если пощупать поиском, то элемент находится
    public void waitForLearnMoreLink()
    {
        this.waitForElementPresent(STEP_LEARN_MORE_LINK,"Cannot find 'Learn more about Wikipedia' link",10);
    }

    //appium 17 мне и тут не предлагает accessibility id, но если пощупать поиском по id, кнопка находится
    public void clickNextButton()
    {
        this.waitForElementAndClick(NEXT_LINK,"Cannot find and click 'Next' link",10);
    }

    public void clickGetStartedButton()
    {
        this.waitForElementAndClick(GET_STARTED_BUTTON,"Cannot find and click 'Get started' link",10);
    }

    public void waitForNewWayToExplore()
    {
        this.waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_TEXT,"Cannot find 'New ways to explore' link",10);
    }

    public void waitForAddOrEditPreferredLangText()
    {
        this.waitForElementPresent(STEP_ADD_OR_EDIT_LANG_LINK,"Cannot find 'Add or edit preferred languages' link",10);
    }

    public void waitForLearnMoreAboutDataCollectedText()
    {
        this.waitForElementPresent(STEP_LEARN_MORE_DATA_COLL_LINK,"Cannot find 'Learn more about data collected' link",10);
    }

    public void clickSkipLink()
    {
        this.waitForElementAndClick(SKIP,"Cannot find and click Skip link",6);
    }
}
