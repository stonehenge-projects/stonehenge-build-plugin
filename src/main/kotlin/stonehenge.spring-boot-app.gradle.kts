plugins {
    java
    id("org.springframework.boot")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// bootJar is the primary artifact; plain jar is not needed for apps
tasks.named<Jar>("jar") {
    enabled = false
}