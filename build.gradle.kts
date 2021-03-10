buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
        classpath("org.jetbrains.kotlinx:binary-compatibility-validator:0.4.0")
    }

    repositories {
        google()
        mavenCentral()
        jcenter {
            content {
                includeVersion("org.jetbrains.trove4j", "trove4j", "20160824")
            }
        }
    }
}

apply(plugin = "binary-compatibility-validator")

extensions.configure<kotlinx.validation.ApiValidationExtension> {
    // Ignore all sample projects, since they're not part of our API.
    // Only leaf project name is valid configuration, and every project must be individually ignored.
    // See https://github.com/Kotlin/binary-compatibility-validator/issues/3
    ignoredProjects.addAll(listOf("sample-cardbutton", "sample-commons", "sample-extra"))
}

subprojects {
    repositories {
        google()
        mavenCentral()
        jcenter {
            content {
                includeVersion("org.jetbrains.trove4j", "trove4j", "20160824")
            }
        }
    }
}

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.ALL
}
