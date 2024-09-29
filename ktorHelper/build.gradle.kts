plugins {
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    kotlin("plugin.serialization") version "2.0.10"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.kotlinx.serialization.json)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "com.stwcoding.networkmodule"
            artifactId = "ktorhelper"
            version = "1.1.0-SNAPSHOT"

            pom {
                name = "Ktor Helper"
                description = ""
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        name = "Leo Wong"
                        email = "singtsun9941@gmail.com"
                    }
                }
            }
        }
    }
    repositories {
        if (!version.toString().endsWith("SNAPSHOT")) {
            maven {
                url = uri(System.getenv("MY_MAVEN_REPO_PATH"))
            }

            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/singtsun9941/KtorHelper")
                credentials {
                    username = System.getenv("GITHUB_USERNAME")
                    password = System.getenv("GITHUB_PUBLISH_TOKEN")
                }
            }
        }
    }
}