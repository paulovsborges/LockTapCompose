plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.pvsb.presentation"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
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
    implementation(libs.compose.activity)
    implementation(libs.compose.material)
    implementation(libs.compose.constraintLayout)
    implementation(libs.compose.coil)
    implementation(libs.compose.navigation)
    implementation(libs.kotlin.serialization)
    implementation(libs.hilt.android)
    implementation(libs.hilt.compose)
    implementation("androidx.exifinterface:exifinterface:1.3.3")
    implementation("androidx.exifinterface:exifinterface:1.3.3")
    kapt(libs.hilt.compiler)

    implementation(libs.camerax.core)
    implementation(libs.camerax.lifecycle)
    implementation(libs.camerax.view)
    implementation(libs.camerax.extensions)

    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)

    testImplementation(libs.test.jUnit)
    androidTestImplementation(libs.test.ext)
    androidTestImplementation(libs.test.espresso)

    // workaround to fix bug with the compose preview
//    implementation(Compose.preview)
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0-alpha02")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0-alpha02")
}
