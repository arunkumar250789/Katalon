import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

// Test Case: Valid Login
WebUI.comment("Starting Valid Login Test")

try {
	// Navigate to application
	CustomKeywords.'common.CommonKeywords.navigateToApplication'(GlobalVariable.ApplicationURL)
	
	// Wait for page to load completely
	WebUI.waitForPageLoad(10)
	
	// Click login link to navigate to login page
	WebUI.comment("Clicking login link")
	WebUI.click(findTestObject('Object Repository/Pages/Login_page/Page_Demo Web Shop/a_Register_ico-login'))
	WebUI.waitForPageLoad(10)
	
	// Perform login with valid credentials
	WebUI.comment("Attempting login with valid credentials")
	boolean loginResult = CustomKeywords.'common.CommonKeywords.performLogin'(
		GlobalVariable.ValidEmail,
		GlobalVariable.ValidPassword
	)
	
	// Verify login success
	WebUI.comment("Verifying login success")
	
	// Wait for page to load after login
	WebUI.delay(2)
	
	// Verify welcome message or logout link is present
	boolean isLoggedIn = false
	
	try {
		// Check if logout link exists
		if (WebUI.verifyElementPresent(findTestObject('Pages/Home_Page/logout_link'), 5, FailureHandling.OPTIONAL)) {
			WebUI.comment("Logout link found - login successful")
			isLoggedIn = true
		}
	} catch (Exception e) {
		WebUI.comment("Logout link not found, checking for account link")
	}
	
	// Alternative verification - check for account link or email display
	if (!isLoggedIn) {
		try {
			// Check if user email is displayed (common after login)
			isLoggedIn = WebUI.verifyTextPresent(GlobalVariable.ValidEmail, false, FailureHandling.OPTIONAL)
			if (isLoggedIn) {
				WebUI.comment("User email found - login successful")
			}
		} catch (Exception e) {
			WebUI.comment("Could not verify login through email display")
		}
	}
	
	// Final verification
	if (isLoggedIn) {
		WebUI.comment("Valid Login Test PASSED")
	} else {
		WebUI.comment("Valid Login Test FAILED - Could not verify successful login")
		throw new Exception("Login verification failed")
	}
	
	// Take screenshot of successful login
	WebUI.takeScreenshot()
	
} catch (Exception e) {
	WebUI.comment("Test failed with error: " + e.getMessage())
	WebUI.takeScreenshot()
	throw e
} finally {
	// Cleanup
	WebUI.comment("Cleaning up and closing browser")
	WebUI.closeBrowser()
}

WebUI.comment("Valid Login Test Completed")