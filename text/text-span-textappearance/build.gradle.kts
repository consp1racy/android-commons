import net.xpece.gradle.android.withJavadocJar
import net.xpece.gradle.android.withSourcesJar

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("net.xpece.android")
    id("net.xpece.publish.sonatype")
}

group = "net.xpece.android.text"
version = "1.2.0"

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(14)
    }

    withSourcesJar()
    withJavadocJar()

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

    implementation(project(":commons:commons-resources"))
    implementation(project(":text:text-typeface"))
}
