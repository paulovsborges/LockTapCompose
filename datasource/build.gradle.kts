plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
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

    implementation(libs.androix.core)
    implementation(libs.androix.lifecycle)
    implementation(libs.androix.appCompat)

    implementation(Hilt.android)
    kapt(Hilt.compiler)
    implementation(SqlDelight.driver)
    implementation(SqlDelight.coroutines)
    implementation(DataStore.core)
    implementation(Kotlin.serialization)

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
