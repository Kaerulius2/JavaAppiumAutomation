package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.By;

public class ArticleTests extends CoreTestCase
{
    @Test
    public void testCompareArticleTitle() throws InterruptedException {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        String article_title;
        if(Platform.getInstance().isAndroid()){
            article_title = ArticlePageObject.getAndroidArticleTitle();
        } else{
            article_title = ArticlePageObject.getiOSArticleTitle(1);
        }

        assertEquals("We see unexpected Article title","Java (programming language)", article_title);
    }

    @Test
    public void testSwipeArticle() throws InterruptedException {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement(1);
        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testArticleHasTitle() throws InterruptedException {

        String search_line = "Java";
        String substring = "Object-oriented programming language";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring(substring);
        //в нормальной ситуации тест падает, так как статья не успевает открыться,а появления мы не ждём - проверим с задержкой
        //Thread.sleep(5000);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.assertArticleHasTitle();

    }

}
