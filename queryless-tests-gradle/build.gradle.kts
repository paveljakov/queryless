plugins {
    java
    id("queryless.plugin") version("1.0.0-SNAPSHOT") apply(true)
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