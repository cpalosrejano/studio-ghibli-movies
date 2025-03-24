import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.library) // id("com.android.library")
    alias(libs.plugins.kotlin.android)  // id("org.jetbrains.kotlin.android")
}

android {
    namespace = "io.kikiriki.sgmovie.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    val tmdbApiKey = getTmdbApiKey()
    buildTypes.forEach {
        it.buildConfigField("String", "TMDB_API_KEY", "\"${tmdbApiKey}\"")
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

    // modules
    implementation(project(":core:coroutines"))
    implementation(project(":domain"))
    testImplementation(project(":core:test"))

    // hilt - dependency injector
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    // firebase
    implementation(platform(libs.firebase.bom))
    // firebase: firestore
    implementation(libs.firebase.firestore)

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

private fun getTmdbApiKey() : String {
    // load properties
    val prop = Properties()
    prop.load(FileInputStream(File(rootProject.rootDir, "local.properties")))

    // load tmdb api key
    return prop.getProperty("TMDB_API_KEY") ?: ""
}