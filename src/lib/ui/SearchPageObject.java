package lib.ui;

        import io.appium.java_client.AppiumDriver;
        import org.openqa.selenium.By;

abstract public class SearchPageObject extends MainPageObject {

     protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_LENS_ICON,
            SEARCH_RESULT_TITLE;

    public SearchPageObject(AppiumDriver driver){
        super(driver); //драйвер из MainPageObject
    }

    //----------TEMPLATE METHODS------------
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}",substring);
    }
    //----------TEMPLATE METHODS------------

    public void initSearchInput()
    {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
        this.waitForElementPresent(SEARCH_INPUT, "Cannot find search input after clicking", 5);
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }
    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    public void clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    public void clickSearchIcon()
    {
        this.waitForElementAndClick(SEARCH_LENS_ICON, "Cannot find and click search icon", 5);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type search input",5);
    }
    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath,"Cannot find search results with substring: "+ substring,5);
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath,"Cannot find and click search results with substring: "+ substring,15);
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(SEARCH_RESULT_ELEMENT,"Cannot find anything by your request",15);
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }


    public void waitForEmptyResultsLabel() {

        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result label", 15);
    }

    public void assertThereIsNoResultsOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT,"We supposed not to find any results");
    }

    public String getSearchlineHint() {
        return this.waitForElementAndGetAttr(SEARCH_INPUT,"text", "Cannot find search input", 5);
    }

    public void assertAllResultsHasText(String search_word) {
        this.testTextSearchResults(By.id(SEARCH_RESULT_TITLE),search_word,"Not all results has text: " + search_word);
    }
}
