package com.revature.gluecode;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.page.LoginPage;
import com.revature.page.EmployeePage;
import com.revature.page.FinanceManagerPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginTest {
	
	private WebDriver driver;
	private LoginPage loginPage;
	private EmployeePage employeePage;
	private FinanceManagerPage financeManagerPage;
	
	@Given("I am at the login page")
	public void i_am_at_the_login_page() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users/husto/Documents/chromedriver.exe");
		
		this.driver = new ChromeDriver();
		
		this.driver.get("http://localhost:8080");
		this.loginPage = new LoginPage(driver);
	}
	
	@When("I type in a username of {string}")
	public void i_type_in_a_valid_username_of(String string) {
		this.loginPage.getUsernameInput().sendKeys(string);
	}
	
	@When("I type in a password of {string}")
	public void i_type_in_a_valid_password_of(String string) {
		this.loginPage.getPasswordInput().sendKeys(string);
	}
	
	@When("I click the login button")
	public void i_click_the_login_button() {
		this.loginPage.getLoginButton().click();
	}
	
	@Then("I should see a message of {string}")
	public void i_should_see_a_message_of(String string) throws InterruptedException {
		String actual = this.loginPage.getErrorMessage().getText();
		
		Assertions.assertEquals(string, actual);
		
		this.driver.quit();
	}
	
	@Then("I should be redirected to the employee homepage")
	public void i_should_be_redirected_to_the_employee_homepage() throws InterruptedException {
		this.employeePage = new EmployeePage(this.driver);
		
		String expectedWelcomeHeading = "Welcome to the Employee homepage!";
		
		Assertions.assertEquals(expectedWelcomeHeading, this.employeePage.getWelcomeHeading().getText());
		
		this.driver.quit();
	}
	
	@Then("I should be redirected to the finance manager homepage")
	public void i_should_be_redirected_to_the_finance_manager_homepage() throws InterruptedException {
		this.financeManagerPage = new FinanceManagerPage(this.driver);
		
		String expectedWelcomeHeading = "Welcome to the Finance Manager Homepage!";
		
		Assertions.assertEquals(expectedWelcomeHeading, this.financeManagerPage.getWelcomeHeading().getText());
		
		this.driver.quit();
	}

}
