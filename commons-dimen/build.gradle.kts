plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(14)
    }
}

dependencies {
    compileOnly("androidx.annotation:annotation:1.1.0")

    implementation(kotlin("stdlib"))

    implementation(project(":commons-base"))
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("COMMONS_DIMEN_VERSION_NAME") as String

apply(from = rootProject.file("android-release.gradle"))
