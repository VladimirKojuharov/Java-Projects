import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        System.setProperty("webdriver.firefox.driver", "C:\\Users\\Leni\\Downloads\\geckodriver-v0.32.2-win-aarch64-1\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get("https://coinmarketcap.com/coins/");
        driver.manage().window().maximize();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Ethereum");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String data;

        for (int i = 0; i < 5; i++) {
            sheet.createRow(i);
            data = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div[1]/div[4]/table/tbody/tr[2]/td[4]/div/a/span")).getText();
            sheet.getRow(i).createCell(0).setCellValue(LocalDateTime.now().toString());
            sheet.getRow(i).createCell(1).setCellValue(data);
            Thread.sleep(5000L);
        }

        FileOutputStream outputStream;
        outputStream = new FileOutputStream(new File("C:\\Users\\Leni\\IdeaProjects\\CryptoTracker\\CryptoData.xlsx"));
        workbook.write(outputStream);

    }
}
