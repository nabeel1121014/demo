import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
                String chromeDriverPath;
                if (System.getProperty("os.name").contains("Windows")) {
                    chromeDriverPath = "lib/chromedriver.exe";
                } else {
                    chromeDriverPath = "lib/chromedriver";
                }
                System.setProperty("webdriver.chrome.driver", chromeDriverPath);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 ");
                driver = new ChromeDriver(options);
                driver.manage().deleteAllCookies();
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