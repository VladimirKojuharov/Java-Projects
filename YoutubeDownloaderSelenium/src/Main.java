import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.firefox.driver", "C:\\Users\\Leni\\Downloads\\geckodriver-v0.32.2-win-aarch64-1\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();

        driver.get("https://youtube.com");
        driver.manage().window().maximize();

        WebElement searchInYoutube;

        try {
            searchInYoutube  = driver.findElement(By.xpath("/html/body/ytd-app/ytd-consent-bump-v2-lightbox/tp-yt-paper-dialog/div[3]/div[2]/div[1]/div[1]/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div/div[2]"));
            searchInYoutube.findElement(By.xpath("/html/body/ytd-app/ytd-consent-bump-v2-lightbox/tp-yt-paper-dialog/div[4]/div[2]/div[6]/div[1]/ytd-button-renderer[1]/yt-button-shape/button/yt-touch-feedback-shape/div/div[2]")).click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Thread.sleep(3000L);

        WebElement inputField = driver.findElement(By.xpath("//div[@id='search-input']/input"));
        inputField.sendKeys("java");

        Thread.sleep(2000L);

        driver.findElement(By.xpath("//body[1]/ytd-app[1]/div[1]/div[1]/ytd-masthead[1]/div[4]/div[2]/ytd-searchbox[1]/button[1]")).click();

        Thread.sleep(3000L);

        driver.findElement(By.xpath("//body[1]/ytd-app[1]/div[1]/ytd-page-manager[1]/ytd-search[1]/div[1]/ytd-two-column-search-results-renderer[1]/div[1]/ytd-section-list-renderer[1]/div[2]/ytd-item-section-renderer[1]/div[3]/ytd-video-renderer[1]/div[1]/ytd-thumbnail[1]/a[1]/yt-image[1]/img[1]")).click();

        String webSite = driver.getCurrentUrl();
        System.out.println(webSite);

        String dwSite =  "https://yout.com/video/?url=" + webSite;
        driver.get(dwSite);
        Thread.sleep(3000L);
        driver.findElement(By.xpath("//body/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[3]/div[3]/div[7]/button[1]")).click();

        Thread.sleep(200000L);
        driver.quit();

    }

}
