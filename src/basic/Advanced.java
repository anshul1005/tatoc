package basic;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Advanced
{
    public static void main(String[] args) throws SQLException, InterruptedException, IOException
	{
		
    	String downloadDir = System.getProperty("user.home") + "//Downloads";
    	System.setProperty("webdriver.chrome.driver", downloadDir+"//chromedriver");
        WebDriver driver = new ChromeDriver();
		driver.get("http://10.0.1.86/tatoc/advanced/hover/menu");
		WebElement menu=driver.findElement(By.xpath("html/body/div/div[2]/div[2]/span[1]"));
		Actions builder = new Actions(driver);  
		builder.moveToElement(menu).build().perform();
		WebDriverWait wait = new WebDriverWait(driver, 5); 
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div[2]/div[2]/span[5]")));  
		WebElement menuOption = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/span[5]"));
		menuOption.click();
		
		Thread.sleep(5000);
		String symbol=null, name=null, passkey=null,id=null;
		java.sql.PreparedStatement stmt=null;
		java.sql.Connection con=null;
		ResultSet rs=null;
		symbol= driver.findElement(By.cssSelector("#symboldisplay")).getText();

		try
		{
	    con=DriverManager.getConnection("jdbc:mysql://10.0.1.86:3306/tatoc","tatocuser","tatoc01");
	    stmt= con.prepareStatement("select id from identity where symbol=?;");
		stmt.setString(1, symbol);
		 rs= stmt.executeQuery();
			while( rs.next()){
				id=  rs.getString("id");
			}
			System.out.println(id);
			int identity= Integer.parseInt(id);
			 rs.close();
			stmt.close();	
			stmt= con.prepareStatement("select name,passkey from credentials where id=?;");
			stmt.setInt(1, identity);
			rs= stmt.executeQuery();
			if(((ResultSet) rs).next())
			{
				name= ((ResultSet) rs).getString("name");
				passkey= ((ResultSet) rs).getString("passkey");
			}
			rs.close();
			stmt.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
       {
			if(rs!=null){
				rs.close();
			}
			if(stmt!=null){
				stmt.close();
			}
			
			if(con!=null){
				con.close();
			}
		}
		
		driver.findElement(By.cssSelector("#name")).sendKeys(name);
		driver.findElement(By.cssSelector("#passkey")).sendKeys(passkey);
		System.out.println(name);
		System.out.println(passkey);
		driver.findElement(By.cssSelector("#submit")).click();
		/*JavascriptExecutor js = (JavascriptExecutor) driver;
		
		Thread.sleep(2000);
		//js.executeScript("document.getElementsByClassName('video')[0].getElementsByTagName('object')[0].playMovie();");
		js.executeScript("player.play()");
		Thread.sleep(25000);
		driver.findElement(By.linkText("Proceed")).click();*/
		
		/*while(true)
		{
			String state = (String)js.executeScript("return document.getElementsByClassName('video')[0].getElementsByTagName('object')[0].getState();");
			System.out.println(state);
			
			if(state.equals("paused"))
			{
				driver.findElement(By.linkText("Proceed")).click();
				break;
			}
		}*/
	    Thread.sleep(2000);
		driver.get("http://10.0.1.86/tatoc/advanced/rest");
		
		String s2=driver.findElement(By.cssSelector("span#session_id")).getText();
	    String s3=s2.substring(12);
	    System.out.println(s3);
	    
	    String s = "http://10.0.1.86/tatoc/advanced/rest/service/token/";
	    s = s.concat(s3);
	    driver.get(s);
	    
	    String s4=driver.findElement(By.cssSelector("html>body>pre")).getText();
	    //String s5=s4.substring(10);
	    String result = s4.substring(10,42);
	    System.out.println(result);
	    
	    
	    URL url = new URL("http://10.0.1.86/tatoc/advanced/rest/service/register");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
	
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		String string= "id="+s3+"& signature="+result+"&allow_access=1";
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(string);
		wr.flush();
		wr.close();

		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + string);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) 
		{
			response.append(inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString());
	
		conn.disconnect();
		driver.navigate().back();
	    driver.findElement(By.cssSelector(".page a")).click();
	   // driver.findElement(By.linkText("Download File")).click();
	    
	Thread.sleep(2000);
	    
	    
	    
		//WebDriver driver = new ChromeDriver();
		//driver.get("http://10.0.1.86/tatoc/advanced/file/handle");
		driver.findElement(By.cssSelector(".page a")).click();
		
		Thread.sleep(4000);
		BufferedReader br = null;
		List<String> strings=null;
		try {

			String s1;

			br = new BufferedReader(new FileReader(downloadDir+"//file_handle_test.dat"));

			strings= new ArrayList<String>();
			
			while ((s1 = br.readLine()) != null) 
			{
				strings.add(s1);
			}

		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (br != null)
					br.close();
			} 
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}

	    String signature= strings.get(2);
	    signature= signature.substring(11);
	    driver.findElement(By.cssSelector("#signature")).sendKeys(signature);
	    driver.findElement(By.cssSelector(".submit")).click();

	}
}



