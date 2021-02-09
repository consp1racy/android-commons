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
    implementation("androidx.annotation:annotation:1.1.0")

    implementation(kotlin("stdlib"))
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("COMMONS_BASE_VERSION_NAME") as String

apply(from = rootProject.file("android-release.gradle"))
