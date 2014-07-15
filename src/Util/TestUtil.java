package Util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import DataTable.Xls_Reader;
import Tests.TestBase;

public class TestUtil extends TestBase{

	public static void doLogin(String username, String password) throws InterruptedException{
		
		if(isLoggedIn){
			logout();
		}
		
		getObject("SignInLink").click();
		getObject("UserSignIn").sendKeys(username);
		getObject("PassSignIn").sendKeys(password);
		getObject("SignInBut").click();
		
		Thread.sleep(3000L);
		try{
		String displayedUsername=driver.findElement(By.xpath(OR.getProperty("UserTopLog"))).getText();
		
		if(displayedUsername.equals(username)){
			isLoggedIn=true;
		}else{
			isLoggedIn=false;
		}
		}catch(Throwable t){
			isLoggedIn=false;
		}
		
	}
	
	public static void logout(){
		if(isLoggedIn){
			getObject("SignOutLink").click();
			isLoggedIn=false;
		}
			
		}
	
	public static boolean isSkip(String testCase){
		for(int rowNum=2; rowNum<=datatable.getRowCount("Test Cases");rowNum++){
			if(testCase.equals(datatable.getCellData("Test Cases", "TCID", rowNum))){
				if(datatable.getCellData("Test Cases", "Runmode", rowNum).equals("Y"))
					return false;
			else
				return true;
		}
		}
			return false;
		
	}
	
public static Object[][] getData(String testName){
		
		if(datatable == null){
			datatable = new Xls_Reader(System.getProperty("user.dir")+"//src//config//Suite1.xlsx");
		}
		
		int rows=datatable.getRowCount(testName)-1;
		if(rows <=0){
			Object[][] testData =new Object[1][0];
			return testData;
			
		}
	    rows = datatable.getRowCount(testName);  // 3
		int cols = datatable.getColumnCount(testName);
		System.out.println("Test Name -- "+testName);
		System.out.println("Total rows -- "+ rows);
		System.out.println("Total cols -- "+cols);
		Object data[][] = new Object[rows-1][cols];
		
		for(int rowNum = 2 ; rowNum <= rows ; rowNum++){
			
			for(int colNum=0 ; colNum< cols; colNum++){
				data[rowNum-2][colNum]=datatable.getCellData(testName, colNum, rowNum);
			}
		}
		
		return data;
		
	}
	
public static void takeScreenShot(String fileName) {
	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	try {
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\screenshots\\"+fileName+".jpg"));
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	}	   
    
}



