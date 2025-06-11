# IndoorsSDK-Android
## Contents

- [Requirements](#requirements)
- [Getting Started](#getting_started)
- [Gradle](#gradle)
- [Code](#code)

## Requirements
- Support minimum android sdk 24 API

## Getting Started
This library is available [on Artifactory](https://software.indoorsnavi.pro/artifactory/android). To use it, add the following to your `build.gradle`:

### Gradle
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
    // ---indoors sdk---
    implementation("pro.indoorsnavi:indoorssdk:6.1.25")

    // ---specific sdk implementation---
    //android support
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.work:work-runtime:2.9.1")

    //maplibre
    implementation("org.maplibre.gl:android-sdk:10.2.0")
    implementation("org.maplibre.gl:android-sdk-services:5.9.0")
    implementation("org.maplibre.gl:android-plugin-building-v9:2.0.1")
    implementation("org.maplibre.gl:android-plugin-markerview-v9:2.0.1")
    implementation("org.maplibre.gl:android-plugin-annotation-v9:2.0.1")
    implementation("org.maplibre.gl:android-sdk-turf:5.9.0")

    //ar
    implementation("com.google.ar:core:1.45.0")

    //notification service
    implementation("com.google.android.gms:play-services-gcm:17.0.0")

    //images
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.makeramen:roundedimageview:2.3.0")

    //3d
    implementation("org.rajawali3d:rajawali:1.1.970")

    //database and objects
    implementation("org.greenrobot:greendao:3.3.0")
    implementation("org.greenrobot:eventbus:3.3.1")

    //encryption db
    implementation("net.zetetic:android-database-sqlcipher:4.4.0")

    //http request
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.12.7")
    implementation("com.google.code.gson:gson:2.10.1")

    //barcode
    implementation("com.google.zxing:core:3.3.3")

    //compress decompress files
    implementation("org.apache.commons:commons-compress:1.21")

    //java types
    implementation("net.jodah:typetools:0.3.1")

    //easy device info
    implementation("com.github.nisrulz:easydeviceinfo:2.4.1")
    implementation("com.github.nisrulz:easydeviceinfo-base:2.4.1")
    implementation("com.github.nisrulz:easydeviceinfo-ads:2.4.1")

    //cron
    implementation("com.cronutils:cron-utils:9.1.3")

    //math
    implementation("org.apache.commons:commons-math3:3.6.1")
```
### Code
### To get started with indoorsSDK, clone the example project of this repository

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
                onAuthorizeSuccess()
            } else {
                onAuthorizeFailed()
            }
        }
    }

    private fun onAuthorizeSuccess() {
        loadApplication()
    }

    private fun onAuthorizeFailed() {

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

        val currentBuilding = getCurrentBuilding()
        val listBuildings = getCurrentBuildings()

        setBuildings(listBuildings)
        setCurrentBuilding(currentBuilding)
    }
}
```
