import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id(SqlDelight.plugin) version SqlDelight.version
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

    implementation(Hilt.android)
    kapt(Hilt.compiler)
    implementation(SqlDelight.driver)

    implementation(AndroidX.core)
    implementation(AndroidX.lifeCycle)
    implementation(AndroidX.appCompat)
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
