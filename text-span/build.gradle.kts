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

    implementation(project(":commons-resources"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${KotlinCompilerVersion.VERSION}")
}


group = rootProject.property("TEXT_GROUP_ID").toString()
version = rootProject.property("TEXT_SPAN_VERSION_NAME").toString()

apply(from = rootProject.file("android-release.gradle"))
