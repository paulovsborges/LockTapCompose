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

    implementation(libs.androix.core)
    implementation(libs.androix.lifecycle)
    implementation(libs.androix.appCompat)
    implementation(libs.compose.activity)
    implementation(libs.compose.material)
    implementation(libs.compose.constraintLayout)
    implementation(libs.compose.coil)
    implementation(libs.compose.navigation)
    implementation(Kotlin.serialization)
    implementation(Hilt.android)
    implementation(Hilt.compose)
    implementation("androidx.exifinterface:exifinterface:1.3.3")
    implementation("androidx.exifinterface:exifinterface:1.3.3")
    kapt(Hilt.compiler)

    implementation(libs.camerax.core)
    implementation(libs.camerax.lifecycle)
    implementation(libs.camerax.view)
    implementation(libs.camerax.extensions)

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
