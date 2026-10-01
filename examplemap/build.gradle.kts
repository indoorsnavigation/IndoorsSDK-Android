plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "pro.indoorsnavi.examplemap"
    compileSdk = 36

    defaultConfig {
        applicationId = "pro.indoorsnavi.indoorssdkandroid"
        minSdk = 24
        targetSdk = 36
        versionCode = 12
        versionName = "1.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // ---indoors sdk---
    implementation("pro.indoorsnavi:indoors-sdk-core:8.0.9")
    implementation("pro.indoorsnavi:indoors-sdk-map:8.0.9")



}