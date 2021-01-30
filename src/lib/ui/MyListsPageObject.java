package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.By;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL;

    private static String getFolderXpathByName(String folderName)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}",folderName);
    }

    private static String getSavedArticleXpathByTitle(String title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}",title);
    }

    public MyListsPageObject(AppiumDriver driver){
        super(driver);
    }

    public void openFolderByName(String name_of_folder)
    {
        this.waitForElementAndClick(getFolderXpathByName(name_of_folder), "Cannot find folder by name: " + name_of_folder, 5);
    }

    public void swipeByArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        this.swipeElementToLeft(getSavedArticleXpathByTitle(article_title), "Cannot find saved article");
        if(Platform.getInstance().isIOS())
        {
            this.clickElementToTheRightUpperCorner(getSavedArticleXpathByTitle(article_title),"Cannot find saved article");
        }
        this.waitForArticleToDisappearByTitle(article_title);
    }


    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(article_xpath,"Cannot find saved article with title: " + article_title,15);
    }

    public void isArticlePresent(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.assertElementPresent(article_xpath,"Cannot find saved article with title: " + article_title);
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(article_xpath,"Saved article still present with title: " + article_title,15);
    }

    public void waitAndclickArticleByTitle(String article_title) {
        this.waitForElementAndClick(getSavedArticleXpathByTitle(article_title), "Cannot find article by title: " + article_title, 5);
    }
}
