include(":sample-commons", ":commons", ":sample-extra")
include(":commons-services", ":commons-servicesx", ":commons-resources", ":commons-dimen", ":commons-dimen-lazy", ":commons-base")
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

includeSubprojects("dialog")
includeSubprojects("text")
