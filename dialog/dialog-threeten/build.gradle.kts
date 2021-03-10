import net.xpece.gradle.android.withJavadocJar
import net.xpece.gradle.android.withSourcesJar

plugins {
    id("com.android.library")
    id("net.xpece.android")
    id("net.xpece.publish.sonatype")
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("DIALOG_THREETEN_VERSION_NAME") as String

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(26)
    }

    withSourcesJar()
    withJavadocJar()
}

dependencies {
    api("androidx.appcompat:appcompat:1.1.0")

    implementation(project(":picker"))
    implementation(project(":commons:commons-resources"))
    implementation(project(":dialog:dialog-datetime-base"))

    implementation("androidx.annotation:annotation:1.1.0")
    implementation("androidx.core:core:1.1.0")
}
