package net.xpece.gradle.publish

import org.gradle.api.artifacts.dsl.RepositoryHandler

open class PublishExtension {

    internal var publishReleaseFromComponent: String? = null
    internal var repositories: RepositoryHandler.() -> Unit = {}

    fun releaseFromDefaultComponent() {
        releaseFromComponent(DEFAULT_COMPONENT_NAME)
    }

    fun releaseFromComponent(componentName: String) {
        check(publishReleaseFromComponent == null) { "Release already set up." }
        publishReleaseFromComponent = componentName
    }

    fun repositories(block: RepositoryHandler.() -> Unit) {
        repositories = block
    }

    internal companion object {

        const val DEFAULT_COMPONENT_NAME = "__DEFAULT__"
    }
}
