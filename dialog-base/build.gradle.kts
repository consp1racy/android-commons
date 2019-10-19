import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
	id("com.android.library")
	id("kotlin-android")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.2")

    defaultConfig {
        minSdkVersion(14)
	}
}

dependencies {
    compileOnly("androidx.appcompat:appcompat:1.1.0@aar")

    implementation("androidx.annotation:annotation:1.1.0")
    implementation("androidx.core:core:1.1.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${KotlinCompilerVersion.VERSION}")
}

repositories {
	google()
    jcenter()
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("PICKER_VERSION_NAME") as String

apply(from = rootProject.file("android-release.gradle"))
