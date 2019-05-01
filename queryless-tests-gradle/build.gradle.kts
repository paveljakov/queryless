import java.util.Calendar
import queryless.plugin.extension.QuerylessExtension

plugins {
    java
    idea
    eclipse
    id("com.github.hierynomus.license") version("0.15.0")
    id("com.github.paveljakov.plugin") version("1.0.0-SNAPSHOT")
}

group = "com.github.paveljakov"
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

license {
    include("**/*.java")
    exclude("**/generated/**")
    mapping("java", "SLASHSTAR_STYLE")
    header = rootProject.file("HEADER")
    strictCheck = true
    ext["year"] = Calendar.getInstance().get(Calendar.YEAR)
}

configure<QuerylessExtension> {
    sources = fileTree("src/main/resources").matching {
        include("**/*.*")
    }
    isEnabled = true
}