package mx.tec.lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestHtmlApplicationTests {
	private WebClient webClient;
	
	@BeforeEach
	public void init() throws Exception {
		webClient = new WebClient();
	}
	
	@AfterEach
	public void close() throws Exception {
		webClient.close();
	}

	@Test
	public void givenAClient_whenEnteringAutomationPractice_thenPageIsCorrect()
			throws Exception {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php");
		
		assertEquals("My Store", page.getTitleText());
	}
	
	@Test
	public void givenAClient_whenEnteringLoginCredentials_thenAccountPageIsDisplayed()
	throws Exception {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back-my-account");
		HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
		emailField.setValueAttribute("carlosdanielventuracarreon@gmail.com");
		HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
		passwordField.setValueAttribute("heartsofiron2");
		HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
		HtmlPage resultPage = submitButton.click();
		
		assertEquals("My account - My Store", resultPage.getTitleText());
	}	
	@Test
	public void givenAClient_whenEnteringWrongLoginCredentials_thenAuthenticationPageIsDisplayed()
	throws Exception {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back-my-account");
		HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
		emailField.setValueAttribute("carlosdanielventuracarreon@gmail.com");
		HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
		passwordField.setValueAttribute("heartsofiron");
		HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
		HtmlPage resultPage = submitButton.click();
		
		assertEquals("Login - My Store", resultPage.getTitleText());
	}
	@Test
	public void givenAClient_whenEnteringWrongLoginCredentials_thenErrorMessageIsDisplayed()
	throws Exception {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back-my-account");
		HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
		emailField.setValueAttribute("carlosdanielventuracarreon@gmail.com");
		HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
		passwordField.setValueAttribute("heartsofiron");
		HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
		HtmlPage resultPage = submitButton.click();
		
		
		assertEquals("HtmlListItem[<li>]", resultPage.getFirstByXPath("//div[@class='alert alert-danger']/ol/li").toString());
	}
	@Test
	public void givenAClient_whenSearchingNotExistingProduct_thenNoResultsDisplayed()
	throws Exception {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=search&orderby=position&orderway=desc&search_query=&submit_search=");
		
		assertNotEquals("nill?", page.getFirstByXPath("//*[@id=\"center_column\"]/ul/li[1]/div/div[1]/div/a[1]/img"));
	}
	@Test
	public void givenAClient_whenSearchingEmptyString_thenPleaseEnterDisplayed()
	throws Exception {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=search&orderby=position&orderway=desc&search_query=&submit_search=");
		
		
		assertEquals("HtmlParagraph[<p class=\"alert alert-warning\">]", page.getFirstByXPath("//*[@id=\"center_column\"]/p").toString());
	}
	@Test
	public void givenAClient_whenSigningOut_thenAuthenticationPageIsDisplayed()
	throws Exception {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back-my-account");
		HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
		emailField.setValueAttribute("carlosdanielventuracarreon@gmail.com");
		HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
		passwordField.setValueAttribute("heartsofiron2");
		HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
		HtmlPage resultPage = submitButton.click();
		HtmlButton logoutButton = (HtmlButton)resultPage.getFirstByXPath("//*[@id=\"header\"]/div[2]/div/div/nav/div[2]/a");
		HtmlPage finalPage = logoutButton.click();
		assertEquals("Login - My Store", resultPage.getTitleText());
	}
}
