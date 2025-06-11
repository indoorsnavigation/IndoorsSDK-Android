plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "pro.indoorsnavi.indoorssdkandroid"
    compileSdk = 35

    defaultConfig {
        applicationId = "pro.indoorsnavi.indoorssdkandroid"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
}