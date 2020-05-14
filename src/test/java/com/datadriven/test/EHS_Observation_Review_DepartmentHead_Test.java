package com.datadriven.test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.utility.EHS_Observation_Util;

public class EHS_Observation_Review_DepartmentHead_Test {

	
	
	WebDriver driver;
	
	@BeforeMethod
	public void BMTest () throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "C:/Users/GAURAV/workspace/Vguard_Automation/driver/chromedriver.exe");
        driver = new ChromeDriver();
		System.out.println("Launch browser successfully");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("http://safeaskehs.cloudapp.net:65002/Account/Login");
		Thread.sleep(10000);
		  
	}
	@DataProvider
	public Iterator<Object[]> GetTestData()
	{
		
		ArrayList<Object[]>testdata=EHS_Observation_Util.GetDataFromExcel();
		return testdata.iterator();
		
		
	}
	
	@Test(dataProvider="GetTestData",enabled=true)
	public void EHS_Observation_Review_DepartmentHead(String UserName,String Password,String SubUnit,String Department,String Contractor,String Area,String ExactLocation,String Severity,
			String ObservationType,String ObservationCategory,String NoOfPersons,String Descriptionofwhathappened,String ImmediateContainmentAction,String ReportedbyName,String Attachments1,
			String Attachments2,String Attachments3,String Attachments4,String DepartmentHeadUsername,String DepartmentHeadPassword,String Clickonthat,String AuthorizationAction,
			String Comment,String ActiontobeTaken,String Responsibility,String Priority1) throws InterruptedException, AWTException
	{
		//Enter User name 
		driver.findElement(By.id("txtUserName")).sendKeys(DepartmentHeadUsername);
		Thread.sleep(1000);
		//Enter Password
		driver.findElement(By.id("txtPassword")).sendKeys(DepartmentHeadPassword);
		Thread.sleep(1000);
		//Click on submit
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(10000);
		
		//For you Review
		driver.findElement(By.xpath("//div[contains(@class,'lead-statistics relative two bg-warning')]//i[@class='arrow icofont-arrow-right']")).click();
		Thread.sleep(10000);
		//Click on that 
		driver.findElement(By.partialLinkText(Clickonthat)).click();
		Thread.sleep(5000);
						
		//New window handle
		String parentHandle = driver.getWindowHandle(); // get the current window handle
		
		for (String winHandle : driver.getWindowHandles()) {
		    driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
		}	
		//code to do something on new window
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		//scroll down
		jse.executeScript("scroll(0, 250);");
		
		//Authorization Action
		Select Authorizationaction = new Select(driver.findElement(By.id("ddlAction")));
		Authorizationaction.selectByVisibleText(AuthorizationAction);
		Thread.sleep(2000);
		
		//Comment
		driver.findElement(By.id("txtComment")).sendKeys(Comment);
		Thread.sleep(1000);
		
		//IMPROVE & CONTROL (Recommendation(s))
		driver.findElement(By.xpath("//button[@title='Add Action to be Taken']")).click();
		Thread.sleep(1000);
		
		//Action to be Taken 
		driver.findElement(By.xpath("//textarea[@name='txtTaskDescription']")).sendKeys(ActiontobeTaken);
		Thread.sleep(1000);
		
		//Responsibility 
		driver.findElement(By.xpath("//input[@name='txtResponsible']")).sendKeys(Responsibility);
		Thread.sleep(2000);
		
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_DOWN);
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_TAB);
		Thread.sleep(2000);
		
		//Priority
		Select priority = new Select(driver.findElement(By.xpath("//select[@name='ddlTaskPriority']")));
		priority.selectByVisibleText(Priority1);
		Thread.sleep(1000);
		
		//Save CAPA
		driver.findElement(By.xpath("//button[@title='Save']")).click();
		Thread.sleep(2000);
		
		//Submit 
		driver.findElement(By.xpath("//button[@class='btn btn-success waves-effect w-md waves-light m-b-5']")).click();
		Thread.sleep(5000);
		
		driver.close(); // close newly opened window when done with it
		driver.switchTo().window(parentHandle); // switch back to the original window

	}
	
	@AfterMethod
	public void AMTest() throws InterruptedException
	{
		driver.quit();
		System.out.println("Close browser successfully");
	}
}
