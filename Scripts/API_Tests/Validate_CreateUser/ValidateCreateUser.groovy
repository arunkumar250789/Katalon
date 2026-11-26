import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

// Minimal validation script for Create User API
WS.comment('Starting Validate_CreateUser script')

try {
    // Create request object and set variables
    RequestObject request = findTestObject('API_Endpoints/User_Management/Create_User_API')
    
    // Generate test data with timestamp
    String ts = (new Date()).format('yyyyMMddHHmmss')
    String testName = 'CI Test User'
    String testUsername = 'ci_user_' + ts
    String testEmail = 'ci.user.' + ts + '@example.com'
    String testPhone = '555-010-' + ts[-4..-1]
    
    // Set variables on request object
    request.setVariables([
        'name': testName,
        'username': testUsername,
        'email': testEmail,
        'phone': testPhone
    ])
    
    // Send request and log response
    ResponseObject response = WS.sendRequest(request)
    WS.comment("Response status: " + response.getStatusCode())
    WS.comment("Response body: " + response.getResponseBodyContent())
    
    // Validate response
    WS.verifyResponseStatusCode(response, 201)
    WS.verifyElementPropertyValue(response, 'name', testName)
    WS.verifyElementPropertyValue(response, 'username', testUsername)
    WS.verifyElementPropertyValue(response, 'email', testEmail)
    WS.verifyElementPropertyValue(response, 'phone', testPhone)
} catch (Exception e) {
    WS.comment("Test failed with error: " + e.getMessage())
    throw e
}// Basic assertions
WS.verifyResponseStatusCode(response, 201)

def body = response.getResponseBodyContent()
WS.comment('Response body: ' + body)

def json = new groovy.json.JsonSlurper().parseText(body)
assert json.name == 'CI Test User'
assert json.email == ('ci.user.' + ts + '@example.com')

WS.comment('Validate_CreateUser completed successfully')
