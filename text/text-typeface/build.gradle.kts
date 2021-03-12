import net.xpece.gradle.android.withJavadocJar
import net.xpece.gradle.android.withSourcesJar

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("net.xpece.android")
    id("net.xpece.publish.sonatype")
}

group = "net.xpece.android.text"
version = "1.1.0"

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(14)
    }

    withSourcesJar()
    withJavadocJar()
}

dependencies {
    implementation("androidx.annotation:annotation:1.1.0")
    implementation("androidx.core:core:1.3.2")
}
