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

    implementation(Coroutines.core)

    testImplementation(MockK.core)
    testImplementation(platform(Junit.jupiterPlatform))
    testImplementation(Junit.jupiter)
    testImplementation(Coroutines.test)
    testImplementation(AssertJ.core)
}
