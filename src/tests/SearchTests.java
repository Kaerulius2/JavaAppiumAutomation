package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch() throws InterruptedException {


        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        //SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();

    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        String search_string = "Linking Park Discography";
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_string);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue("We found too few results", amount_of_search_results > 0);
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        String search_string = "qwsdchcxxg";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_string);

        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultsOfSearch();
    }

    @Test
    public void testSearchFieldText()
    {
        String search_hint = "Search…";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();

        assertEquals("Search hint is not: "+ search_hint,search_hint, SearchPageObject.getSearchlineHint());
    }

    @Test
    public void testSearchSeveralArtAndCancel()
    {
        String search_line = "Java";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);

        //убедимся, что контейнеров для статей хотя бы больше 1
        assertTrue("Few articles found", SearchPageObject.getAmountOfFoundArticles()>1);
        //ткнём крест
        SearchPageObject.clickCancelSearch();
        //убедимся, что все контейнеры исчезли

        SearchPageObject.assertThereIsNoResultsOfSearch();
    }

    @Test
    public void testSearchResultsText()
    {
        String search_word="Java";
        //ткнём в поиск
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        //поищем слово
        SearchPageObject.typeSearchLine(search_word);
        //проверим, что в каждом результате есть слово
        SearchPageObject.assertAllResultsHasText(search_word);
    }


}
