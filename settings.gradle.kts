plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "A-Maker-BE"
include("domain")
include("api")
include("infra")
include("notification")
include("common")
include("batch")
