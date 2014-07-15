package Tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import Util.TestUtil;

import DataTable.Xls_Reader;

public class TestBase {

	public static Properties CONFIG=null;
	public static Properties OR=null;
	public static WebDriver dr =null;
	public static EventFiringWebDriver driver = null;
	public static boolean isLoggedIn=false;
	public static ProfilesIni allprofiles =null;
	public static FirefoxProfile profile=null;
	public static Xls_Reader datatable=null;

	public void initialize() throws IOException{
		
		if(driver == null){
			
		CONFIG = new Properties();
		FileInputStream fn = new FileInputStream(System.getProperty("user.dir")+"//src//config//config.properties");
		CONFIG.load(fn);
		
		OR = new Properties();
		fn = new FileInputStream(System.getProperty("user.dir")+"//src//config//OR.properties");
		OR.load(fn);
		
		if(CONFIG.getProperty("browser").equals("Firefox")){
		allprofiles = new ProfilesIni();
		profile = allprofiles.getProfile(CONFIG.getProperty("Selenium"));
		dr = new FirefoxDriver(profile);
		}else if (CONFIG.getProperty("browser").equals("IE")){
		dr = new InternetExplorerDriver();
		}else if(CONFIG.getProperty("browser").equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", (System.getProperty("user.dir")+"\\chrome\\chromedriver.exe"));
			dr=new ChromeDriver();
		}
		
		
		
		driver = new EventFiringWebDriver(dr);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}


public static WebElement getObject(String xpathKey){
	try{
	return driver.findElement(By.xpath(OR.getProperty(xpathKey)));
	}catch(Throwable t){
		TestUtil.takeScreenShot(xpathKey);
		Assert.assertTrue(t.getMessage(), false);
		
	return null;	
	}
	
	
}
}

