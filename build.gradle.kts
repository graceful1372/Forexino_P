
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.navigationSafeargs)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.googleServices)

}

android {
    namespace = ".."
    compileSdk = 34

    defaultConfig {
        applicationId = "ir.hmb72.forexuser"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    //Lifecycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    //Coroutines
    implementation(libs.coroutine)
    //Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    //Gson
    implementation(libs.gson)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    //OkHttp
    implementation(libs.okhttp.client)
    implementation(libs.okhttp.logging.interceptor)
    //Glide
    implementation(libs.glide)
    //DataStore
    implementation(libs.data.store)
    //Viewpager
    implementation(libs.viewpager2)
    //Receive OTP
    implementation(libs.play.services.base)
    implementation(libs.play.services.api.phone)
    //Other
    implementation(libs.lottie)
    implementation(libs.calligraphy)
    implementation(libs.viewpump)
    implementation(libs.mpChart)
    implementation(libs.dynamic.size)
    implementation(libs.persian.date)
    implementation(libs.circle.image)
    implementation(libs.circle.indicator){
        because("Display count points at the bottom of each list")
    }
    implementation(libs.shimmer.recyclerview){
        because("Shimmer effect for recyclerview")
    }
    implementation(libs.todkars.recyclerview){
        because("Shimmer effect for recyclerview")
    }
    implementation(libs.pinview)


}