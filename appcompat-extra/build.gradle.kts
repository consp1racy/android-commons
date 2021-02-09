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
    implementation("androidx.annotation:annotation:1.1.0")

    api("androidx.appcompat:appcompat:1.1.0")
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("EXTRA_VERSION_NAME") as String

apply(from = rootProject.file("android-release.gradle"))
