plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "io.kikiriki.sgmovie.core.test"
    compileSdk = 35

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
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