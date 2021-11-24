package BackendEngineer.Helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class DriverHelpers {
    private static final String USER_AGENT = "user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36";
    private static final String URL_GOOGLE = "https://google.com";

    private static final String WINDOW_OPEN = "window.open()";
    private static final String SCROLL_SMALL = "window.scrollBy(0,300)";
    private static final String SCROLL_MEDIUM = "window.scrollBy(0,600)";
    private static final String REMOVE_ELEMENT = "document.querySelector('%s).parentElement.remove()";

    private static final String EMPTY = "";

    private WebDriver driver;
    private WebDriverWait driverWait;
    private JavascriptExecutor javascriptExecutor;

    public DriverHelpers() {
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments(USER_AGENT);
        System.setProperty("webdriver.chrome.driver", "C:\\Chrome Driver\\chromedriver.exe"); // change this to your chromedriver.exe path
        driver = new ChromeDriver(options);
        driverWait = new WebDriverWait(driver, 10);
        javascriptExecutor = (JavascriptExecutor) driver;
    }

    public List<String> preparingTwoTabs() {
        driver.get(URL_GOOGLE);
        javascriptExecutor.executeScript(WINDOW_OPEN);
        return new ArrayList<>(driver.getWindowHandles());
    }

    public List<WebElement> getElementByScrolling(String url, String xpath, String tab) {
        switchTab(tab);
        driver.get(url);
        javascriptExecutor.executeScript(SCROLL_MEDIUM);
        return driver.findElements(By.xpath(xpath));
    }

    public void getWebPage(String path, String tab) {
        switchTab(tab);
        driver.get(path);
    }

    public void waitOnElement(String xpath) {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public void removeElement(String element) {
        javascriptExecutor.executeScript(String.format(REMOVE_ELEMENT, element));
    }

    public void setScrollSmall() {
        javascriptExecutor.executeScript(SCROLL_SMALL);
    }

    public String getText(String xpath) {
        return driver.findElements(By.xpath(xpath)).isEmpty() ? EMPTY : driver.findElement(By.xpath(xpath)).getText();
    }

    public String getImageSrc(String xpath, String attribute) {
        return driver.findElements(By.xpath(xpath)).isEmpty() ? EMPTY : driver.findElement(By.xpath(xpath)).getAttribute(attribute);
    }

    public void switchTab(String tab) {
        driver.switchTo().window(tab);
    }

    public void quit() {
        driver.quit();
    }


}
