import org.stonehenge.build.Versions

plugins {
    `java-library`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.withType<Javadoc>().configureEach {
    options.encoding = "UTF-8"
    (options as StandardJavadocDocletOptions).apply {
        charSet = "UTF-8"
        addStringOption("Xdoclint:none", "-quiet")
    }
}

dependencies {
    // ── BOMs ──────────────────────────────────────────────────────────────────
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${Versions.SPRING_BOOT}"))
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:${Versions.SPRING_CLOUD}"))
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:${Versions.SPRING_CLOUD_AWS}"))
    implementation(platform("com.google.cloud:spring-cloud-gcp-dependencies:${Versions.SPRING_CLOUD_GCP}"))
    implementation(platform("com.alibaba.cloud:spring-cloud-alibaba-dependencies:${Versions.SPRING_CLOUD_ALIBABA}"))
    implementation(platform("de.codecentric:spring-boot-admin-dependencies:${Versions.SPRING_BOOT_ADMIN}"))
    implementation(platform("org.testcontainers:testcontainers-bom:${Versions.TESTCONTAINERS}"))
    implementation(platform("io.debezium:debezium-bom:${Versions.DEBEZIUM}"))
    annotationProcessor(platform("org.springframework.boot:spring-boot-dependencies:${Versions.SPRING_BOOT}"))
    // Required by Gradle 9+ for JUnit Platform to discover and run tests
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // ── Version constraints for non-BOM dependencies ───────────────────────────
    constraints {
        implementation("org.springframework.integration:spring-integration-debezium:${Versions.SPRING_INTEGRATION_DEBEZIUM}")
        // Debezium BOM may pin logback/SLF4J to versions incompatible with Spring Boot 4.x
        implementation("ch.qos.logback:logback-classic:${Versions.LOGBACK}")
        implementation("ch.qos.logback:logback-core:${Versions.LOGBACK}")
        implementation("org.slf4j:slf4j-api:${Versions.SLF4J}")
        implementation("org.slf4j:jul-to-slf4j:${Versions.SLF4J}")
        implementation("org.slf4j:log4j-over-slf4j:${Versions.SLF4J}")
        implementation("com.clickhouse:clickhouse-jdbc:${Versions.CLICKHOUSE_JDBC}")
        implementation("org.redisson:redisson:${Versions.REDISSON}")
        implementation("org.apache.curator:curator-framework:${Versions.CURATOR}")
        implementation("org.apache.curator:curator-recipes:${Versions.CURATOR}")
        implementation("org.apache.curator:curator-client:${Versions.CURATOR}")
        implementation("io.etcd:jetcd-core:${Versions.JETCD}")
        implementation("org.openjdk.jmh:jmh-core:${Versions.JMH}")
        implementation("org.openjdk.jmh:jmh-generator-annprocess:${Versions.JMH}")
        annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:${Versions.JMH}")
        implementation("com.alibaba:easyexcel:${Versions.EASYEXCEL}")
        implementation("org.apache.commons:commons-lang3:${Versions.COMMONS_LANG3}")
        implementation("org.apache.commons:commons-collections4:${Versions.COMMONS_COLLECTIONS4}")
        implementation("commons-io:commons-io:${Versions.COMMONS_IO}")
        implementation("com.google.guava:guava:${Versions.GUAVA}")
        implementation("org.dom4j:dom4j:${Versions.DOM4J}")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${Versions.SPRINGDOC}")
        implementation("com.ctrip.framework.apollo:apollo-client:${Versions.APOLLO_CLIENT}")
        implementation("com.ctrip.framework.apollo:apollo-openapi:${Versions.APOLLO_CLIENT}")
        // xxl ecosystem
        implementation("com.xuxueli:xxl-job-core:${Versions.XXL_JOB}")
        implementation("com.xuxueli:xxl-sso-core:${Versions.XXL_SSO}")
        implementation("com.xuxueli:xxl-tool:${Versions.XXL_TOOL}")

        // elasticjob
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-api:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-infra-common:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-restful:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-executor-kernel:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-executor-type:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-simple-executor:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-dataflow-executor:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-script-executor:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-http-executor:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-registry-center-api:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-registry-center-zookeeper-curator:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-regitry-center-provider:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-tracing-api:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-tracing-rdb:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-error-handler-spi:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-error-handler-type:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-error-handler-general:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-error-handler-email:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-lite-core:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-lite-lifecycle:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-lite-spring-core:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-lite-spring-namespace:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-lite-spring-boot-starter:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-cloud-common:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-cloud-executor:${Versions.ELASTICJOB}")
        implementation("org.apache.shardingsphere.elasticjob:elasticjob-cloud-scheduler:${Versions.ELASTICJOB}")
    }
}