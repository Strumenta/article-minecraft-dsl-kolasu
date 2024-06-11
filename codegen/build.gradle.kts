plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java-library")
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
}

dependencies {
    api(project(":ast"))
    implementation("org.apache.commons:commons-configuration2:2.9.0")
    implementation("commons-beanutils:commons-beanutils:1.9.4")
}
