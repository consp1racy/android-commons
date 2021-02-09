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

    implementation("androidx.core:core:1.1.0")

    compileOnly("androidx.recyclerview:recyclerview:1.0.0@aar")
    compileOnly("androidx.viewpager:viewpager:1.0.0@aar")
    compileOnly("androidx.viewpager2:viewpager2:1.0.0-beta05@aar")
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("EDGEEFFECT_VERSION_NAME") as String

apply(from = rootProject.file("android-release.gradle"))
