import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    id("io.gitlab.arturbosch.detekt") version "1.17.1"
    id("com.github.johnrengelman.shadow") version "4.0.4"
    jacoco
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
    implementation("org.jsoup:jsoup:1.14.2")
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("com.googlecode.grep4j:grep4j:1.8.7")
    implementation("org.antlr:symtab:1.0.8")
    testImplementation("io.mockk:mockk:1.9.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.6.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.1")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.6.1")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("shadow")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "MainKt"))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

jacoco {
    toolVersion = "0.8.7"
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
