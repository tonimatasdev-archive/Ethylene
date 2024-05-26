plugins {
    id("java-library")
    id("idea")
    id("net.neoforged.gradle.userdev") version "7.+"
}

val minecraftVersion: String by extra
val neoVersion: String by extra
val neoVersionRange: String by extra
val loaderVersionRange: String by extra
val modVersion: String by extra

version = modVersion
group = "dev.tonimatas"

repositories {
    mavenLocal()
}

base.archivesName = "ethylene"
idea.module.isDownloadJavadoc = true
java.toolchain.languageVersion = JavaLanguageVersion.of(21)

minecraft {
    //accessTransformers.file rootProject.file("src/main/resources/accesstransformer.cfg")
}

runs {
    configureEach {
        systemProperty("forge.logging.markers", "REGISTRIES")
        systemProperty("forge.logging.console.level", "debug")
        modSource(project.sourceSets.main.get())
    }

    create("server") {
        systemProperty("forge.enabledGameTestNamespaces", "ethylene")
        programArgument("--nogui")
    }
    
    create("gameTestServer") {
        systemProperty("forge.enabledGameTestNamespaces", "ethylene")
    }
}

dependencies {
    implementation("net.neoforged:neoforge:${neoVersion}")
}

tasks.withType<ProcessResources> {
    val replaceProperties = mapOf("minecraftVersion" to minecraftVersion, "neoVersionRange" to neoVersionRange,
        "loaderVersionRange" to loaderVersionRange, "modVersion" to modVersion)
    inputs.properties(replaceProperties)

    filesMatching("META-INF/neoforge.mods.toml") {
        expand(replaceProperties)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
