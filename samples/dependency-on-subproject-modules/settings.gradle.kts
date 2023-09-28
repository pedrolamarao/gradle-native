pluginManagement {
    plugins {
        id("br.dev.pedrolamarao.metal.application") version("1.0-SNAPSHOT")
        id("br.dev.pedrolamarao.metal.archive") version("1.0-SNAPSHOT")
        id("br.dev.pedrolamarao.metal.cxx") version("1.0-SNAPSHOT")
        id("br.dev.pedrolamarao.metal.ixx") version("1.0-SNAPSHOT")
    }
    repositories {
        mavenLocal()
    }
}

rootProject.name = "sample"

include("application")
include("archive")