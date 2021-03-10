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

        val signingProps by lazy {
            Properties().apply {
                target.rootProject.file("publishing.properties")
                    .inputStream().buffered().use(this::load)
            }
        }

        extension.repositories {
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