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
    implementation(libs.test.junit)
    implementation(libs.test.mockk)
    implementation(libs.test.mockk.android)
    // testing: Dispatchers
    implementation(libs.test.kotlinx.coroutines)
    // testing: InstantTaskExecutorRule
    implementation(libs.androidx.arch.core.test)
}