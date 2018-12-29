import queryless.plugin.extension.QuerylessExtension

plugins {
    java
    id("queryless.plugin") version("1.0.0-SNAPSHOT")
}

group = "queryless"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

configure<QuerylessExtension> {
    sources = fileTree("src/main/resources").matching { include("**/*.*") }
}