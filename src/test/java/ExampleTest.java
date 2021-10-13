import models.Talent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.homePage.HomePage;
import pages.searchResults.SearchResultsPage;

import java.util.List;

public class ExampleTest extends BaseTest {
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    public ExampleTest() {
        super();
        homePage = new HomePage(driver);
        searchResultsPage = new SearchResultsPage(driver);
    }

    /**
     * Scenario: Validate the talents filters is working and showing correct results in page 1
     */
    @Test
    public void verifyGoogleSearchDisplayed() {
        String searchKey = "Automation";

        // Visit the home page and make sure its loaded
        homePage.visit().isLoaded();

        // filter talents using some key
        homePage.clickOnSearchInput()
                .clickTalentOption()
                .typeInSearchInput(searchKey)
                .clickSubmitSearchButton();

        // Wait resutls page until its loaded and parse results
        searchResultsPage.isLoaded();
        List<Talent> talents = searchResultsPage.parseTalents();

        // Validate and print if all talents have the search key and were its found
        validateTalentsContainsSearchKey(searchKey,talents);
    }

    /**
     *  Assert that all the existing talents contains the search key in one of its fields
     * @param key the key used to filter the talents
     * @param talents
     */
    public void validateTalentsContainsSearchKey(String key, List<Talent> talents) {
        for (Talent talent : talents) {
            Assertions.assertTrue(talent.isMatchFilterKey(key), "Talent expected to have search key in one of the fields");
        }
    }

}

