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
val springBootVersion               = property("springBoot")               as String
val springCloudVersion              = property("springCloud")              as String
val springBootAdminVersion          = property("springBootAdmin")          as String
val springCloudAwsVersion           = property("springCloudAws")          as String
val springCloudGcpVersion           = property("springCloudGcp")          as String
val springCloudAlibabaVersion       = property("springCloudAlibaba")      as String
val debeziumVersion                 = property("debezium")                as String
val springIntegrationDebeziumVersion = property("springIntegrationDebezium") as String
val logbackVersion                  = property("logback")                 as String
val slf4jVersion                    = property("slf4j")                   as String
val clickhouseJdbcVersion           = property("clickhouseJdbc")          as String
val redissonVersion                 = property("redisson")                as String
val curatorVersion                  = property("curator")                 as String
val jetcdVersion                    = property("jetcd")                   as String
val jmhVersion                      = property("jmh")                    as String
val elasticsearchJavaV8Version      = property("elasticsearchJavaV8")     as String
val elasticsearchJavaV9Version      = property("elasticsearchJavaV9")     as String
val easyexcelVersion                = property("easyexcel")               as String
val testcontainersVersion           = property("testcontainers")          as String
val commonsLang3Version             = property("commonsLang3")            as String
val commonsCollections4Version      = property("commonsCollections4")     as String
val commonsIoVersion                = property("commonsIo")               as String
val guavaVersion                    = property("guava")                   as String
val dom4jVersion                    = property("dom4j")                   as String
val springdocVersion                = property("springdoc")               as String
val apolloClientVersion             = property("apolloClient")            as String
val nexusPublishVersion             = property("nexusPublish")            as String

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
                const val SPRING_CLOUD                   = "$springCloudVersion"
                const val SPRING_BOOT_ADMIN              = "$springBootAdminVersion"
                const val SPRING_CLOUD_AWS               = "$springCloudAwsVersion"
                const val SPRING_CLOUD_GCP               = "$springCloudGcpVersion"
                const val SPRING_CLOUD_ALIBABA           = "$springCloudAlibabaVersion"
                const val DEBEZIUM                       = "$debeziumVersion"
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
                const val EASYEXCEL                      = "$easyexcelVersion"
                const val TESTCONTAINERS                 = "$testcontainersVersion"
                const val COMMONS_LANG3                  = "$commonsLang3Version"
                const val COMMONS_COLLECTIONS4           = "$commonsCollections4Version"
                const val COMMONS_IO                     = "$commonsIoVersion"
                const val GUAVA                          = "$guavaVersion"
                const val DOM4J                          = "$dom4jVersion"
                const val SPRINGDOC                      = "$springdocVersion"
                const val APOLLO_CLIENT                  = "$apolloClientVersion"
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
    implementation("io.github.gradle-nexus:publish-plugin:$nexusPublishVersion")
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