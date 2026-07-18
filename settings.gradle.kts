enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ksword-multiplatform"
include(":ksword")

// ktar is consumed from Maven Central (io.github.mjdenham:ktar). It rarely changes, so the
// composite build is off by default for reproducible, standalone builds. Uncomment to develop
// ktar and ksword together — Gradle then substitutes the local ../ktar-multiplatform checkout.
// includeBuild("../ktar-multiplatform")