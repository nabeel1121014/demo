package pages.searchResults;

import models.Talent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.AbstractPageObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsPage extends AbstractPageObject {

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    // Talent locators

    public final static String ADVANCE_SEARCH_XPATH = "//button[contains(text(),'Advanced Search')]";
    public final static String TALENTS_CONTAINER_CSS_SELECTOR = "//div[contains(@class,'up-card-section')]";
    public final static String TALENT_OVERVIEW_XPATH = "./div[contains(@class,'profile-stats')]/following-sibling::div";
    public final static String TALENT_SKILLS_XPATH = "./div[contains(@class,'up-skill-badge')]";
    public final static String TALENT_TITLE_XPATH = "./p[contains(@class,'freelancer-title')]";
    public final static String TALENT_NAME_XPATH = "./div[contains(@class,'identity-name')]";
    private List<Talent> talents = new ArrayList<Talent>();

    @Override
    protected By getPageLoadedElement() {
        return By.xpath(ADVANCE_SEARCH_XPATH);
    }

    public List<Talent> parseTalents() {
        List<WebElement> talentElements = driver.findElements(By.xpath(TALENTS_CONTAINER_CSS_SELECTOR));
        for (WebElement talent : talentElements) {
            talents.add(parseTalent(talent));
        }
        return talents;
    }

    public Talent parseTalent(WebElement el) {
        return Talent.builder()
                .title(parseTalentTitle(el))
                .name(parseTalentName(el))
                .overview(parseTalentOverview(el))
                .skills(parseSkillsOverview(el))
                .build();
    }

    public String parseTalentName(WebElement el) {
        return el.findElement(By.cssSelector(TALENT_NAME_XPATH)).getAttribute("innerText");
    }

    public String parseTalentTitle(WebElement el) {
        return el.findElement(By.cssSelector(TALENT_TITLE_XPATH)).getAttribute("innerText");
    }

    public String parseTalentOverview(WebElement el) {
        return el.findElement(By.xpath(TALENT_OVERVIEW_XPATH)).getAttribute("innerText");
    }

    public List<String> parseSkillsOverview(WebElement el) {
        List<WebElement> skillElements = el.findElements(By.xpath(TALENT_SKILLS_XPATH));
        List<String> skills = new ArrayList<>();
        for (WebElement skill : skillElements) {
            skills.add(skill.getAttribute("innerText"));
        }
        return skills;
    }
}
