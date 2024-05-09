plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java-library")
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
}

dependencies {
    implementation("com.strumenta.kolasu:kolasu-core:1.5.56")
}
