buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("org.jetbrains.kotlinx:binary-compatibility-validator:0.7.0")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.4.30")
    }

    repositories {
        google()
        mavenCentral()
    }
}

apply(plugin = "binary-compatibility-validator")

extensions.configure<kotlinx.validation.ApiValidationExtension> {
    // Ignore all sample projects, since they're not part of our API.
    // Only leaf project name is valid configuration, and every project must be individually ignored.
    // See https://github.com/Kotlin/binary-compatibility-validator/issues/3
    ignoredProjects.addAll(listOf("sample-cardbutton", "sample-commons"))
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")

    tasks.withType<org.jetbrains.dokka.gradle.DokkaTask> {
        dokkaSourceSets {
            configureEach {
                reportUndocumented.set(false)
                skipDeprecated.set(true)
                includeNonPublic.set(false)
                skipEmptyPackages.set(true)
            }
        }
    }

    repositories {
        google()
        mavenCentral()
    }

    configurations.all {
        resolutionStrategy.dependencySubstitution {
            substitute(module("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2"))
                .using(module("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3"))
        }
    }
}

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.ALL
}
