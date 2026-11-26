import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.context.TestCaseContext
import internal.GlobalVariable

class CustomTestListener {
	
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		println "Starting test case: " + testCaseContext.getTestCaseId()
		GlobalVariable.TestStartTime = System.currentTimeMillis()
	}
	
	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		println "Finished test case: " + testCaseContext.getTestCaseId()
		
		if (GlobalVariable.TestStartTime != null && GlobalVariable.TestStartTime != 0) {
			long executionTime = System.currentTimeMillis() - GlobalVariable.TestStartTime
			println "Execution time: " + executionTime + "ms"
		}
	}
}