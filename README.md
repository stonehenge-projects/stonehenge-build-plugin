# stonehenge-build-plugin

Gradle convention plugins for Stonehenge-series projects.

Provides standardized build conventions for Java 25 / Spring Boot 4.x projects, covering toolchain configuration, compile options, test setup, and publishing.

## Requirements

- Java 25+
- Gradle 9.5+

## Plugins

| Plugin ID | Purpose |
|---|---|
| `stonehenge.java-library` | Java library: toolchain, sources jar, javadoc jar, test setup |
| `stonehenge.spring-boot-app` | Spring Boot application: bootJar, toolchain, test setup |
| `stonehenge.publish-github` | Publish to GitHub Packages |
| `stonehenge.publish-maven-central` | Publish to Maven Central |

## Usage

### settings.gradle.kts

```kotlin
pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/stonehenge-projects/stonehenge-build-plugin")
            credentials(PasswordCredentials::class)
        }
        gradlePluginPortal()
    }
}
```

### build.gradle.kts

```kotlin
plugins {
    id("stonehenge.java-library") version "1.0.0"
    // or
    id("stonehenge.spring-boot-app") version "1.0.0"
}
```

## Distribution

Published to [GitHub Packages](https://github.com/stonehenge-projects/stonehenge-build-plugin/packages).