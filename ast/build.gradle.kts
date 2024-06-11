val kotlinVersion: String by properties

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java-library")
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
    antlr
}

dependencies {
    api("com.strumenta.kolasu:kolasu-core:1.5.56")
    antlr("org.antlr:antlr4:4.13.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

tasks.compileKotlin {
    dependsOn(tasks.generateGrammarSource)
}

tasks.compileTestKotlin {
    dependsOn(tasks.generateGrammarSource)
    dependsOn(tasks.generateTestGrammarSource)
}

tasks.runKtlintCheckOverMainSourceSet {
    dependsOn(tasks.generateGrammarSource)
}
