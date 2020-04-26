plugins {
	id("com.android.library")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(26)
	}
}

dependencies {
    api("androidx.appcompat:appcompat:1.1.0")

    implementation(project(":picker"))
    implementation(project(":commons-resources"))
    implementation(project(":dialog-datetime-base"))

    implementation("androidx.annotation:annotation:1.1.0")
    implementation("androidx.core:core:1.1.0")
}

repositories {
	google()
    jcenter()
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("DIALOG_THREETEN_VERSION_NAME") as String

apply(from = rootProject.file("android-release.gradle"))
