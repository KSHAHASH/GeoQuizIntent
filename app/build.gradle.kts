plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

}

android {
    namespace = "com.bignerdranch.android.geoquiz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bignerdranch.android.geoquiz"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Use JUnit 4 for unit tests
    testImplementation(libs.junit) // JUnit 4
    // Remove JUnit 5 dependencies if not using them
    // testImplementation(libs.junit.jupiter)

    // Android testing libraries
    androidTestImplementation(libs.androidx.junit) // JUnit for Android tests
    androidTestImplementation(libs.androidx.espresso.core) // Espresso for UI tests

    implementation(kotlin("script-runtime"))
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.5")
    implementation("androidx.activity:activity-ktx:1.7.2")
}