package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.page.HomePage;
import com.qa.hubspot.page.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
@Epic("Epic - 101 : create login page features")
@Feature("US - 501 : create test for login page on hubspot")
public class LoginPageTest {
	WebDriver driver;
	BasePage basePage;
	Properties prop;
	LoginPage loginPage;
	Credentials userCred;
	
	
	@BeforeTest(alwaysRun=true)
	public void setUp() {
		basePage = new BasePage();
		prop = basePage.init_properties();
		String browserName = prop.getProperty("browser");
	    driver = basePage.init_driver(browserName);
		driver.get(prop.getProperty("url"));
		
		loginPage=new LoginPage(driver);
		userCred=new Credentials(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test(priority = 1, description = "verify login page title test.....!!!")
	@Description("verify LoginPage Title Test....")
	@Severity(SeverityLevel.NORMAL)
	public void verifyLoginPageTitleTest() throws InterruptedException {
		String title=loginPage.getPageTitle();
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);
		
	}
	
	
	@Test(priority = 2, groups = "sanity")
	@Description("verify Sign up link Test....")
	@Severity(SeverityLevel.CRITICAL)
	public void verifySignUpLinkTest() {
		Assert.assertTrue(loginPage.checkSignUpLink());
	}
	
	@Test(priority = 3)
	@Description("verify Login Test....")
	@Severity(SeverityLevel.BLOCKER)
	public void loginTest() throws InterruptedException {
	HomePage homePage=loginPage.doLogin(userCred);
	String homepagetitle=homePage.getHomePageTitle();
	Assert.assertEquals(homepagetitle, AppConstants.HOME_PAGE_TITLE);
	
		
	}
	
	@DataProvider
	public Object[][] getLoginInvalidData() {
		Object data [][] = {
				{"test11111@gmail.com", "test123"},  
				
				  {"test2@gmail.com", " "}, 
				  {"  ", "test12345"},
				  {"test", "test"}, 
				  {" ", " "}
				 
			  };
		return data;
		
	}
	
	@Test(priority = 4, dataProvider="getLoginInvalidData" , enabled=false)
	public void login_InvalidTestCases(String invalid_username, String invalid_password) {
		userCred.setAppUsername(invalid_username);
		userCred.setAppPassword(invalid_password);
		loginPage.doLogin(userCred);
		Assert.assertTrue(loginPage.checkLoginErrorMesg());
		
	}

	
	
@AfterTest(alwaysRun=true)
public void tearDown() {
	driver.quit();
}
}
