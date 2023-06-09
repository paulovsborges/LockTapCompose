plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id(SqlDelight.plugin) version SqlDelight.version
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

    implementation(AndroidX.core)
    implementation(AndroidX.lifeCycle)
    implementation(AndroidX.appCompat)
    implementation(Compose.activity)
    implementation(Compose.material)
    implementation(Compose.constraintLayout)
    implementation(Compose.preview)
    implementation(Compose.coil)
    implementation(Compose.navigation)
    implementation(Kotlin.serialization)
    implementation(Hilt.android)
    implementation(Hilt.compose)
    kapt(Hilt.compiler)
    implementation(SqlDelight.driver)

    testImplementation(Test.jUnit)
    androidTestImplementation(Test.jUInitExt)
    androidTestImplementation(Test.espresso)
}
