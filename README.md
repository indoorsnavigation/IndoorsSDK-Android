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
Add the URL of the artifact repository
```groovy
repositories {
     ...
     ivy { 
         url 'https://software.indoorsnavi.pro/artifactory/android' 
     }
}
```
Add the latest version dependency
```groovy
dependencies {
    ...
    //indoors SDK
    implementation 'pro.indoorsnavi:indoors-sdk-core:7.8.4'
    implementation 'pro.indoorsnavi:indoors-sdk-map:7.8.4'
}
```
