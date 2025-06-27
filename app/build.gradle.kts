import java.io.InputStreamReader
import java.util.Properties

internal object Config {
    internal const val NAME_SPACE = "r.team.unpluggedproviderapp"
    internal const val APPLICATION_ID = "r.team.unpluggedproviderapp"
    internal const val VERSION_CODE = 1
    internal const val VERSION_NAME = "1.0.0"
    internal const val ANDROID_TEST_INSTRUMENTATION = "androidx.test.runner.AndroidJUnitRunner"
    internal const val PROGUARD_FILE = "proguard-android-optimize.txt"
    internal const val PROGUARD_RULES = "proguard-rules.pro"
}

internal fun getLocalProperty(key: String, file: String = "local.properties"): String {
    val properties = Properties()
    val localFile = rootProject.file(file)
    if (localFile.isFile) {
        InputStreamReader(localFile.inputStream(), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    } else error("Local properties file not found at path: $file")

    return properties.getProperty(key) ?: error("Key '$key' not found in local.properties")
}


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.android.ksp)
    alias(libs.plugins.hilt.android)

}

android {
    namespace = Config.NAME_SPACE

    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = Config.APPLICATION_ID
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = Config.VERSION_CODE
        versionName = Config.VERSION_NAME

        testInstrumentationRunner = Config.ANDROID_TEST_INSTRUMENTATION
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile(Config.PROGUARD_FILE),
                Config.PROGUARD_RULES
            )
            buildConfigField(
                "String",
                "REST_API_URL",
                getLocalProperty("BASE_URL_DEV")
            )
            buildConfigField(
                "String",
                "ALLOWED_CONSUMER_PACKAGE",
                "\"r.team.unpluggedconsumerapp\""
            )


        }

        debug {
            isMinifyEnabled = false
            isDebuggable = true
            buildConfigField(
                "String",
                "REST_API_URL",
                getLocalProperty("BASE_URL_DEV")
            )
            buildConfigField(
                "String",
                "ALLOWED_CONSUMER_PACKAGE",
                "\"r.team.unpluggedconsumerapp\""
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
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.runtime.android)
    implementation(libs.androidx.navigation.fragment)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.timber)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.room)
    ksp(libs.room.compiler)
}