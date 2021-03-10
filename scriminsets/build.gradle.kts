import net.xpece.gradle.android.withJavadocJar
import net.xpece.gradle.android.withSourcesJar

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("net.xpece.android")
    id("net.xpece.publish.sonatype")
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("SCRIMINSETS_VERSION_NAME") as String

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(14)
    }

    withSourcesJar()
    withJavadocJar()
}

dependencies {
    implementation("androidx.core:core:1.1.0")

    implementation(project(":commons:commons-resources"))
}
