plugins {
    application
    java
    id("org.danilopianini.gradle-java-qa") version "1.200.0"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.10.5")
}

application {
    mainClass.set("it.unibo.mvc.SimpleGUI")
}

tasks.javadoc {
    isFailOnError = false
}
