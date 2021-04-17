import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    application
}

group = "me.hives"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:4.6.0.0"))

    implementation("ch.qos.logback:logback-classic:1.3.0-alpha4")

    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-server-jetty")
    implementation("org.http4k:http4k-format-jackson")
}

application {
    mainClass.set("MainKt")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}