plugins {
    kotlin("jvm")
}

group = "com.backgu.amaker"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.auth0:java-jwt:3.18.3")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
