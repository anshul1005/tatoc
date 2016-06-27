package basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Chrome {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		String downloadDir = System.getProperty("user.home") + "//Downloads";
		System.setProperty("webdriver.chrome.driver", downloadDir+"//chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get("http://10.0.1.86/tatoc/advanced/file/handle");
		driver.findElement(By.cssSelector(".page a")).click();

	}

}
