plugins {
    `kotlin-dsl`
    `maven-publish`
//    signing  // uncomment when publishing to Maven Central
}

group = "org.stonehenge.build"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:4.0.7")
    implementation("io.github.gradle-nexus:publish-plugin:2.0.0")
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                name.set(provider { project.name })
                description.set(provider { project.description ?: project.name })
                url.set("https://github.com/stonehenge-projects/stonehenge-build-plugin")
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
                    connection.set("scm:git:git://github.com/stonehenge-projects/stonehenge-build-plugin.git")
                    developerConnection.set("scm:git:ssh://git@github.com/stonehenge-projects/stonehenge-build-plugin.git")
                    url.set("https://github.com/stonehenge-projects/stonehenge-build-plugin")
                    tag.set("HEAD")
                }
                issueManagement {
                    system.set("GitHub Issues")
                    url.set("https://github.com/stonehenge-projects/stonehenge-build-plugin/issues")
                }
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/stonehenge-projects/stonehenge-build-plugin")
            credentials(PasswordCredentials::class)
        }
        // Maven Central — uncomment to enable
//        maven {
//            name = "sonatype"
//            url = if (version.toString().endsWith("SNAPSHOT"))
//                uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
//            else
//                uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
//            credentials {
//                username = providers.gradleProperty("ossrhUsername").orNull
//                password = providers.gradleProperty("ossrhPassword").orNull
//            }
//        }
    }
}

// Maven Central signing — uncomment together with signing plugin and sonatype repository
//signing {
//    val signingKey = providers.gradleProperty("signingKey")
//    val signingPassword = providers.gradleProperty("signingPassword")
//    if (signingKey.isPresent) {
//        useInMemoryPgpKeys(signingKey.get(), signingPassword.orNull)
//        sign(publishing.publications["pluginMaven"])
//    }
//}