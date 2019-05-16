package selenium.kit.driver;

import org.testng.annotations.*;
import selenium.kit.exception.BrowserException;

import static org.assertj.core.api.Assertions.assertThat;

@Test
public class BrowserTest {

    private Browser browser;

    @BeforeClass
    @Parameters({"browser"})
    public void setUpBrowser(@Optional("firefox") String browserName){
        if(browserName.equals("firefox")){
            browser = new Browser("firefox");
        }else {
            browser = new Browser("chrome");
        }
    }

    @Test(dataProvider = "test drivers")
    public void createBrowser(String address, String title){
        for(int i = 0; i < 5; i++){
            browser.open(address);
            assertThat(browser.getTitle()).isEqualTo(title);
            System.out.println(browser.getDriver() + " " + address + "" + title);
        }
    }

    @Test(expectedExceptions = {BrowserException.class })
    void browserNameCannotBeEmpty(){
        new Browser("");
    }

    @Test(expectedExceptions = {BrowserException.class })
    void openGooglePageWithoutHttp(){
        browser.open("google.com");
    }

    @Test
    void backNavigation(){
        browser.open("http://google.com");
        browser.open("https://www.seleniumhq.org/");
        browser.back();
        assertThat(browser.getTitle()).isEqualTo("Google");
    }

    @Test
    void getCurrentAddress(){
        browser.open("https://www.seleniumhq.org/");
        assertThat(browser.getCurrentUrl()).isEqualTo("https://www.seleniumhq.org/");
    }

    @Test
    void refreshGooglePage(){
        browser.open("http://google.com");
        browser.refresh();
        assertThat(browser.getTitle()).isEqualTo("Google");
    }

    @DataProvider(name="test drivers")
    public Object[][] getEducationsValidData() {
        return new Object[][]
                {
                        {
                            "http://google.com", "Google"
                        },
                        {
                            "https://www.seleniumhq.org/", "Selenium - Web Browser Automation"

                        }
                };
    }

    @AfterClass
    public void closeBrowser(){
        browser.close();
    }

}
