pluginManagement {
    val kotlinVersion: String by settings

    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        id("org.jetbrains.kotlin.jvm") version kotlinVersion apply(false)
    }
}

include("ast", "codegen")
