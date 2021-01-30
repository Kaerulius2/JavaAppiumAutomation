package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.By;

public class MyListsTests extends CoreTestCase {

    private static final String name_of_folder = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList()
    {


        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement(1);
        String article_title;
        if(Platform.getInstance().isAndroid()){
            article_title = ArticlePageObject.getAndroidArticleTitle();
        } else{
            article_title = ArticlePageObject.getiOSArticleTitle(1);
        }

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyList(name_of_folder);
        }else
        {
            ArticlePageObject.addArticleToMySaved();
        }

        if(Platform.getInstance().isIOS()){
            ArticlePageObject.closeLoginToSyncDialog();
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if(Platform.getInstance().isAndroid()){
            MyListsPageObject.openFolderByName(name_of_folder);
        }

        MyListsPageObject.swipeByArticleToDelete(article_title);

    }

    @Test
    public void testSaveTwoArticlesDeleteOne()
    {
        //String name_of_folder = "Learning programming";
        String first_search = "Java";
        String first_search_substring = "Object-oriented programming language";
        String second_search = "JavaScript";
        String second_search_substring = "Programming language";
        //поищем первую статью
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(first_search);
        SearchPageObject.clickByArticleWithSubstring(first_search_substring);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement(1);
        //сохраним тайтл первой статьи
        String first_article_title;
        if(Platform.getInstance().isAndroid()){
            first_article_title = ArticlePageObject.getAndroidArticleTitle();
        } else{
            first_article_title = ArticlePageObject.getiOSArticleTitle(1);
        }

        //добавим первую статью в папку (для Андроид) и в сохраненное (для iOS)

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyList(name_of_folder);
        }else
        {
            ArticlePageObject.addArticleToMySaved();
        }

        if(Platform.getInstance().isIOS()){
            ArticlePageObject.closeLoginToSyncDialog();
        }

        //кликнем по иконке поика для Андроид и Назад для iOS (поскольку аппиум в этом месте почему-то не видит элементов)
        if(Platform.getInstance().isAndroid()){
            SearchPageObject.clickSearchIcon();
        }else{
            ArticlePageObject.closeArticle();
            SearchPageObject.initSearchInput();
        }

        //поищем вторую статью
        SearchPageObject.typeSearchLine(second_search);
        SearchPageObject.clickByArticleWithSubstring(second_search_substring);
        ArticlePageObject.waitForTitleElement(2);
        //сохраним тайтл второй статьи
        String second_article_title;
        if(Platform.getInstance().isAndroid()){
            second_article_title = ArticlePageObject.getAndroidArticleTitle();
        } else{
            second_article_title = ArticlePageObject.getiOSArticleTitle(2);
        }
        //добавим вторую статью в папку
        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyList(name_of_folder);
        }else
        {
            ArticlePageObject.addArticleToMySaved();
        }

        //закроем статью
        ArticlePageObject.closeArticle();
        //окроем списки
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        //откроем наш список
        if(Platform.getInstance().isAndroid()){
            MyListsPageObject.openFolderByName(name_of_folder);
        }
        //удалим первую статью
        MyListsPageObject.swipeByArticleToDelete(first_article_title);
        //проверим, что первая статья удалена
        MyListsPageObject.waitForArticleToDisappearByTitle(first_article_title);
        //проверим, что вторая статья осталась
        MyListsPageObject.isArticlePresent(second_article_title);
        //войдем в статью
        MyListsPageObject.waitAndclickArticleByTitle(second_article_title);
        //проверим, что тайтл соответствует ранее сохраненному
        String article_title_after_delete;
        if(Platform.getInstance().isAndroid()){
            article_title_after_delete = ArticlePageObject.getAndroidArticleTitle();
        } else{
            article_title_after_delete = ArticlePageObject.getiOSArticleTitle(2);
        }

        assertEquals("Article title not equal saved example", second_article_title, article_title_after_delete);

    }

}
