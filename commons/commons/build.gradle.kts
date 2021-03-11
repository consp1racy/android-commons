import net.xpece.gradle.android.withJavadocJar
import net.xpece.gradle.android.withSourcesJar

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("net.xpece.android")
    id("net.xpece.publish.sonatype")
}

group = rootProject.property("GROUP_ID") as String
version = rootProject.property("COMMONS_VERSION_NAME") as String

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(17)

        consumerProguardFiles("proguard-consumer-rules.pro")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
            res.srcDir("src/main/res-preview")
            res.srcDir("src/main/res-support")
        }
    }

    withSourcesJar()
    withJavadocJar()

    lintOptions {
        isAbortOnError = false
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
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test:rules:1.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")

    testImplementation("junit:junit:4.12")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("com.jakewharton.threetenabp:threetenabp:1.0.0")

    api(project(":design-extra"))

    api(project(":commons:commons-services"))
    api(project(":commons:commons-resources"))
    api(project(":commons:commons-dimen"))
    api(project(":commons:commons-base"))

    api(project(":picker"))

    api("androidx.core:core-ktx:1.1.0")
    api("com.google.android.material:material:1.0.0")

    compileOnly("androidx.fragment:fragment-ktx:1.1.0")

    compileOnly("androidx.annotation:annotation:1.1.0")

    compileOnly("androidx.palette:palette:1.0.0")

    compileOnly("com.google.android.gms:play-services-maps:17.0.0")

    compileOnly("com.jakewharton.threetenabp:threetenabp:1.0.0")

    compileOnly("com.jakewharton.timber:timber:4.5.0")

    compileOnly("com.squareup.moshi:moshi:1.0.0")
    compileOnly("com.squareup.okio:okio:1.0.0")
    compileOnly("com.squareup.okhttp3:okhttp:3.0.0")
    compileOnly("com.squareup.retrofit2:retrofit:2.0.0")

    compileOnly("io.reactivex.rxjava2:rxjava:2.0.0")

    compileOnly("androidx.lifecycle:lifecycle-extensions:2.0.0")
}

repositories {
    maven("https://jitpack.io")
}
