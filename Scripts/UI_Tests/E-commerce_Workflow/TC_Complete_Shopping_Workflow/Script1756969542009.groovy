import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import internal.GlobalVariable

// Test Case: Complete E-commerce Workflow
WebUI.comment("Starting Complete Shopping Workflow Test")

try {
	// Step 1: Navigate and Login
	CustomKeywords.'common.CommonKeywords.navigateToApplication'(GlobalVariable.ApplicationURL)
	
	// Click login link
	WebUI.click(findTestObject('Object Repository/Pages/Login_page/Page_Demo Web Shop/a_Register_ico-login'))
	WebUI.waitForPageLoad(10)
	
	// Login
	WebUI.setText(findTestObject('Pages/Login_page/email_field'), GlobalVariable.ValidEmail)
	WebUI.setText(findTestObject('Pages/Login_page/password_field'), GlobalVariable.ValidPassword)
	WebUI.click(findTestObject('Pages/Login_page/forgot_password'))
	WebUI.delay(3)
	
	// Step 2: Navigate to Computers category
	WebUI.comment("Navigating to products")
	
	// Create dynamic object for Computers link
	TestObject computersLink = new TestObject()
	computersLink.addProperty('xpath', ConditionType.EQUALS, "//a[contains(@href,'computers') or contains(text(),'Computer')]")
	
	WebUI.click(computersLink, FailureHandling.OPTIONAL)
	WebUI.waitForPageLoad(10)
	WebUI.delay(2)
	
	// Step 3: Click first product
	TestObject firstProduct = new TestObject()
	firstProduct.addProperty('css', ConditionType.EQUALS, ".product-item:first-child .product-title a")
	
	WebUI.click(firstProduct, FailureHandling.OPTIONAL)
	WebUI.waitForPageLoad(10)
	
	// Step 4: Add to Cart
	WebUI.comment("Adding product to cart")
	
	try {
		WebUI.setText(findTestObject('Pages/Product_Page/quantity_field'), '2', FailureHandling.OPTIONAL)
	} catch (Exception e) {
		WebUI.comment("Quantity field not editable")
	}
	
	WebUI.click(findTestObject('Pages/Product_Page/add_to_cart_btn'))
	WebUI.delay(3)
	
	// Step 5: View Cart
	WebUI.comment("Viewing shopping cart")
	WebUI.click(findTestObject('Pages/Shopping_Cart/cart_link'))
	WebUI.waitForPageLoad(10)
	
	// Step 6: Verify Cart
	boolean cartVisible = WebUI.verifyElementPresent(
		findTestObject('Pages/Shopping_Cart/checkout_button'),
		10,
		FailureHandling.OPTIONAL
	)
	
	if (cartVisible) {
		WebUI.comment("✅ Shopping workflow completed successfully")
	} else {
		WebUI.comment("⚠️ Cart verification failed")
	}
	
	WebUI.takeScreenshot()
	
} catch (Exception e) {
	WebUI.comment("❌ Shopping workflow failed: " + e.getMessage())
	CustomKeywords.'common.CommonKeywords.takeScreenshotOnFailure'('Shopping_Workflow')
	throw e
} finally {
	WebUI.closeBrowser()
}