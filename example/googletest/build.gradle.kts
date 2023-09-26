plugins {
    id("base")
    id("br.dev.pedrolamarao.metal.cpp")
    id("br.dev.pedrolamarao.metal.cxx")
    id("br.dev.pedrolamarao.metal.base2")
}

// register "main" archive with cxx sources

metal {
    cpp {
        create("main")
    }
    cxx {
        create("main") {
            compileOptions = listOf("-g","--std=c++17")
            header( cpp.named("main").map { it.sources.sourceDirectories } )
            sources.exclude("**/*.h")
        }
    }
    archives {
        create("main") {
            source( cxx.named("main").map { it.outputs } )
        }
    }
}

// wire to base tasks

val archive = tasks.register("archive") {
    group = "metal"
    dependsOn(
        tasks.named("archive-main")
    )
}

val compile = tasks.register("compile") {
    group = "metal"
    dependsOn(
        tasks.named("compile-main-cxx")
    )
}

tasks.assemble.configure {
    dependsOn(archive)
}