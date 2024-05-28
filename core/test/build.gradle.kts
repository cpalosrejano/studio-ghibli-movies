plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "io.kikiriki.sgmovie.core.test"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // testing
    implementation("junit:junit:4.13.2")
    implementation("io.mockk:mockk:1.13.8")
    implementation("io.mockk:mockk-android:1.13.8")
    // testing: Dispatchers
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    // testing: InstantTaskExecutorRule
    implementation("androidx.arch.core:core-testing:2.2.0")
}