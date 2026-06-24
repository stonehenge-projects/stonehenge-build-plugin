plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "org.stonehenge.build"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

// Read all versions from gradle.properties — single source of truth
val springBootVersion               = property("springBoot") as String
val springDependencyManagementVersion = property("springDependencyManagement") as String
val debeziumVersion                 = property("debezium") as String
val springCloudVersion              = property("springCloud") as String
val springBootAdminVersion          = property("springBootAdmin") as String
val testcontainersVersion           = property("testcontainers") as String
val springIntegrationDebeziumVersion = property("springIntegrationDebezium") as String
val logbackVersion                  = property("logback") as String
val slf4jVersion                    = property("slf4j") as String
val clickhouseJdbcVersion           = property("clickhouseJdbc") as String
val redissonVersion                 = property("redisson") as String
val curatorVersion                  = property("curator") as String
val jetcdVersion                    = property("jetcd") as String
val jmhVersion                      = property("jmh") as String
val elasticsearchJavaV8Version      = property("elasticsearchJavaV8") as String
val elasticsearchJavaV9Version      = property("elasticsearchJavaV9") as String

// Generate Versions.kt so convention plugins can access versions at runtime
val generateVersions by tasks.registering {
    val outputDir = layout.buildDirectory.dir("generated-sources/kotlin/org/stonehenge/build")
    outputs.dir(outputDir)
    doLast {
        val file = outputDir.get().file("Versions.kt").asFile
        file.parentFile.mkdirs()
        file.writeText("""
            package org.stonehenge.build

            internal object Versions {
                const val SPRING_BOOT                    = "$springBootVersion"
                const val SPRING_DEPENDENCY_MANAGEMENT   = "$springDependencyManagementVersion"
                const val DEBEZIUM                       = "$debeziumVersion"
                const val SPRING_CLOUD                   = "$springCloudVersion"
                const val SPRING_BOOT_ADMIN              = "$springBootAdminVersion"
                const val TESTCONTAINERS                 = "$testcontainersVersion"
                const val SPRING_INTEGRATION_DEBEZIUM    = "$springIntegrationDebeziumVersion"
                const val LOGBACK                        = "$logbackVersion"
                const val SLF4J                          = "$slf4jVersion"
                const val CLICKHOUSE_JDBC                = "$clickhouseJdbcVersion"
                const val REDISSON                       = "$redissonVersion"
                const val CURATOR                        = "$curatorVersion"
                const val JETCD                          = "$jetcdVersion"
                const val JMH                            = "$jmhVersion"
                const val ELASTICSEARCH_JAVA_V8          = "$elasticsearchJavaV8Version"
                const val ELASTICSEARCH_JAVA_V9          = "$elasticsearchJavaV9Version"
            }
        """.trimIndent())
    }
}

sourceSets.main {
    kotlin.srcDir(layout.buildDirectory.dir("generated-sources/kotlin"))
}

tasks.compileKotlin {
    dependsOn(generateVersions)
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
    implementation("io.spring.gradle:dependency-management-plugin:$springDependencyManagementVersion")
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