package selen;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.*;
import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.*;

import java.util.*;
import java.io.*;
import java.net.*;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities; 

class functionalityTests {
	

	@BeforeAll
	public static void setUp() throws Exception {
		String projDir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projDir + "\\drivers\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", projDir + "\\drivers\\geckodriver.exe");
	}
	
	//tests expected website url
	@Test
	public void testUrl() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
		WebDriver driver = new ChromeDriver(options);
        driver.get("https://myenvironmentnow.appspot.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String expUrl = "https://myenvironmentnow.appspot.com/";
        String currUrl = driver.getCurrentUrl().toString();
        System.out.println(currUrl);
        System.out.println(expUrl);
        assertTrue(expUrl.equals(currUrl));
	}

	//tests home button
	@Test
	public void testHomeButton() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
		WebDriver driver = new ChromeDriver(options);
        driver.get("https://myenvironmentnow.appspot.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String expUrl = "https://myenvironmentnow.appspot.com/";
        WebElement homeButt = driver.findElement(By.className("nav-link"));
        Actions action = new Actions(driver);
        action.click(homeButt).build().perform();
        System.out.println(driver.getCurrentUrl());
        List<WebElement> navLinks = driver.findElements(By.className("nav-link"));
        String currUrl = driver.getCurrentUrl().toString();
        assertTrue(expUrl.equals(currUrl));
	}
	
	//tests about button
	@Test
	public void testAboutButton() throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
		WebDriver driver = new ChromeDriver(options);
        driver.get("https://myenvironmentnow.appspot.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String expUrl = "https://myenvironmentnow.appspot.com/about";
        List<WebElement> navLinks = driver.findElements(By.className("nav-link"));
        WebElement aboutButt = navLinks.get(1);
        
        //homeButt.getAttribute(arg0)
        Actions action = new Actions(driver);
        Thread.sleep(2000);
        action.click(aboutButt).build().perform();
        String currUrl = driver.getCurrentUrl().toString();
        assertTrue(expUrl.equals(currUrl));
	}
	
	//tests contact button
	@Test
	public void testContactButton() throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
		WebDriver driver = new ChromeDriver(options);
        driver.get("https://myenvironmentnow.appspot.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String expUrl = "https://myenvironmentnow.appspot.com/contact";
        List<WebElement> navLinks = driver.findElements(By.className("nav-link"));
        WebElement aboutButt = navLinks.get(2);
        
        //homeButt.getAttribute(arg0)
        Actions action = new Actions(driver);
        Thread.sleep(2000);
        action.click(aboutButt).build().perform();
        String currUrl = driver.getCurrentUrl().toString();
        assertTrue(expUrl.equals(currUrl));
	}
	
	//tests get statistics button
	@Test
	public void testFirstButton() throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
		WebDriver driver = new ChromeDriver(options);
        driver.get("https://myenvironmentnow.appspot.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String expUrl = "https://myenvironmentnow.appspot.com/compare/";
        //WebElement firstButt = driver.findElement(By.className("ui animated button"));
        WebElement firstButt = driver.findElement(By.xpath("//*[@id=\"singleButton\"]"));
      
        
        //homeButt.getAttribute(arg0)
        Actions action = new Actions(driver);
        Thread.sleep(2000);
        action.click(firstButt).build().perform();
        Thread.sleep(500);
        String currUrl = driver.getCurrentUrl().toString();
        System.out.println(currUrl);
        System.out.println(expUrl);
        assertTrue(expUrl.equals(currUrl));
	}
	
	//tests compare second location button
	@Test
	public void testSecondButton() throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
		WebDriver driver = new ChromeDriver(options);
        driver.get("https://myenvironmentnow.appspot.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String expUrl = "https://myenvironmentnow.appspot.com";
        //WebElement firstButt = driver.findElement(By.className("ui animated button"));
        WebElement firstButt = driver.findElement(By.xpath("//*[@id=\"secondLocation\"]"));
      
        
        //homeButt.getAttribute(arg0)
        Actions action = new Actions(driver);
        Thread.sleep(2000);
        action.click(firstButt).build().perform();
        Thread.sleep(500);
        String currUrl = driver.getCurrentUrl().toString();
        //System.out.println(currUrl);
        //System.out.println(expUrl);
        WebElement newInput = driver.findElement(By.xpath("//*[@id=\"address2\"]"));
        assertTrue(newInput != null);
	}

	//tests compare locations button
	@Test
	public void testCompareLocationsButton() throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
		WebDriver driver = new ChromeDriver(options);
        driver.get("https://myenvironmentnow.appspot.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String expUrl = "https://myenvironmentnow.appspot.com/compare2/";
        //WebElement firstButt = driver.findElement(By.className("ui animated button"));
        WebElement firstButt = driver.findElement(By.xpath("//*[@id=\"secondLocation\"]"));
      
        
        //homeButt.getAttribute(arg0)
        Actions action = new Actions(driver);
        Thread.sleep(2000);
        action.click(firstButt).build().perform();
        Thread.sleep(500);
        String currUrl = driver.getCurrentUrl().toString();
        //System.out.println(currUrl);
        //System.out.println(expUrl);
        WebElement newButtonCompare = driver.findElement(By.xpath("//*[@id=\"compareButton\"]"));
        Actions action2 = new Actions(driver);
        Thread.sleep(2000);
        action2.click(newButtonCompare).build().perform();
        Thread.sleep(500);
        currUrl = driver.getCurrentUrl().toString();
        assertTrue(currUrl.equals("https://myenvironmentnow.appspot.com/compare2/"));
	}
	
	//tests about button then home button
	@Test
	public void testAboutHome() throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
		WebDriver driver = new ChromeDriver(options);
        driver.get("https://myenvironmentnow.appspot.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String expUrl = "https://myenvironmentnow.appspot.com/";
        List<WebElement> navLinks = driver.findElements(By.className("nav-link"));
        WebElement aboutButt = navLinks.get(1);
        
        //homeButt.getAttribute(arg0)
        Actions action = new Actions(driver);
        Thread.sleep(2000);
        action.click(aboutButt).build().perform();
        
        WebElement homeButt = driver.findElement(By.className("nav-link"));
        Actions action2 = new Actions(driver);
        action2.click(homeButt).build().perform();
        
        String currUrl = driver.getCurrentUrl().toString();
        System.out.println(currUrl);
        System.out.println(expUrl);
        assertTrue(expUrl.equals(currUrl));
	}
	
		//tests about contact then home button
	@Test
	public void testAboutContactHome() throws InterruptedException {
	ChromeOptions options = new ChromeOptions();
	options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
	WebDriver driver = new ChromeDriver(options);
        driver.get("https://myenvironmentnow.appspot.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String expUrl = "https://myenvironmentnow.appspot.com/";
        List<WebElement> navLinks = driver.findElements(By.className("nav-link"));
        WebElement aboutButt = navLinks.get(1);
        
        //homeButt.getAttribute(arg0)
        Actions action = new Actions(driver);
        Thread.sleep(2000);
        action.click(aboutButt).build().perform();
        navLinks = driver.findElements(By.className("nav-link"));
        WebElement contButt = navLinks.get(2);
        
        //homeButt.getAttribute(arg0)
        Actions action1 = new Actions(driver);
        Thread.sleep(2000);
        action1.click(contButt).build().perform();
        
        WebElement homeButt = driver.findElement(By.className("nav-link"));
        Actions action2 = new Actions(driver);
        action2.click(homeButt).build().perform();
        
        String currUrl = driver.getCurrentUrl().toString();
        //System.out.println(currUrl);
        //System.out.println(expUrl);
        assertTrue(expUrl.equals(currUrl));
	}
}
