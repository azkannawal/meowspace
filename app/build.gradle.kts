plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.meowspace"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.meowspace"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity:1.9.0")
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.ink:ink-geometry-android:1.0.0-alpha04")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.compose.material:material-icons-extended")
    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.34.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")
    implementation("androidx.webkit:webkit:1.7.0")
}