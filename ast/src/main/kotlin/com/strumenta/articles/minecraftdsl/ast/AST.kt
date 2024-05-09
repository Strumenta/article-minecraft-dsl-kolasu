package com.strumenta.articles.minecraftdsl.ast

import com.strumenta.kolasu.model.Node

data class Mod(
    val id: String,
    val name: String,
    val version: String,
    val license: String
) : Node()
