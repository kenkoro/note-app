plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.kenkoro.note_app"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.kenkoro.note_app"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "com.kenkoro.note_app.feature_note.HiltTestRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
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
    jvmTarget = JavaVersion.VERSION_17.toString()
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.3"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  val daggerHilt = "2.48.1"
  implementation("com.google.dagger:dagger-android:$daggerHilt")
  implementation("com.google.dagger:hilt-android:$daggerHilt")
  ksp("com.google.dagger:hilt-android-compiler:$daggerHilt")
  kspAndroidTest("com.google.dagger:hilt-android-compiler:$daggerHilt")

  val room = "2.5.2"
  implementation("androidx.room:room-runtime:$room")
  implementation("androidx.room:room-ktx:$room")
  annotationProcessor("androidx.room:room-compiler:$room")
  ksp("androidx.room:room-compiler:$room")

  val coreKtx = "1.12.0"
  val lifecycleRuntimeKtx = "2.6.2"
  val activityCompose = "1.8.0"
  val navigationCompose = "2.7.4"
  val materialIconsExtended = "1.6.0-alpha07"
  val hiltNavigationCompose = "1.1.0-beta01"
  implementation("androidx.core:core-ktx:$coreKtx")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeKtx")
  implementation("androidx.activity:activity-compose:$activityCompose")
  implementation("androidx.navigation:navigation-compose:$navigationCompose")
  implementation("androidx.compose.material:material-icons-extended:$materialIconsExtended")
  implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationCompose")

  implementation(platform("androidx.compose:compose-bom:2023.03.00"))
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-graphics")
  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation("androidx.compose.material3:material3")

  val junit = "4.13.2"
  val extJunit = "1.1.5"
  val espressoCore = "3.5.1"
  val truth = "1.1.5"
  testImplementation("junit:junit:$junit")
  testImplementation("com.google.truth:truth:$truth")
  androidTestImplementation("androidx.test.ext:junit:$extJunit")
  androidTestImplementation("androidx.test.espresso:espresso-core:$espressoCore")
  androidTestImplementation("com.google.dagger:hilt-android-testing:$daggerHilt")
  androidTestImplementation("com.google.truth:truth:$truth")

  androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")
  debugImplementation("androidx.compose.ui:ui-tooling")
  debugImplementation("androidx.compose.ui:ui-test-manifest")
}