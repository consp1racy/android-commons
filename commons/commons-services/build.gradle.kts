import net.xpece.gradle.android.withJavadocJar
import net.xpece.gradle.android.withSourcesJar

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("net.xpece.android")
    id("net.xpece.publish.sonatype")
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("COMMONS_SERVICES_VERSION_NAME") as String

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(14)
    }

    withSourcesJar()
    withJavadocJar()
}

dependencies {
    compileOnly("androidx.annotation:annotation:1.1.0")

    compileOnly("androidx.core:core:1.1.0")
}
