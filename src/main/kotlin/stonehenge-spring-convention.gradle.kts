import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.stonehenge.build.Versions

plugins {
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
}

configure<DependencyManagementExtension> {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:${Versions.SPRING_BOOT}")
        mavenBom("io.debezium:debezium-bom:${Versions.DEBEZIUM}")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${Versions.SPRING_CLOUD}")
        mavenBom("de.codecentric:spring-boot-admin-dependencies:${Versions.SPRING_BOOT_ADMIN}")
        mavenBom("org.testcontainers:testcontainers-bom:${Versions.TESTCONTAINERS}")
    }
    dependencies {
        dependency("org.springframework.integration:spring-integration-debezium:${Versions.SPRING_INTEGRATION_DEBEZIUM}")
        // Debezium BOM pins logback/SLF4J to versions incompatible with Spring Boot 4.x
        dependency("ch.qos.logback:logback-classic:${Versions.LOGBACK}")
        dependency("ch.qos.logback:logback-core:${Versions.LOGBACK}")
        dependency("org.slf4j:slf4j-api:${Versions.SLF4J}")
        dependency("org.slf4j:jul-to-slf4j:${Versions.SLF4J}")
        dependency("org.slf4j:log4j-over-slf4j:${Versions.SLF4J}")
        dependency("com.clickhouse:clickhouse-jdbc:${Versions.CLICKHOUSE_JDBC}")
        dependency("org.redisson:redisson:${Versions.REDISSON}")
    }
}