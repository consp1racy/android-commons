include(":sample-commons", ":commons", ":sample-extra")
include(":commons-services", ":commons-servicesx", ":commons-resources", ":commons-dimen", ":commons-dimen-lazy", ":commons-base")
include(":cardbutton", ":sample-cardbutton")
include(":scriminsets")
include(":icontabs")
include(":appcompat-extra")
include(":recyclerview-extra")
include(":design-extra")
include(":picker")
include(":dialog-base")
include(":dialog-message")
include(":dialog-datetime-base")
include(":dialog-threeten")
include(":dialog-threetenbp")
include(":edgeeffect")
include(":appcompat-chronometer")

fun includeSubprojects(vararg projectPaths: String) {
    projectPaths.forEach { projectPath ->
        file(projectPath).listFiles()!!
            .filter { File(it, "build.gradle.kts").exists() || File(it, "build.gradle").exists() }
            .forEach { include(":$projectPath:${it.name}") }
    }
}

includeSubprojects("text")
