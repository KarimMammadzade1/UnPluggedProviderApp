# UnPluggedProviderApp (Data Provider App)

## 📋 Overview

UnPluggedProviderApp is the **data provider app** responsible for:

- Fetching object data from a REST API
- Storing the data locally using Room Database
- Providing controlled, secure access to this data to authorized external apps via a **ContentProvider**

---

## ✅ Features

- ✅ Fetch data from REST API:  
Base URL: `https://api.restful-api.dev/`

- ✅ Local caching with Room

- ✅ Exposes search functionality to external apps via ContentProvider URI:
`content://r.team.unpluggedproviderapp.devicesprovider/search?query={search_query}`


- ✅ Security:  
Only apps signed with **allowed signatures** can query this ContentProvider.  
App signature validation is done inside the Provider for security.

- ✅ Object Details:
Detailed object data can be accessed from REST endpoint:  
`https://api.restful-api.dev/objects/{id}`

---

## ✅ Build Configuration

Add the following into your local `local.properties` file:
BASE_URL_PROD=https://api.restful-api.dev/
BASE_URL_DEV=https://api.restful-api.dev/


Both build variants point to the same endpoint for this assignment.

---

## ✅ Tech Stack:

- Kotlin
- Room Database
- Retrofit
- ContentProvider with signature-based access restriction
- MVVM architecture (for internal data handling)
- Coroutines

---

## ✅ Running the App:

1. Clone the repository.
2. Make sure the local.properties contains the correct BASE_URL values.
3. Build and run the app on a device or emulator.

**Note:**  
This app needs to be installed first before the Consumer app can access its data.

---

## ✅ Important:

- Provider App will **reject access** from any app that doesn't match the allowed signature.

---

## ⚙️ Project Structure & Notes

- Configuration constants such as `APPLICATION_ID`, `VERSION_CODE`, and related build configs are currently defined inside the app's `build.gradle.kts` file for simplicity.
- The helper function to read from `local.properties` is also defined there.
- **TODO:** For a larger or production project, these configs and utility functions should be moved to a centralized and separate Gradle Kotlin script file (e.g., `Config.gradle.kts`) to improve maintainability and reusability.
- Due to task scope and time constraints, this simplification was kept for this assignment.





