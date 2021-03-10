import net.xpece.gradle.android.withJavadocJar
import net.xpece.gradle.android.withSourcesJar

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("net.xpece.android")
    id("net.xpece.publish.sonatype")
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("EDGEEFFECT_VERSION_NAME") as String

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

    implementation("androidx.core:core:1.1.0")

    compileOnly("androidx.recyclerview:recyclerview:1.0.0@aar")
    compileOnly("androidx.viewpager:viewpager:1.0.0@aar")
    compileOnly("androidx.viewpager2:viewpager2:1.0.0-beta05@aar")
}
