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
    kotlinOptions {
        jvmTarget = "1.8"
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

    testImplementation(Test.jUnit)
    androidTestImplementation(Test.jUInitExt)
    androidTestImplementation(Test.espresso)
}

sqldelight {
    databases {
        create("LockTapDataBase") {
            packageName.set("com.pvsb.locktapcompose")
        }
    }
}
