plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "org.stonehenge.build"
version = "1.0.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:4.0.6")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.7")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/stonehenge-projects/stonehenge-build-plugin")
            credentials(PasswordCredentials::class)
        }
    }
}