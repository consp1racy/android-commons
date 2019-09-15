plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdkVersion(29)
    //buildToolsVersion()

    defaultConfig {
        minSdkVersion(14)
    }
}

dependencies {
    val kotlin_version = rootProject.ext["kotlin_version"]
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")

    implementation("androidx.core:core:1.1.0")

    implementation(project(":commons-resources"))
}

group = rootProject.property("GROUP_ID").toString()
version = rootProject.property("SCRIMINSETS_VERSION_NAME").toString()

apply(from = rootProject.file("android-release.gradle"))
