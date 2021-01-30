package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {

    @Test
    public void testChangeScreenOrientationOnSearchResults()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        String title_before_rotation;
        if(Platform.getInstance().isAndroid()){
            title_before_rotation = ArticlePageObject.getAndroidArticleTitle();
        } else{
            title_before_rotation = ArticlePageObject.getiOSArticleTitle(1);
        }

        this.rotateScreenLandscape();

        String title_after_rotation;
        if(Platform.getInstance().isAndroid()){
            title_after_rotation = ArticlePageObject.getAndroidArticleTitle();
        } else{
            title_after_rotation = ArticlePageObject.getiOSArticleTitle(1);
        }

        assertEquals("Article title changed after screen rotation", title_after_rotation, title_before_rotation);

        this.rotateScreenPortrait();

        String title_after_second_rotation;
        if(Platform.getInstance().isAndroid()){
            title_after_second_rotation = ArticlePageObject.getAndroidArticleTitle();
        } else{
            title_after_second_rotation = ArticlePageObject.getiOSArticleTitle(1);
        }

        assertEquals("Article title changed after screen rotation", title_after_second_rotation, title_before_rotation);
    }

    @Test
    public void testCheckSearchArticleInBackground()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

}
