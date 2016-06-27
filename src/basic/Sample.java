package basic;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
public class Sample 
{

	public static void main(String[] args) 
	{
		

		
				WebDriver wb = new FirefoxDriver();
				wb.get("http://10.0.1.86/tatoc/basic/grid/gate");
				String s, s1;

				wb.findElement(By.cssSelector("div.greenbox")).click();
				wb.switchTo().frame(0);
				s = wb.findElement(By.cssSelector("div#answer")).getAttribute("class");
				wb.switchTo().frame("child");
				s1 = wb.findElement(By.cssSelector("div#answer")).getAttribute("class");
			
				while (true) {
					wb.switchTo().defaultContent();
					wb.switchTo().frame(0);
					if (!(s.equals(s1))) {
						System.out.println(s + " and " + s1 + "\n");
						wb.findElement(By.linkText("Repaint Box 2")).click();
						wb.switchTo().frame("child");
						s1 = wb.findElement(By.cssSelector("div#answer")).getAttribute("class");
						System.out.println("not equals\n");
					} else {
						
						wb.findElement(By.linkText("Proceed")).click();
						break;
					}
				}
				WebElement dragElement=wb.findElement(By.id("dragbox"));
				  WebElement dropElement=wb.findElement(By.id("dropbox"));
				    
				  Actions builder = new Actions(wb);  // Configure the Action
				  Action dragAndDrop = builder.clickAndHold(dragElement)
				    .moveToElement(dropElement)
				    .release(dropElement)
				    .build();  // Get the action
				    dragAndDrop.perform();
				    wb.findElement(By.linkText("Proceed")).click();

				    wb.findElement(By.linkText("Launch Popup Window")).click();
				    
				    String winHandleBefore = wb.getWindowHandle();

				    
				    for(String winHandle : wb.getWindowHandles())
				    {
				        wb.switchTo().window(winHandle);
				    }
				     wb.findElement(By.cssSelector("input#name")).sendKeys("heyaa");
				    wb.findElement(By.cssSelector("input#submit")).click();
				    wb.switchTo().window(winHandleBefore);
				    wb.findElement(By.linkText("Proceed")).click();
				   
				    wb.findElement(By.linkText("Generate Token")).click();
				    String s2=wb.findElement(By.cssSelector("#token")).getText();
				    String s3=s2.substring(7);
				    System.out.println(s3);
				    Cookie name = new Cookie("Token", s3);
					wb.manage().addCookie(name);
					wb.findElement(By.linkText("Proceed")).click();
			           
				    
			}
			

	}
