pluginManagement {
    plugins {
        val metalVersion = "0.1+"
        id("br.dev.pedrolamarao.metal.application") version(metalVersion)
        id("br.dev.pedrolamarao.metal.c") version(metalVersion)
    }
    repositories {
        mavenLocal()
    }
}

rootProject.name = "sample"