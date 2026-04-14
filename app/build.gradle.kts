plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
}

android {
  namespace = "dev.skaba.soma.app"
  compileSdk {
    version = release(36)
  }

  defaultConfig {
    applicationId = "dev.skaba.soma.app"
    minSdk = 24
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildFeatures {
    buildConfig = true
  }

  buildTypes {
    debug {
      buildConfigField(
        "String",
        "BACKEND_URL",
        "\"htts://debug-soma.skaba.dev\"",
      ) // pro debug hostuju custom backend
    }

    release {
      buildConfigField("String", "BACKEND_URL", "\"htts://soma.skaba.dev\"")

      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  buildFeatures {
    compose = true
  }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.tooling)
  debugImplementation(libs.androidx.compose.ui.test.manifest)

  //  https://developer.android.com/jetpack/androidx/releases/room
  implementation(libs.androidx.room.runtime)
  implementation(libs.androidx.room.ktx)
  ksp(libs.androidx.room.compiler)

  // coil knihovna pro obrazky
  implementation(libs.coil.compose)

  // navigace
  implementation(libs.androidx.navigation.compose)
  implementation(libs.kotlinx.serialization.json)

  // datastore
  implementation(libs.androidx.datastore.preferences)

  // internet
  implementation(libs.retrofit)
  implementation(libs.retrofit.kotlinx)
  implementation(libs.okhttp)
}