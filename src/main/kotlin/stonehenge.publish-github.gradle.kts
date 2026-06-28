plugins {
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            versionMapping {
                usage("java-api") { fromResolutionOf("runtimeClasspath") }
                usage("java-runtime") { fromResolutionResult() }
            }
            pom {
                url.set("https://github.com/stonehenge-projects/${rootProject.name}")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("stonehenge-projects")
                        name.set("budong")
                        email.set("budong.fei@protonmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/stonehenge-projects/${rootProject.name}.git")
                    developerConnection.set("scm:git:ssh://github.com/stonehenge-projects/${rootProject.name}.git")
                    url.set("https://github.com/stonehenge-projects/${rootProject.name}")
                }
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/stonehenge-projects/${rootProject.name}")
            credentials(PasswordCredentials::class)
        }
    }
}