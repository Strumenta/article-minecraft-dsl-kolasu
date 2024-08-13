package com.strumenta.articles.minecraftdsl.ast

import com.strumenta.articles.minecraftdsl.antlr.MinecraftLexer
import com.strumenta.articles.minecraftdsl.antlr.MinecraftParser
import com.strumenta.articles.minecraftdsl.antlr.MinecraftParser.BlockContext
import com.strumenta.articles.minecraftdsl.antlr.MinecraftParser.ModContext
import com.strumenta.kolasu.mapping.ParseTreeToASTTransformer
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Source
import com.strumenta.kolasu.parsing.ANTLRTokenFactory
import com.strumenta.kolasu.parsing.KolasuANTLRToken
import com.strumenta.kolasu.parsing.KolasuParser
import com.strumenta.kolasu.validation.Issue
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.Lexer
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.TokenStream

data class Mod(
    val id: String,
    val name: String,
    val version: String,
    val license: String? = null,
    val groupId: String? = null,
    val authors: List<String> = listOf(),
    val description: String = "The '$name' mod.",
    var blocks: List<Block> = listOf()
) : Node()

data class Block(val name: String) : Node()

class MinecraftModParser : KolasuParser<Mod, MinecraftParser, ModContext, KolasuANTLRToken>(ANTLRTokenFactory()) {
    override fun createANTLRLexer(charStream: CharStream): Lexer = MinecraftLexer(charStream)

    override fun createANTLRParser(tokenStream: TokenStream): MinecraftParser = MinecraftParser(tokenStream)

    override fun parseTreeToAst(
        parseTreeRoot: ModContext,
        considerPosition: Boolean,
        issues: MutableList<Issue>,
        source: Source?
    ): Mod? {
        return MinecraftParseTreeToASTTransformer(issues, source).transform(parseTreeRoot) as Mod?
    }
}

class MinecraftParseTreeToASTTransformer(
    issues: MutableList<Issue>,
    source: Source?
) : ParseTreeToASTTransformer(issues, false, source) {
    init {
        registerNodeFactory<ModContext, Mod> {
            if (exception == null) {
                val name = if (name.type == MinecraftLexer.NAME) name.text else stringContents(name)!!
                Mod(id.text, name, stringContents(version)!!, stringContents(license))
            } else {
                null
            }
        }.withChild(Mod::blocks, ModContext::block)
        registerNodeFactory<BlockContext, Block> {
            Block(name.text)
        }
    }

    private fun stringContents(token: Token?): String? = token?.text?.substring(1, token.text.length - 1)
}
