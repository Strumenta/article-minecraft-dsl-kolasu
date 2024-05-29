plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java-library")
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
    antlr
}

dependencies {
    implementation("com.strumenta.kolasu:kolasu-core:1.5.56")
    antlr("org.antlr:antlr4:4.13.1")
}

tasks.compileKotlin {
    dependsOn(tasks.generateGrammarSource)
}

tasks.runKtlintCheckOverMainSourceSet {
    dependsOn(tasks.generateGrammarSource)
}
