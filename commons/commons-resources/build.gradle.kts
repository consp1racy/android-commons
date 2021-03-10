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
    api("androidx.appcompat:appcompat-resources:1.1.0")

    implementation(project(":commons:commons-base"))
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("COMMONS_RESOURCES_VERSION_NAME") as String

apply(from = rootProject.file("android-release.gradle"))
