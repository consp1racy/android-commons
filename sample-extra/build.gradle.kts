plugins {
    id("com.android.application")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        applicationId = "net.xpece.android.extra.sample"

        minSdkVersion(14)
        targetSdkVersion(25)

        versionCode = 1
        versionName = "1.0"

        vectorDrawables.useSupportLibrary = true
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
    implementation(project(":design-extra"))
}
