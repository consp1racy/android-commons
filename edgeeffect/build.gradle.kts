import org.jetbrains.kotlin.config.KotlinCompilerVersion

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
    implementation("androidx.annotation:annotation:1.1.0")

    implementation("androidx.core:core:1.1.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${KotlinCompilerVersion.VERSION}")

    compileOnly("androidx.recyclerview:recyclerview:1.0.0@aar")
    compileOnly("androidx.viewpager:viewpager:1.0.0@aar")
    compileOnly("androidx.viewpager2:viewpager2:1.0.0-beta05@aar")
}

group = rootProject.property("GROUP_ID").toString()
version = rootProject.property("EDGEEFFECT_VERSION_NAME").toString()

apply(from = rootProject.file("android-release.gradle"))
