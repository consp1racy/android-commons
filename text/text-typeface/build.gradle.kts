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
    implementation("androidx.core:core:1.3.2")
}


group = rootProject.property("TEXT_GROUP_ID") as String
version = rootProject.property("TEXT_TYPEFACE_VERSION_NAME") as String

apply(from = rootProject.file("android-release.gradle"))
