plugins {
    `maven-publish`
    signing
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            versionMapping {
                usage("java-api") { fromResolutionOf("runtimeClasspath") }
                usage("java-runtime") { fromResolutionResult() }
            }
            pom {
                name.set(provider { project.name })
                description.set(provider { project.description ?: project.name })
                url.set("https://github.com/stonehenge-projects/${rootProject.name}")
                inceptionYear.set("2025")
                licenses {
                    license {
                        name.set("Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("BuDong-Fei")
                        name.set("BuDong")
                        email.set("budong.fei@icloud.com")
                        timezone.set("Asia/Shanghai")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/stonehenge-projects/${rootProject.name}.git")
                    developerConnection.set("scm:git:ssh://git@github.com/stonehenge-projects/${rootProject.name}.git")
                    url.set("https://github.com/stonehenge-projects/${rootProject.name}")
                    tag.set("HEAD")
                }
                issueManagement {
                    system.set("GitHub Issues")
                    url.set("https://github.com/stonehenge-projects/${rootProject.name}/issues")
                }
            }
        }
    }
    repositories {
        maven {
            name = "sonatype"
            url = if (version.toString().endsWith("SNAPSHOT"))
                uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            else
                uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = providers.gradleProperty("ossrhUsername").orNull
                password = providers.gradleProperty("ossrhPassword").orNull
            }
        }
    }
}

// Signing is skipped automatically when signingKey is absent (local dev friendly)
signing {
    val signingKey = providers.gradleProperty("signingKey")
    val signingPassword = providers.gradleProperty("signingPassword")
    if (signingKey.isPresent) {
        useInMemoryPgpKeys(signingKey.get(), signingPassword.orNull)
        sign(publishing.publications["maven"])
    }
}