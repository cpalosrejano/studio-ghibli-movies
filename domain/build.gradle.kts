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
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")

    // testing
    implementation("junit:junit:4.13.2")
    implementation("io.mockk:mockk:1.13.8")
    implementation("io.mockk:mockk-android:1.13.8")
    // testing: Dispatchers
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    // testing: InstantTaskExecutorRule
    implementation("androidx.arch.core:core-testing:2.2.0")
}