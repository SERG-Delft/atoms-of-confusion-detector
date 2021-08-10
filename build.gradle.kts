import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    id("io.gitlab.arturbosch.detekt") version "1.17.1"
}

group = "org.serg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.17.1")
    implementation("com.github.ajalt.clikt:clikt:3.2.0")
    implementation("org.antlr:antlr4-runtime:4.9.2")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:0.15.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
    implementation("org.antlr:symtab:1.0.8")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Detekt> {
    jvmTarget = "1.8"
}

detekt {
    config = files("./detekt-config.yml")
    buildUponDefaultConfig = true

    reports {
        html.enabled = false
        xml.enabled = false
        txt.enabled = false
    }
}
