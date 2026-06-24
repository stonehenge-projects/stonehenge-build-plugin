import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
}

// All third-party versions centralized here — plugin and BOM use the same value
val springBootVersion       = "4.0.6"
val debeziumVersion         = "3.5.1.Final"
val springCloudVersion      = "2025.1.0"
val springBootAdminVersion  = "4.0.4"
val testcontainersVersion   = "1.21.4"

configure<DependencyManagementExtension> {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
        mavenBom("io.debezium:debezium-bom:$debeziumVersion")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
        mavenBom("de.codecentric:spring-boot-admin-dependencies:$springBootAdminVersion")
        mavenBom("org.testcontainers:testcontainers-bom:$testcontainersVersion")
    }
    dependencies {
        dependency("org.springframework.integration:spring-integration-debezium:7.0.4")
        // Debezium BOM pins logback/SLF4J to versions incompatible with Spring Boot 4.x
        dependency("ch.qos.logback:logback-classic:1.5.32")
        dependency("ch.qos.logback:logback-core:1.5.32")
        dependency("org.slf4j:slf4j-api:2.0.17")
        dependency("org.slf4j:jul-to-slf4j:2.0.17")
        dependency("org.slf4j:log4j-over-slf4j:2.0.17")
        dependency("com.clickhouse:clickhouse-jdbc:0.7.2")
        dependency("org.redisson:redisson:3.45.0")
    }
}