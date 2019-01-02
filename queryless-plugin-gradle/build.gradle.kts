plugins {
    `java-gradle-plugin`
    `maven-publish`
    idea
}

group = "queryless"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    compile("queryless", "queryless-core", "1.0.0-SNAPSHOT")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

gradlePlugin {
    plugins {
        create("QuerylessPlugin") {
            id = "queryless.plugin"
            implementationClass = "queryless.plugin.GradlePlugin"
        }
    }
}