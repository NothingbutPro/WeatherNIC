@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt.android.plugin)
    alias(libs.plugins.google.services)
    alias(libs.plugins.mapsplatform.secrets.gradle.plugin)
//    alias(libs.plugins.kotlin.kapt)


}

android {
    namespace = "com.parag.weathernic"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.parag.weathernic"
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
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.material)
    implementation(libs.intuit.sdp)
    implementation(libs.intuit.ssp)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation(libs.logging.interceptor)
    implementation(libs.play.services.maps)
    implementation(libs.firebase.bom)
    implementation(libs.play.services.location)
    implementation(libs.work.runtime.ktx)

}