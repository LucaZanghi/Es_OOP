plugins {
    java
    id("org.danilopianini.gradle-java-qa") version "1.199.0"
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
    // The BOM (Bill of Materials) synchronizes all the versions of Junit coherently.
    testImplementation(platform("org.junit:junit-bom:6.1.3"))
    // The annotations, assertions and other elements we want to have access to when compiling our tests.
    testImplementation("org.junit.jupiter:junit-jupiter")
    // The engine that must be available at runtime to run the tests.
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    testLogging { events("passed", "skipped", "failed") }
    testLogging.showStandardStreams = true
}

spotbugs {
    // FindOverridableMethodCall flags the double-brace initialization in the tests,
    // which is used on purpose to showcase anonymous classes
    omitVisitors.set(listOf("FindOverridableMethodCall"))
}

tasks.javadoc {
    isFailOnError = false
}
