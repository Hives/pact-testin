import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    id("au.com.dius.pact") version "4.0.2"

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
    implementation("org.http4k:http4k-client-okhttp")

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.22")
    testImplementation("au.com.dius.pact:consumer:4.2.4")
}

application {
    mainClass.set("MainKt")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}