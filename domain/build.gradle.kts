plugins {
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.library) // id("com.android.library")
    alias(libs.plugins.kotlin.android)  // id("org.jetbrains.kotlin.android")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "io.kikiriki.sgmovie.domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

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
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // common modules
    implementation(project(":core:coroutines"))
    testImplementation(project(":core:test"))

    // hilt - dependency injector
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    // testing
    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.mockk.android)
    // testing: Dispatchers
    testImplementation(libs.test.kotlinx.coroutines)
    // testing: InstantTaskExecutorRule
    testImplementation(libs.androidx.arch.core.test)
}