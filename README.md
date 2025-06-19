# IndoorsSDK-Android
## Contents

- [Requirements](#requirements)
- [Getting Started](#getting-started)  
- [Gradle](#gradle)
- [Using map](#using-map)  

## Requirements
- Support minimum android sdk 24 API

## Getting Started
### To get started with indoorsSDK, you can clone an example project from this repository.

### Gradle
This library is available [on Artifactory](https://software.indoorsnavi.pro/artifactory/android). To use it, add the following to your `build.gradle`:

#### Groovy
Step 1. Add the URL of the artifact repository
```groovy
    repositories {
        ...
        ivy { 
            url 'https://software.indoorsnavi.pro/artifactory/android' 
        }
    }
```
Step 2. Add the view binding features
```groovy
    android {
        ...
         buildFeatures {
              viewBinding = true
         }
    }
```

Step 3. Add the latest version dependency
```groovy
    //indoors SDK
    implementation 'pro.indoorsnavi:indoors-sdk-core:7.0.6'
    implementation 'pro.indoorsnavi:indoors-sdk-map:7.0.6'
```
### Using map
#### Kotlin
Step 1. Add the INcore initialization to the onCreate method of your Activity
```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        ...
        initializeINCore()
    }

    private fun initializeINCore() {
        val configuration = INCoreConfiguration.defaultConfiguration(applicationContext)
        INCore.initializeWithConfiguration(applicationContext, configuration)
    }
}
```

Step 2. Download the INApplication using authorization by your CLIENT_ID, CLIENT_SECRET
```kotlin
    ...
    companion object {
        private const val CLIENT_ID: String = "YOUR-CLIENT_ID"
        private const val CLIENT_SECRET: String = "YOUR-CLIENT_SECRET"
    }

    private var listBuildings : ArrayList<INBuilding>? = null
    private var currentBuilding : INBuilding? = null

    init {
        verifyAccessToken()
    }

    private fun verifyAccessToken() {
        INCore.getInstance().service.verifyAccessTokenWithCompletionBlock { isAuthorized ->
            if (isAuthorized as Boolean) {
                loadApplication()
            } else {
                authorizeApplication()
            }
        }
    }

    private fun authorizeApplication() {
        INCore.getInstance().service.authorizeApplicationWithClientId(CLIENT_ID, CLIENT_SECRET) { success: Any ->
            if (success as Boolean) {
                 loadApplication()
            }
        }
    }

    private fun loadApplication() {
        INCore.getInstance().service.loadApplicationsWithCompletionBlock { applications: Any? ->
            val listApplications = applications as ArrayList<INApplication>
            loadBuildings(listApplications[0])
        }
    }

    private fun loadBuildings(inApplication: INApplication) {
        INCore.getInstance().service.loadBuildingsOfApplication(currentApplication,{ resultBuildings: INResponseData ->
            listBuildings = resultBuildings.getData() as ArrayList<INBuilding>
            selectCurrentBuilding()
        }, { error -> })
    }

    private fun selectCurrentBuilding(listBuildings: ArrayList<INBuilding>) {
        val buildingId = USING_BUILDING_ID
        listBuildings?.forEach { building -> if(building.Id == buildingId) {
            currentBuilding = building
            return@forEach
        } }
    }
```

Step 3. To use the map, create a Fragment extended from INGlobalMapFragment
```kotlin
class MapFragment : INGlobalMapFragment() {

    ...
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         //implement methods for getting getCurrentBuilding() and getCurrentBuildings() 
        val currentBuilding = getCurrentBuilding()
        val listBuildings = getCurrentBuildings()

        setBuildings(listBuildings)
        setCurrentBuilding(currentBuilding)
    }
}
```
