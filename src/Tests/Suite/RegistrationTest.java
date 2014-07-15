package Tests.Suite;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import Tests.TestBase;
import Util.TestUtil;

@RunWith (Parameterized.class)

public class RegistrationTest extends TestBase{

public String name;
public String id;
public String password;
public String email;
public String city;
public String positivedata;

public RegistrationTest(String name, String id, String password, String email,
		String city, String positivedata){
	
	this.name=name;
	this.id=id;
	this.password=password;
	this.email=email;
	this.city=city;
	this.positivedata=positivedata;
}

	@Before
	public void BeforeTest() throws IOException{
		initialize();
		if(TestUtil.isSkip("RegistrationTest"))
			Assume.assumeTrue(false);
	}
		
	@Test
	public void registerTest() throws InterruptedException{
		driver.navigate().to(CONFIG.getProperty("testSiteName"));
		getObject("RegLink").click();
		getObject("RegName").sendKeys(name);
		getObject("RegID").sendKeys(id);
		getObject("RegPass").sendKeys(password);
		getObject("RegEmail").sendKeys(email);
		getObject("RegCity").sendKeys(city);
		
		getObject("Above18").click();
		getObject("RegButton").click();
		
		Thread.sleep(5000L);
		WebElement thkyou =null;
		try{
		thkyou = driver.findElement(By.xpath(OR.getProperty("SuccReg")));
		}catch(Throwable t){
			TestUtil.takeScreenShot("Registration_Error");
			if(positivedata.equals("Y")){
				Assert.assertTrue("Not able to register with valid data", false);
			}
		}
		if(positivedata.equals("N")){
			TestUtil.takeScreenShot("Registration_Error_1");
			Assert.assertTrue("Able to register with invalid data ", false);
		}
	
	}
	@Parameters
	public static Collection<Object[]> dataSupplier(){
		
		Object[][] data = TestUtil.getData("RegistrationTest");
		
	return Arrays.asList(data);
	
	
	}
	

}
