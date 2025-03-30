plugins {
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "io.kikiriki.sgmovie"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.kikiriki.sgmovie"
        minSdk = 23
        targetSdk = 35
        versionCode = 20250327
        versionName = "1.2.0"

        /* we will use the custom test runner which support hilt */
        //testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "io.kikiriki.sgmovie.CustomTestRunner"

    }
    testOptions {
        animationsDisabled = true
    }
    buildFeatures {
        viewBinding = true
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
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }
}

dependencies {

    implementation(project(":analytics"))
    implementation(project(":domain"))
    implementation(project(":data"))
    testImplementation(project(":core:test"))

    // firebase
    implementation(platform(libs.firebase.bom))
    // firebase: crashlytics
    implementation(libs.firebase.crashlytics)

    // android X: kotlin core
    implementation(libs.androidx.core.ktx)
    // android X: constraint layout
    implementation(libs.androidx.constraintlayout)
    // android X: coroutines with lifecycle aware
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.runtime)

    // google material
    implementation(libs.google.material)

    // image processing
    implementation(libs.coil)
    implementation(libs.coil.gif)

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

    // ui testing
    androidTestImplementation(libs.androidx.test.core.ktx)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
    androidTestImplementation(libs.androidx.test.espresso.intents)
    androidTestImplementation(libs.androidx.test.espresso.contrib)
    androidTestImplementation(libs.google.dagger.hilt.android.testing)
    kaptAndroidTest(libs.google.dagger.hilt.android.compiler)
}

// Hilt: Allow references to generated code
kapt { correctErrorTypes = true }