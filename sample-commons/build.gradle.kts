plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "net.xpece.commons.android.sample"

        minSdkVersion(17)
        targetSdkVersion(25)

        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs["debug"]
        }
    }

    lintOptions {
        ignore("ExpiredTargetSdkVersion")
    }
}

dependencies {
    implementation(project(":commons"))
    implementation(project(":edgeeffect"))
    implementation(project(":text-span"))

    implementation("androidx.cardview:cardview:1.0.0")

    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.12")
    implementation("com.jakewharton.threetenabp:threetenabp:1.2.1")
    implementation("com.jakewharton.timber:timber:4.7.1")
}
