import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import internal.GlobalVariable

// Test Case: Invalid Login with Data-Driven Approach
WebUI.comment("Starting Invalid Login Test with Test Data")

try {
	// Navigate to application
	CustomKeywords.'common.CommonKeywords.navigateToApplication'(GlobalVariable.ApplicationURL)
	
	// Click login link
	WebUI.click(findTestObject('Object Repository/Page_Demo Web Shop/a_Register_ico-login'))
	WebUI.waitForPageLoad(10)
	
	// Get test data
	def testData = findTestData('Data Files/TestData/LoginTestData')
	
	// Loop through test data
	for (int row = 1; row <= testData.getRowNumbers(); row++) {
		String email = testData.getValue('email', row)
		String password = testData.getValue('password', row)
		String expectedResult = testData.getValue('expected_result', row)
		
		WebUI.comment("Testing with email: " + email)
		
		// Fill login form
		WebUI.setText(findTestObject('Pages/Login_page/email_field'), email)
		WebUI.setText(findTestObject('Pages/Login_page/password_field'), password)
		WebUI.click(findTestObject('Pages/Login_page/forgot_password'))
		
		WebUI.delay(2)
		
		if (expectedResult == 'failure') {
			// Verify error message appears
			boolean errorPresent = WebUI.verifyElementPresent(
				findTestObject('Pages/Login_page/error_message'),
				5,
				FailureHandling.OPTIONAL
			)
			
			if (errorPresent) {
				WebUI.comment("✅ Expected failure scenario validated")
			} else {
				WebUI.comment("⚠️ Error message not found")
			}
		}
		
		// Reset for next iteration
		WebUI.navigateToUrl(GlobalVariable.ApplicationURL)
		WebUI.click(findTestObject('Object Repository/Pages/Login_page/Page_Demo Web Shop/a_Register_ico-login'))
		WebUI.waitForPageLoad(5)
	}
	
	WebUI.comment("✅ Invalid Login Test Completed")
	
} catch (Exception e) {
	WebUI.comment("❌ Test failed: " + e.getMessage())
	CustomKeywords.'common.CommonKeywords.takeScreenshotOnFailure'('Invalid_Login')
	throw e
} finally {
	WebUI.closeBrowser()
}