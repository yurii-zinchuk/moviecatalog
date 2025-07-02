import java.util.Properties

val localProperties = project.rootProject.file("local.properties")
    .takeIf { it.exists() }
    ?.inputStream()
    ?.use { Properties().apply { load(it) } }

val tmdbAccessToken = localProperties?.getProperty("tmdb.access_token") ?: ""

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.room.gradle)
    alias(libs.plugins.ktlint.gradle)
}

android {
    namespace = "com.zinchuk.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "TMDB_ACCESS_TOKEN", "\"$tmdbAccessToken\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}
room {
    schemaDirectory("$projectDir/schemas")
}
kapt {
    correctErrorTypes = true
}

dependencies {

    // Gson
    implementation(libs.gson)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.coroutines.core)

    // Hilt
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.android.work)

    // Room
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.room.paging)

    // Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Tests
    testImplementation(libs.junit)

    // Debug
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}