plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.sqlDelight.plugin)
}

android {
    namespace = "com.pvsb.locktapcompose"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.pvsb.locktapcompose"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(Modules.datasource))
    implementation(project(Modules.domain))
    implementation(project(Modules.presentation))

    implementation(libs.androix.core)
    implementation(libs.androix.lifecycle)
    implementation(libs.androix.appCompat)
    implementation(libs.compose.activity)
    implementation(libs.compose.material)
    implementation(libs.compose.constraintLayout)
    implementation(libs.compose.preview)
    implementation(libs.compose.coil)
    implementation(libs.compose.navigation)
    implementation(libs.kotlin.serialization)
    implementation(libs.hilt.android)
    implementation(libs.hilt.compose)
    kapt(libs.hilt.compiler)
    implementation(libs.sqlDelight.driver)

    testImplementation(libs.test.jUnit)
    androidTestImplementation(libs.test.ext)
    androidTestImplementation(libs.test.espresso)
}
