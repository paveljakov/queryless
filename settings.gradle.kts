rootProject.name = "queryless-parent"

include("queryless-plugin-gradle", "queryless-tests-gradle")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
    }
}