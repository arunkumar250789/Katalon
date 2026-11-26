# Run API tests (Katalon)

This document explains how to run the API test suite (`Test Suites/API_Test_Suite`) locally or in CI.

Prerequisites
- Katalon Studio CLI or Katalon Runtime Engine installed on the runner, and accessible as `katalon` on PATH or via the `KATALON_EXEC` environment variable.
- The project file `AutomationFramework.prj` sits at the repository root.

Run locally (Windows PowerShell):

1. Open PowerShell in the repository root.
2. Run:

```powershell
#$env:KATALON_EXEC = 'C:\path\to\katalon\katalon.exe' # optional: set if not on PATH
& $env:KATALON_EXEC -noSplash -runMode=console -projectPath "$PWD\AutomationFramework.prj" -retry=0 -testSuitePath="Test Suites/API_Test_Suite" -executionProfile="default" -browserType="Chrome"
```

Or use the included batch wrapper on Windows:

```powershell
.\run_api_test.bat
```

Customize
- To use a different test suite, edit the `-testSuitePath` parameter in the scripts.
- If you prefer to run a single test case, replace `-testSuitePath` with `-testCasePath="Test Cases/API_Tests/TC_API_Create_User"`.

CI tips
- In CI, set `KATALON_EXEC` to the full path of the katalon executable if it's not on PATH.
- Use machine images with required browsers installed (Chrome) when running UI tests; for API-only runs, browser isn’t strictly required but Katalon still expects a browser type argument.

Notes
- I updated `Object Repository/API_Endpoints/Create_User_API.rs` and added `Object Repository/API_Endpoints/User_Management/Create_User_API.rs` to provide a working parameterized POST request for the create-user test.
- If your real API base URL differs from `https://jsonplaceholder.typicode.com/users`, update the `<restUrl>` in the `.rs` files.
