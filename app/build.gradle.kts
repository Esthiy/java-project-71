group = "hexlet.code"
version = "1.0-SNAPSHOT"

plugins {
    id("java")
    application
    checkstyle
}

application {
    mainClass.set("hexlet.code.App")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("info.picocli:picocli:4.7.5")
    annotationProcessor("info.picocli:picocli-codegen:4.7.5")
}

tasks.test {
    useJUnitPlatform()
}