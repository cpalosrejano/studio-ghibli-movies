plugins {
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.library) // id("com.android.library")
    alias(libs.plugins.kotlin.android)  // id("org.jetbrains.kotlin.android")
}

android {
    namespace = "io.kikiriki.sgmovie.data"
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

    // modules
    implementation(project(":core:coroutines"))
    implementation(project(":domain"))
    testImplementation(project(":core:test"))

    // hilt - dependency injector
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    // networking (retrofit + moshi)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp.logging.interceptor)
    kapt(libs.moshi.kotlin.codegen)

    // room database
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    // testing
    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.mockk.android)
    // testing: Dispatchers
    testImplementation(libs.test.kotlinx.coroutines)
    // testing: InstantTaskExecutorRule
    testImplementation(libs.androidx.arch.core.test)

}

// Hilt: Allow references to generated code
kapt {
    correctErrorTypes = true
}