package selenium.kit.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import selenium.kit.exception.BrowserException;

import java.io.IOException;

public class Browser {

    @Setter
    @Getter
    private WebDriver driver;

    public Browser(String browser){
        setBrowser(browser);
    }

    private void setBrowser(String browserName){
        if(browserName.length() == 0) {
            throw new BrowserException("Browser name cannot be empty");
        }else {
            switch (browserName)
            {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    setDriver(new FirefoxDriver());
                    break;
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    setDriver(new ChromeDriver());
                    break;
                default:
                    break;
            }
        }

    }

    public void close(){
        driver.quit();
        try {
            Runtime.getRuntime().exec("cmd taskkill /F /IM geckodriver.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open(String url){
        if (!url.contains("http")){
            throw new BrowserException("Url should have http or https protocol");
        }else {
            driver.get(url);
        }

    }

    public void refresh(){
        driver.navigate().refresh();
    }

    public void back(){
        driver.navigate().back();
    }

    public String getTitle(){
        return driver.getTitle();
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

}
