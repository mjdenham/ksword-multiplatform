import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKmpLibrary)
    id("com.vanniktech.maven.publish") version "0.30.0"
}

group = "io.github.mjdenham"

kotlin {
    explicitApiWarning()

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    androidLibrary {
        namespace = "org.crosswire.ksword"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }

        withHostTestBuilder {}
    }

    val xcf = XCFramework()
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ksword"
            xcf.add(this)
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(libs.atomicfu)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.encoding)
            implementation(libs.okio)
            implementation("io.github.mjdenham:ktar:0.1.0")
        }
        commonTest.dependencies {
            implementation(libs.junit)
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates("io.github.mjdenham", "ksword", "0.1.0")

    pom {
        name.set("KSword")
        description.set("Kotlin Multiplatform library for reading CrossWire Sword Bible modules, derived from JSword.")
        inceptionYear.set("2024")
        url.set("https://github.com/mjdenham/ksword-multiplatform")
        licenses {
            license {
                name.set("GNU Lesser General Public License, version 2.1")
                url.set("https://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt")
                distribution.set("https://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt")
            }
        }
        developers {
            developer {
                id.set("mjdenham")
                name.set("Martin Denham")
                url.set("https://github.com/mjdenham")
            }
        }
        scm {
            url.set("https://github.com/mjdenham/ksword-multiplatform")
            connection.set("scm:git:git://github.com/mjdenham/ksword-multiplatform.git")
            developerConnection.set("scm:git:ssh://git@github.com/mjdenham/ksword-multiplatform.git")
        }
    }
}
