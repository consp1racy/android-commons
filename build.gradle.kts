import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
    }

    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

subprojects {
    plugins.whenPluginAdded {
        when (this) {
            is LibraryPlugin -> {
                extensions.getByType<LibraryExtension>().apply {
                    buildFeatures {
                        buildConfig = false
                    }
                }
            }
        }
    }

    tasks.withType<Javadoc>().configureEach {
        val options = options as StandardJavadocDocletOptions
        options.addStringOption("Xdoclint:none", "-quiet")
        setExcludes(listOf("**/*.kt"))
    }

    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.ALL
}
