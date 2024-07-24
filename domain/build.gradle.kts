plugins {
    kotlin("jvm")
}

group = "com.backgu.amaker"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
