plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.clientapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.clientapp"
        minSdk = 29
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
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.inappmessaging.display)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.21")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.21")
    val nav_version = "2.8.2"

    // Jetpack Compose integration
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // Feature module support for Fragments
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-android-compiler:2.52")
    kapt ("com.github.bumptech.glide:compiler:4.16.0")
    implementation("io.insert-koin:koin-android:4.0.0")

    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-inappmessaging-display")
    implementation("com.google.firebase:firebase-auth")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")

    implementation ("androidx.fragment:fragment-ktx:1.8.4")

    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    implementation ("androidx.navigation:navigation-fragment-ktx:2.8.2")
    implementation ("androidx.navigation:navigation-ui-ktx:2.8.2")

    implementation ("com.google.android.exoplayer:exoplayer:2.19.1")

}

kapt {
    correctErrorTypes = true
}