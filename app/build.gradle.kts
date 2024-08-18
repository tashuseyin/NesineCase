plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.navigation)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.example.nesinecase"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nesinecase"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://jsonplaceholder.typicode.com/\"")
            buildConfigField("String", "BASE_IMAGE_URL", "\"https://picsum.photos/300/300?grayscale\"")
            buildConfigField("String", "DATABASE_NAME", "\"posts.db\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://jsonplaceholder.typicode.com/\"")
            buildConfigField("String", "BASE_IMAGE_URL", "\"https://picsum.photos/300/300?grayscale\"")
            buildConfigField("String", "DATABASE_NAME", "\"posts.db\"")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.swiperefreshlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment)

    implementation(libs.coroutines)

    implementation(libs.glide)
}