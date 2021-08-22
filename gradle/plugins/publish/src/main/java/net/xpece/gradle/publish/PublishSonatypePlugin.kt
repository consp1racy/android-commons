package net.xpece.gradle.publish

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.*
import org.gradle.plugins.signing.SigningExtension
import java.io.BufferedInputStream
import java.util.*

class PublishSonatypePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.plugins.findPlugin(PublishPlugin::class)
            ?: target.apply<PublishPlugin>()

        target.plugins.findPlugin("signing")
            ?: target.apply(plugin = "signing")

        val publishing = target.extensions.getByName<PublishingExtension>("publishing")
        val signing = target.extensions.getByName<SigningExtension>("signing")
        signing.sign(publishing.publications)

        if (System.getenv("CI") == null) {
            signing.useGpgCmd()
        }

        val extension = target.extensions.getByType<PublishExtension>()
        extension.releaseFromDefaultComponent()

        val signingProps = try {
            Properties().apply {
                target.rootProject.file("publishing.properties")
                    .inputStream().buffered().use(this::load)
            }
        } catch (ex: Throwable) {
            target.logger.error(ex.toString())
            return
        }

        extension.pom {
            name.set(target.name)
            description.set(target.description ?: "Collection of custom utilities for Android development.")
            url.set("https://github.com/consp1racy/android-commons")

            licenses {
                license {
                    name.set("The Apache Software License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }

            developers {
                developer {
                    name.set("Eugen Pechanec")
                    email.set("e.pechanec@gmail.com")
                    url.set("https://github.com/consp1racy")
                }
            }

            scm {
                connection.set("scm:git:https://github.com/consp1racy/android-commons.git")
                developerConnection.set("scm:git:ssh://git@github.com/consp1racy/android-commons.git")
                url.set("https://github.com/consp1racy/android-commons")
            }
        }

        extension.repositories { version ->
            when {
                version.endsWith("-SNAPSHOT") -> {
                    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") {
                        name = "ossrh"

                        credentials {
                            username = System.getProperty("OSSRH_USERNAME")
                                ?: signingProps.getProperty("OSSRH_USERNAME")
                            password = System.getProperty("OSSRH_PASSWORD")
                                ?: signingProps.getProperty("OSSRH_PASSWORD")
                        }
                    }
                }
                else -> {
                    maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
                        name = "ossrh"

                        credentials {
                            username = System.getProperty("OSSRH_USERNAME")
                                ?: signingProps.getProperty("OSSRH_USERNAME")
                            password = System.getProperty("OSSRH_PASSWORD")
                                ?: signingProps.getProperty("OSSRH_PASSWORD")
                        }
                    }
                }
            }
        }
    }
}