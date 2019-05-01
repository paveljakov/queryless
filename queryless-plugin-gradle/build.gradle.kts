import java.util.Calendar

plugins {
    `java-gradle-plugin`
    `maven-publish`
    idea
    id("com.github.hierynomus.license") version("0.15.0")
}

group = "com.github.paveljakov"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    compile("com.github.paveljakov", "queryless-core", "1.0.0-SNAPSHOT")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

gradlePlugin {
    plugins {
        create("QuerylessPlugin") {
            id = "com.github.paveljakov.plugin"
            implementationClass = "queryless.plugin.GradlePlugin"
        }
    }
}

license {
    include("**/*.java")
    mapping("java", "SLASHSTAR_STYLE")
    header = rootProject.file("HEADER")
    strictCheck = true
    ext["year"] = Calendar.getInstance().get(Calendar.YEAR)
}