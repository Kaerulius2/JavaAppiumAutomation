package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject
{

    private static final String
            TITLE = "id:org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "xpath://*[@class='android.widget.TextView'][@text='View page in browser']",
            OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "xpath://*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
            EXISTING_LIST_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{FOLDER}']";

    //----------TEMPLATE METHODS------------
    private static String getFolderElement(String folder)
    {
        return EXISTING_LIST_TPL.replace("{FOLDER}", folder);
    }
    //----------TEMPLATE METHODS------------

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(TITLE, "Cannot find article title",15);
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter()
    {
        this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 20);
    }

    public void addArticleToMyList(String name_of_folder)
    {
        this.waitForElementAndClick(OPTIONS_BUTTON, "Cannot find options", 5);

        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 7);

        this.waitForElementAndClick(ADD_TO_MY_LIST_OVERLAY,"Cannot find GOT IT button",5);

        this.waitForElementAndClear(MY_LIST_NAME_INPUT,"Cannot find input to set name of list",5);

        this.waitForElementAndSendKeys(MY_LIST_NAME_INPUT, name_of_folder,"Cannot put text into Name of this list", 5);

        this.waitForElementAndClick(MY_LIST_OK_BUTTON, "Cannot press OK button", 5);
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(CLOSE_ARTICLE_BUTTON, "Cannot find or press X button", 5);
    }


    public void assertArticleHasTitle() {
        this.assertElementPresent(TITLE,"Article has no title");
    }

    public void addArticleToExistingList(String name_of_folder)
    {
        this.waitForElementAndClick(OPTIONS_BUTTON, "Cannot find options", 5);

        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 7);

        //тут появится выбор - новый лист, или имеющийся - нужно кликнуть по имеющемуся
        this.waitForElementAndClick(getFolderElement(name_of_folder),"Cannot find existing folder to save article",5);

    }
}
