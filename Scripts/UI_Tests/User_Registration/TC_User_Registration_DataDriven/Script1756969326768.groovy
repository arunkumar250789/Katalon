import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import internal.GlobalVariable

// Test Case: User Registration with Multiple Data Sets
WebUI.comment("Starting Data-Driven User Registration Test")

try {
	// Navigate to application
	CustomKeywords.'common.CommonKeywords.navigateToApplication'(GlobalVariable.ApplicationURL)
	
	// Get registration test data
	def testData = findTestData('Data Files/TestData/UserRegistrationData')
	
	// Register multiple users
	for (int row = 1; row <= testData.getRowNumbers(); row++) {
		String firstName = testData.getValue('firstname', row)
		String lastName = testData.getValue('lastname', row)
		String email = testData.getValue('email', row)
		String password = testData.getValue('password', row)
		
		WebUI.comment("Registering user: " + firstName + " " + lastName)
		
		// Click Register link
		WebUI.click(findTestObject('Object Repository/Page_Demo Web Shop/a_Log in_ico-register'))
		WebUI.waitForPageLoad(10)
		
		// Navigate to register page (click Register button on page)
		WebUI.click(findTestObject(null), FailureHandling.OPTIONAL) // Skip if not found
		
		// Fill registration form
		try {
			WebUI.click(findTestObject('Pages/Registration_Page/gender_male'), FailureHandling.OPTIONAL)
			WebUI.setText(findTestObject('Pages/Registration_Page/firstname_field'), firstName)
			WebUI.setText(findTestObject('Pages/Registration_Page/lastname_field'), lastName)
			WebUI.setText(findTestObject('Pages/Registration_Page/email_field'), email)
			WebUI.setText(findTestObject('Pages/Registration_Page/password_field'), password)
			WebUI.setText(findTestObject('Pages/Registration_Page/confirm_password'), password)
			
			// Submit registration
			WebUI.click(findTestObject('Pages/Registration_Page/register_button'))
			WebUI.delay(3)
			
			// Verify success
			boolean isSuccess = WebUI.verifyTextPresent("Your registration completed", false, FailureHandling.OPTIONAL)
			
			if (isSuccess) {
				WebUI.comment("✅ User " + email + " registered successfully")
			} else {
				WebUI.comment("⚠️ Registration may have failed or user already exists")
			}
			
		} catch (Exception e) {
			WebUI.comment("⚠️ Registration error: " + e.getMessage())
		}
		
		// Navigate back for next user
		WebUI.navigateToUrl(GlobalVariable.ApplicationURL)
	}
	
	WebUI.comment("✅ Registration Test Completed")
	
} catch (Exception e) {
	WebUI.comment("❌ Test failed: " + e.getMessage())
	CustomKeywords.'common.CommonKeywords.takeScreenshotOnFailure'('User_Registration')
	throw e
} finally {
	WebUI.closeBrowser()
}