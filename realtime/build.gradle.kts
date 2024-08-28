import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

group = "com.backgu.amaker"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":infra"))
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    testImplementation(kotlin("test"))
    testImplementation("com.redis.testcontainers:testcontainers-redis-junit:1.6.4")
    testImplementation("org.testcontainers:testcontainers:1.19.0")
    testImplementation("org.testcontainers:mysql:1.16.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}
