pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("br.dev.pedrolamarao.metal.")) {
                useModule("br.dev.pedrolamarao.gradle.metal:plugins:0.3-next+")
            }
        }
    }
}

include("application")
include("base")
include("intermediate")
include("internal")