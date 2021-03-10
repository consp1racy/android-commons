plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(14)
    }
}

dependencies {
    compileOnly("androidx.annotation:annotation:1.1.0")

    compileOnly("androidx.core:core:1.1.0")
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("COMMONS_SERVICES_VERSION_NAME") as String

apply(from = rootProject.file("android-release.gradle"))
