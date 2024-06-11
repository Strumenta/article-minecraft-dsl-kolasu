package com.strumenta.articles.minecraftdsl.codegen

import com.strumenta.articles.minecraftdsl.ast.Mod
import org.apache.commons.configuration2.Configuration
import org.apache.commons.configuration2.FileBasedConfiguration
import org.apache.commons.configuration2.PropertiesConfiguration
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder
import org.apache.commons.configuration2.builder.fluent.Parameters
import java.io.File


fun generate(mod: Mod, sdkDir: File) {
    if (!sdkDir.isDirectory) {
        throw IllegalArgumentException("Not a directory: $sdkDir")
    }
    val gradlePropertiesFile = File(sdkDir, "gradle.properties")
    val params = Parameters()
    val builder =
        FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration::class.java)
            .configure(params.properties().setFile(gradlePropertiesFile))
    val config: Configuration = builder.configuration
    config.setProperty("mod_id", mod.id)
    config.setProperty("mod_name", mod.name)
    config.setProperty("mod_version", mod.version)
    if (mod.license != null) {
        config.setProperty("mod_license", mod.license)
    }
    if (mod.groupId != null) {
        config.setProperty("mod_group_id", mod.groupId)
    }
    config.setProperty("mod_authors", mod.authors.joinToString(", "))
    config.setProperty("mod_description", mod.description)
    builder.save()
}

fun main() {
    generate(Mod("my_new_mod", "My new mod", "1.0", "Apache"), File("../../forge-1.20.6-50.1.2-mdk"))
}
