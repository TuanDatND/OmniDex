plugins {
    java
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.dat"
version = "0.0.1-SNAPSHOT"
description = "OmniDex"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

extra["jjwtVersion"] = "0.12.7"
extra["mapstructVersion"] = "1.6.3"

dependencies {

    // =========================
    // SPRING CORE
    // =========================
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // =========================
    // SECURITY
    // =========================
    implementation("org.springframework.boot:spring-boot-starter-security")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:${property("jjwtVersion")}")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${property("jjwtVersion")}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${property("jjwtVersion")}")

    // =========================
    // DATABASE
    // =========================

    // PostgreSQL + JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // MongoDB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    // =========================
    // REALTIME
    // =========================
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    // =========================
    // CACHE
    // =========================
    implementation("org.springframework.boot:spring-boot-starter-cache")

    // =========================
    // DATABASE MIGRATION
    // =========================
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    // =========================
    // OPEN API / SWAGGER
    // =========================
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")

    // =========================
    // MONITORING
    // =========================
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // =========================
    // MAIL
    // =========================
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // =========================
    // RATE LIMITING
    // =========================
    implementation("com.bucket4j:bucket4j-core:8.14.0")

    // =========================
    // MAPPING & LOMBOK
    // =========================

    implementation("org.mapstruct:mapstruct:${property("mapstructVersion")}")

    compileOnly("org.projectlombok:lombok")

    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    annotationProcessor("org.mapstruct:mapstruct-processor:${property("mapstructVersion")}")

    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    // =========================
    // JSON DATE/TIME
    // =========================
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // =========================
    // DEVELOPMENT
    // =========================
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    //==========================
    //Optional
    //==========================
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.9.5")
    implementation("org.apache.commons:commons-lang3")

    // =========================
    // TESTING
    // =========================
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    // Testcontainers
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:mongodb")
    testImplementation("org.testcontainers:redis")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
    options.compilerArgs.add("-Amapstruct.defaultComponentModel=spring")
}
