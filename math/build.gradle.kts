import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.dokka)
    alias(libs.plugins.mavenPublish)
    signing
}

val htmlJar = tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaGeneratePublicationHtml)
    from(tasks.dokkaGeneratePublicationHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

tasks.withType<Sign>().configureEach { dependsOn(htmlJar.get()) }
tasks.withType<AbstractPublishToMaven>().configureEach { mustRunAfter(tasks.withType<Sign>()) }

kotlin {
    jvm()
    androidNativeX64()
    androidNativeX86()
    androidNativeArm64()
    androidNativeArm32()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    linuxX64()
    linuxArm64()
    mingwX64()
    js {
        browser()
        nodejs()
    }
    @OptIn(ExperimentalWasmDsl::class) wasmJs {
        browser()
        nodejs()
        d8()
    }

    applyDefaultHierarchyTemplate()
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    pom {
        name = project.name
        description = "A Kotlin multiplatform library for mathematical operations and utilities."
        url = "https://github.com/xfqwdsj/ltmath"

        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/license/mit/"
                distribution = "repo"
            }
        }

        developers {
            developer {
                id = "xfqwdsj"
                name = "LTFan"
                email = "xfqwdsj@qq.com"
                roles = listOf("Author", "Maintainer")
            }
        }

        scm {
            connection = "scm:git:https://github.com/xfqwdsj/ltmath.git"
            developerConnection = "scm:git:https://github.com/xfqwdsj/ltmath.git"
            url = "https://github.com/xfqwdsj/ltmath"
        }
    }

    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka(htmlJar),
        )
    )
}

publishing {
    repositories {
        maven {
            name = "gitHubPackages"
            url = uri("https://maven.pkg.github.com/xfqwdsj/ltmath")
            credentials(PasswordCredentials::class)
        }
    }
}

signing {
    sign(publishing.publications)
    val publishSigningMode = findProperty("publishSigningMode") as String?
    if (publishSigningMode == "inMemory") return@signing
    useGpgCmd()
}

group = "top.ltfan.math"
version = "0.1.0"
