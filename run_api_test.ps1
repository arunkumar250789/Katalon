Param()
# Run only the API test suite using Katalon CLI. Set KATALON_EXEC environment variable if katalon is not on PATH.
$katalon = if ($env:KATALON_EXEC) { $env:KATALON_EXEC } else { 'katalon' }
$project = Join-Path $PSScriptRoot 'AutomationFramework.prj'
& $katalon -noSplash -runMode=console -projectPath $project -retry=0 -testSuitePath 'Test Suites/API_Test_Suite' -executionProfile 'default' -browserType 'Chrome'
exit $LASTEXITCODE
