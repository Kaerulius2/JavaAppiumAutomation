package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList()
    {
        String name_of_folder = "Learning programming";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);

        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);

    }

    @Test
    public void testSaveTwoArticlesDeleteOne()
    {
        String name_of_folder = "Learning programming";
        String first_search = "Java";
        String first_search_substring = "Object-oriented programming language";
        String second_search = "JavaScript";
        String second_search_substring = "Programming language";
        //поищем первую статью
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(first_search);
        SearchPageObject.clickByArticleWithSubstring(first_search_substring);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        //сохраним тайтл первой статьи
        String first_article_title = ArticlePageObject.getArticleTitle();
        //добавим первую статью в папку
        ArticlePageObject.addArticleToMyList(name_of_folder);
        //кликнем по иконке поика
        SearchPageObject.clickSearchIcon();
        //поищем вторую статью
        SearchPageObject.typeSearchLine(second_search);
        SearchPageObject.clickByArticleWithSubstring(second_search_substring);
        ArticlePageObject.waitForTitleElement();
        //сохраним тайтл второй статьи
        String second_article_title = ArticlePageObject.getArticleTitle();
        //добавим вторую статью в папку
        ArticlePageObject.addArticleToExistingList(name_of_folder);
        //закроем статью
        ArticlePageObject.closeArticle();
        //окроем списки
        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        //откроем наш список
        MyListsPageObject.openFolderByName(name_of_folder);
        //удалим первую статью
        MyListsPageObject.swipeByArticleToDelete(first_article_title);
        //проверим, что первая статья удалена
        MyListsPageObject.waitForArticleToDisappearByTitle(first_article_title);
        //проверим, что вторая статья осталась
        MyListsPageObject.isArticlePresent(second_article_title);
        //войдем в статью
        MyListsPageObject.waitAndclickArticleByTitle(second_article_title);
        //проверим, что тайтл соответствует ранее сохраненному
        assertEquals("Article title not equal saved exam", second_article_title, ArticlePageObject.getArticleTitle());



    }

}
