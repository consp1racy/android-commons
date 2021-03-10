include(":sample-commons", ":sample-extra")
include(":cardbutton", ":sample-cardbutton")
include(":scriminsets")
include(":icontabs")
include(":appcompat-extra")
include(":recyclerview-extra")
include(":design-extra")
include(":picker")
include(":edgeeffect")
include(":appcompat-chronometer")

fun includeSubprojects(vararg projectNames: String) {
    projectNames.forEach { projectName ->
        file(projectName).listFiles()!!
            .filter { File(it, "build.gradle.kts").exists() || File(it, "build.gradle").exists() }
            .forEach { include(":$projectName:${it.name}") }
    }
}

includeSubprojects("commons")
includeSubprojects("dialog")
includeSubprojects("text")

includeBuild("gradle/plugins/publish")
includeBuild("gradle/plugins/android")
