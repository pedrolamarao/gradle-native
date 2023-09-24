plugins {
    id("base")
    id("br.dev.pedrolamarao.metal.cxx")
    id("br.dev.pedrolamarao.metal.base")
}

dependencies {
    nativeImplementation(project(":googletest"))
}

// register "main" archive with cpp and cxx sources

val mainCpp = metal.cpp.sources("main")

val mainIxx = metal.ixx.sources("main") {
    compileOptions = listOf("-g","--std=c++20")
    compileTask.configure {
        headerDependencies.from(mainCpp.sources.sourceDirectories)
    }
}

val mainCxx = metal.cxx.sources("main") {
    compileOptions = listOf("-g","--std=c++20")
    compileTask.configure {
        headerDependencies.from(mainCpp.sources.sourceDirectories)
        moduleDependencies.from(mainIxx.compileTask)
        source(mainIxx.compileTask)
    }
}

val mainArchive = metal.archive("main") {
    archiveTask.configure {
        source(mainCxx.compileTask)
    }
}

// register "test" application with cxx sources

val testCxx = metal.cxx.sources("test") {
    compileOptions = listOf("-g","--std=c++17")
    compileTask.configure {
        headerDependencies.from(mainCpp.sources.sourceDirectories)
        moduleDependencies.from(mainIxx.compileTask)
    }
}

val testApplication = metal.application("test") {
    linkTask.configure {
        source(mainCxx.compileTask)
        source(testCxx.compileTask)
    }
}

// wire to base tasks

val archive = tasks.register("archive") {
    group = "metal"
    dependsOn(mainArchive.archiveTask)
}

tasks.register("commands") {
    group = "metal"
    dependsOn("commands-main-cxx")
}

tasks.register("compile") {
    group = "metal"
    dependsOn(mainCxx.compileTask,testCxx.compileTask);
}

val link = tasks.register("link") {
    group = "metal"
    dependsOn(testApplication.linkTask)
}

tasks.assemble.configure {
    dependsOn(archive,link)
}