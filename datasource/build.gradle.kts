plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    alias(libs.plugins.sqlDelight.plugin)

}

android {
    namespace = "com.pvsb.datasource"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(project(Modules.domain))

    implementation(libs.androix.core)
    implementation(libs.androix.lifecycle)
    implementation(libs.androix.appCompat)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.sqlDelight.driver)
    implementation(libs.sqlDelight.coroutines)
    implementation(libs.androix.dataStore)
    implementation(libs.kotlin.serialization)

    testImplementation(libs.test.jUnit)
    androidTestImplementation(libs.test.ext)
    androidTestImplementation(libs.test.espresso)
}

sqldelight {
    databases {
        create("LockTapDataBase") {
            packageName.set("com.pvsb.locktapcompose")
        }
    }
}
