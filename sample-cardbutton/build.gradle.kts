plugins {
    id("com.android.application")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "net.xpece.android.widget.cardbutton.sample"

        minSdkVersion(18)
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
    implementation(project(":cardbutton"))
}
