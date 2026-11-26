@echo off
REM Run only the API test suite using Katalon CLI. Set KATALON_EXEC env var if katalon is not on PATH.
if "%KATALON_EXEC%"=="" (
  set "KATALON_CMD=katalon"
) else (
  set "KATALON_CMD=%KATALON_EXEC%"
)
"%KATALON_CMD%" -noSplash -runMode=console -projectPath "%~dp0AutomationFramework.prj" -retry=0 -testSuitePath="Test Suites/API_Test_Suite" -executionProfile="default" -browserType="Chrome"
exit /b %ERRORLEVEL%
