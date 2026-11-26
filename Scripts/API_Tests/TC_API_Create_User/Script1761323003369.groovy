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
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import internal.GlobalVariable

// API Test - User Creation
WS.comment("=== Starting API User Creation Test ===")

try {
	// Create request object in code
	RequestObject request = new RequestObject()
	request.setRestUrl("https://jsonplaceholder.typicode.com/users")
	request.setRestRequestMethod("POST")
	
	// Set headers
	List<TestObjectProperty> headers = []
	headers.add(new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json"))
	request.setHttpHeaderProperties(headers)
	
	// Create request body
	def userData = [
		name: "John Doe API",
		username: "johndoe_api",
		email: "john.api@test.com",
		phone: "123-456-7890"
	]
	
	JsonBuilder jsonBuilder = new JsonBuilder(userData)
	request.setRestRequestBody(jsonBuilder.toString())
	
	WS.comment("Sending API request...")
	
	// Send request
	ResponseObject response = WS.sendRequest(request)
	
	// Verify response
	WS.comment("Response Status: " + response.getStatusCode())
	WS.verifyResponseStatusCode(response, 201)
	
	// Parse response
	JsonSlurper jsonSlurper = new JsonSlurper()
	def responseData = jsonSlurper.parseText(response.getResponseBodyContent())
	
	// Verify data
	assert responseData.name == "John Doe API"
	assert responseData.email == "john.api@test.com"
	
	WS.comment("✅ User created with ID: " + responseData.id)
	
	// Store user ID
	GlobalVariable.CreatedUserId = responseData.id.toString()
	
	WS.comment("=== API Test Completed Successfully ===")
	
} catch (Exception e) {
	WS.comment("❌ API Test failed: " + e.getMessage())
	throw e
}
