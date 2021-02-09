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
    implementation("androidx.core:core:1.1.0")

    implementation(project(":commons-resources"))
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("SCRIMINSETS_VERSION_NAME") as String

apply(from = rootProject.file("android-release.gradle"))
