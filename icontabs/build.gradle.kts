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
    api("com.google.android.material:material:1.0.0")
}

group = rootProject.property("GROUP_ID").toString()
version = rootProject.property("ICONTABS_VERSION_NAME").toString()

apply(from = rootProject.file("android-release.gradle"))
