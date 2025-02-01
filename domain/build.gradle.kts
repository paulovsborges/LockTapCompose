plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

dependencies {

    implementation(libs.coroutines.core)

    testImplementation(libs.mockk)
    testImplementation(platform(libs.jUnit.platform))
    testImplementation(libs.jUnit.jupiter)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.assertj)
}
