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
    accessTransformers.file("src/main/resources/accesstransformer.cfg")
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
    
    // Bukkit
    implementation("org.yaml:snakeyaml:2.2")
    implementation("org.apache.maven:maven-resolver-provider:3.9.6")
    implementation("org.apache.maven.resolver:maven-resolver-connector-basic:1.9.18")
    implementation("org.apache.maven.resolver:maven-resolver-transport-http:1.9.18")
    
    // CraftBukkit
    implementation("jline:jline:2.12.1")
    implementation("org.apache.logging.log4j:log4j-iostreams:2.22.1")
    implementation("commons-lang:commons-lang:2.6")
    implementation("com.googlecode.json-simple:json-simple:1.1.1") {
        exclude("junit")
    }
    implementation("org.xerial:sqlite-jdbc:3.45.3.0")
    implementation("com.mysql:mysql-connector-j:8.3.0")
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
