plugins {
    id("com.android.library")
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
	
    implementation("androidx.appcompat:appcompat-resources:1.1.0")
}

group = rootProject.property("GROUP_ID").toString()
version = rootProject.property("SCRIMINSETS_VERSION_NAME").toString()

apply(from = rootProject.file("android-release.gradle"))
