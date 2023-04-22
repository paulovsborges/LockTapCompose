plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.pvsb.presentation"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(Modules.domain))

    implementation(AndroidX.core)
    implementation(AndroidX.lifeCycle)
    implementation(AndroidX.appCompat)
    implementation(Compose.activity)
    implementation(Compose.material)
    implementation(Compose.constraintLayout)
    implementation(Compose.coil)
    implementation(Compose.navigation)
    implementation(Kotlin.serialization)
    implementation(Hilt.android)
    implementation(Hilt.compose)
    kapt(Hilt.compiler)

    implementation(CameraX.core)
    implementation(CameraX.lifecycle)
    implementation(CameraX.view)
    implementation(CameraX.extensions)

    testImplementation(MockK.core)
    testImplementation(Test.jUnit)
    testImplementation(Coroutines.test)
    androidTestImplementation(Test.jUInitExt)
    androidTestImplementation(Test.espresso)

    // workaround to fix bug with the compose preview
//    implementation(Compose.preview)
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0-alpha02")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0-alpha02")
}
