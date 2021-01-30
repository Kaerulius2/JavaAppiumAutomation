package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject
{

    protected static String
            TITLE,
            TITLE2,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            EXISTING_LIST_TPL,
            CLOSE_DIALOG_CROSS;

    //----------TEMPLATE METHODS------------
    private static String getFolderElement(String folder)
    {
        return EXISTING_LIST_TPL.replace("{FOLDER}", folder);
    }
    //----------TEMPLATE METHODS------------

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement(int num)
    {
        if(num==1) {
            return this.waitForElementPresent(TITLE, "Cannot find first article title", 15);
        }else {
            return this.waitForElementPresent(TITLE2, "Cannot find second article title", 15);
        }
    }
    public String getiOSArticleTitle(int num)
    {
        WebElement title_element = waitForTitleElement(num);
        return title_element.getAttribute("name");
    }

    public String getAndroidArticleTitle()
    {
        WebElement title_element = waitForTitleElement(1);
        return title_element.getAttribute("text");

    }

    public void swipeToFooter()
    {
        if(Platform.getInstance().isAndroid()){
            this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 40);
        }else {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT,"Cannot find the end of article", 40);
        }

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

    public void addArticleToMySaved() {
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,"Cannot find option to add to list",5);
    }

    public void closeLoginToSyncDialog() {
        this.waitForElementAndClick(CLOSE_DIALOG_CROSS, "Cannot find cross (x) to close dialog",5);
    }
}
