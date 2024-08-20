// Copyright (c) Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

plugins {
    id("base")
    id("io.qameta.allure-aggregate-report")
}

group = "br.dev.pedrolamarao.gradle.metal"
version = "0.6-SNAPSHOT"

allure {
    environment.put("ALLURE_NO_ANALYTICS","true")
    environment.put("JAVA_HOME",System.getProperty("java.home"))
    version = "2.30.0"
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}

tasks.allureAggregateReport.configure {
    clean = true
    singleFile = true
}

tasks.wrapper.configure {
    gradleVersion = "8.6"
}
