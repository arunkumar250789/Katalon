package common

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class CommonKeywords {
	
	@Keyword
	def navigateToApplication(String url) {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		WebUI.navigateToUrl(url)
		WebUI.waitForPageLoad(30)
	}
	
	@Keyword
	def performLogin(String email, String password) {
		try {
			// Click login link first
			WebUI.click(findTestObject('Object Repository/Pages/Login_page/Page_Demo Web Shop/a_Register_ico-login'), FailureHandling.OPTIONAL)
			WebUI.waitForPageLoad(10)
			
			// Fill login form
			WebUI.waitForElementPresent(findTestObject('Pages/Login_page/email_field'), 10)
			WebUI.setText(findTestObject('Pages/Login_page/email_field'), email)
			WebUI.setText(findTestObject('Pages/Login_page/password_field'), password)
			WebUI.click(findTestObject('Pages/Login_page/forgot_password'))
			WebUI.delay(2)
			return true
		} catch (Exception e) {
			WebUI.comment("Login failed: " + e.getMessage())
			return false
		}
	}
	
	@Keyword
	def takeScreenshotOnFailure(String testCaseName) {
		String timestamp = new Date().format('yyyy-MM-dd_HH-mm-ss')
		String screenshotPath = "Screenshots/" + testCaseName + "_" + timestamp + ".png"
		WebUI.takeScreenshot(screenshotPath)
		return screenshotPath
	}
	
	@Keyword
	def verifyElementPresentWithTimeout(def testObject, int timeout) {
		try {
			WebUI.waitForElementPresent(testObject, timeout)
			return WebUI.verifyElementPresent(testObject, timeout, FailureHandling.OPTIONAL)
		} catch (Exception e) {
			return false
		}
	}
}