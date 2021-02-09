plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(14)
    }
}

dependencies {
    api("androidx.appcompat:appcompat:1.1.0")
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("APPCOMPAT_CHRONOMETER_VERSION_NAME") as String

apply(from = rootProject.file("android-release.gradle"))
