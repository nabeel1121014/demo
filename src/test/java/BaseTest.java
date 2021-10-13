import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.RandomUserAgent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class BaseTest {
    protected static final String BROWSER = System.getProperty("BROWSER", "chrome");

    public static RemoteWebDriver driver;

    @BeforeAll
    public static void setupWebDriver() {
        setupDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
    }

    private static void setupDriver() {
        switch (BROWSER) {
            case "firefox":
                String geckoDriverPath = "lib/geckodriver";
                System.setProperty("webdriver.gecko.driver", geckoDriverPath);
                driver = new FirefoxDriver();
                break;
            case "chrome":
                if(System.getProperty("wdm.chromeDriverVersion") != null){
                    WebDriverManager.chromedriver().version(System.getProperty("chromeDriverVersion")).setup();
                }else
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--enable-benchmarking");
                options.addArguments("--enable-net-benchmarking");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-extensions");
                options.addArguments("--allow-running-insecure-content");
                options.addArguments("--use-fake-ui-for-media-stream");
                options.setPageLoadStrategy(PageLoadStrategy.NONE);
                LoggingPreferences logPrefs = new LoggingPreferences();
                logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
                logPrefs.enable(LogType.BROWSER, Level.ALL);
                options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
                options.addArguments(RandomUserAgent.getRandomUserAgent());
                break;
            case "internetExplorer":
                DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                driver = new InternetExplorerDriver(capabilities);
            default:
                throw new RuntimeException("Browser type unsupported");
        }
    }


    @AfterAll
    public static void suiteTearDown() {
        driver.quit();
    }

}