import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.github.johnrengelman.shadow") version "7.0.0"
    java
    kotlin("plugin.serialization") version "1.5.20"
    kotlin("jvm") version "1.5.20"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("io.ktor:ktor-server-core:1.6.1")
    implementation("io.ktor:ktor-server-cio:1.6.1")
    implementation("io.ktor:ktor-serialization:1.6.1")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.2.2")

    shadow(files("run/ModTheSpire.jar"))
    shadow(files("run/desktop-1.0.jar"))
    shadow(files("run/mods/BaseMod.jar"))
    // shadow(files("run/mods/CommunicationMod.jar"))
    implementation(kotlin("stdlib"))
}

val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions.jvmTarget = "1.8"
compileKotlin.kotlinOptions.freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")

tasks.register<Copy>("copyJar") {
    from("$buildDir/libs/${rootProject.name}-$version-all.jar")
    into("run/mods")
}

tasks.register<JavaExec>("runSTS") {
    classpath = files("run/ModTheSpire.jar")
    workingDir = file("run/")
    args("--skip-launcher", "--skip-intro", "--debug", "--profile", "Default")
}
