package BringItOn.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import BringItOn.page.PastebinMainPage;
import BringItOn.page.PastebinSubmittedPastePage;

public abstract class AbstractTest {
    protected WebDriver driver;

    protected static final String NEW_PASTE_TEXT = "git config --global user.name  \"New Sheriff in Town\"\n" +
            "git reset $(git commit-tree HEAD^{tree} -m \"Legacy code\")\n" +
            "git push origin master --force";
    protected static final String NEW_PASTE_TITLE = "how to gain dominance among developers";
    protected static final String SUBMITTED_PASTE_TITLE_LOCATOR = "//div[@class='info-top']/h1";
    protected static final String EXPECTED_PASTE_SYNTAX = "Bash";
    PastebinMainPage pastebinMainPage;
    PastebinSubmittedPastePage pastebinSubmittedPastePage;

    @BeforeTest(alwaysRun = true)
    public void browserSetup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        pastebinMainPage = new PastebinMainPage(driver)
                .openPage()
                .typeInNewPasteText(NEW_PASTE_TEXT)
                .openSyntaxDropdown()
                .selectSyntaxBash()
                .openExpirationDropdown()
                .selectExpiration10M()
                .typeInNewPasteTitle(NEW_PASTE_TITLE)
                .submitNewPaste();
        pastebinSubmittedPastePage = new PastebinSubmittedPastePage(driver);
    }

    public WebElement waitForElement(By by) {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    @AfterTest(alwaysRun = true)
    public void browserExit() {
        driver.quit();
        driver = null;
    }
}
