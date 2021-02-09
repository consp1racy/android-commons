plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(14)
    }
}

dependencies {
    api("androidx.recyclerview:recyclerview:1.0.0")
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("RECYCLERVIEW_EXTRA_VERSION_NAME") as String

apply(from = rootProject.file("android-release.gradle"))
