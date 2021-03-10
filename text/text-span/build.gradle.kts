plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(14)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.annotation:annotation:1.1.0")

    api(project(":text:text-span-bullet"))
    api(project(":text:text-span-textappearance"))
    api(project(":text:text-span-typeface"))
}


group = rootProject.property("TEXT_GROUP_ID") as String
version = rootProject.property("TEXT_SPAN_VERSION_NAME") as String

apply(from = rootProject.file("android-release.gradle"))