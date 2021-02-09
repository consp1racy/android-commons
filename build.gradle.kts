import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.6.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.5")
    }
}

allprojects {
    tasks.withType<Javadoc>().configureEach {
        val options = options as StandardJavadocDocletOptions
        options.addStringOption("Xdoclint:none", "-quiet")
        setExcludes(listOf("**/*.kt"))
    }

    repositories {
        google()
        jcenter()
    }
}

subprojects {
    plugins.whenPluginAdded {
        if (this is LibraryPlugin) {
            extensions.getByType<LibraryExtension>().libraryVariants.all {
                generateBuildConfigProvider.configure {
                    isEnabled = false
                }
            }
        }
    }
}
