plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(14)
    }
}

dependencies {
    implementation("androidx.annotation:annotation:1.1.0")

    api("com.google.android.material:material:1.0.0")

    api(project(":appcompat-extra"))
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("EXTRA_VERSION_NAME") as String

apply(from = rootProject.file("android-release.gradle"))
