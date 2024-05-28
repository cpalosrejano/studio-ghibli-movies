plugins {
    id("kotlin-kapt")
    id("com.android.library")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.android")
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
    implementation(libs.test.junit)
    implementation(libs.test.mockk)
    implementation(libs.test.mockk.android)
    // testing: Dispatchers
    implementation(libs.test.kotlinx.coroutines)
    // testing: InstantTaskExecutorRule
    implementation(libs.androidx.arch.core.test)
}