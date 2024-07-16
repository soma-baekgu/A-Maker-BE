import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.3.0" apply false
    id("io.spring.dependency-management") version "1.1.5" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.jpa") version "1.9.24" apply false
    kotlin("plugin.spring") version "1.9.24" apply false
    kotlin("kapt") version "1.9.24"
}

allprojects {
    group = "com.backgu.amaker"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.jetbrains.kotlin.kapt")

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("io.github.oshai:kotlin-logging-jvm:5.1.1")
        implementation("com.mysql:mysql-connector-j:8.4.0")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
        implementation("com.querydsl:querydsl-apt:5.0.0:jakarta")
        implementation("jakarta.persistence:jakarta.persistence-api")
        implementation("jakarta.annotation:jakarta.annotation-api")
        kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
        kapt("org.springframework.boot:spring-boot-configuration-processor")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testImplementation("io.mockk:mockk:1.13.11")
        testImplementation("com.ninja-squad:springmockk:4.0.2")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        testRuntimeOnly("com.h2database:h2")
    }
}
