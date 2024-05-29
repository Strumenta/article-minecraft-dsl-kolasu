package com.strumenta.articles.minecraftdsl.ast

import com.strumenta.articles.minecraftdsl.antlr.MinecraftLexer
import com.strumenta.articles.minecraftdsl.antlr.MinecraftParser
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
    val license: String
) : Node()

class MinecraftModParser : KolasuParser<Mod, MinecraftParser, ModContext, KolasuANTLRToken>(ANTLRTokenFactory()) {
    override fun createANTLRLexer(charStream: CharStream): Lexer = MinecraftLexer(charStream)

    override fun createANTLRParser(tokenStream: TokenStream): MinecraftParser = MinecraftParser(tokenStream)

    override fun parseTreeToAst(
        parseTreeRoot: ModContext,
        considerPosition: Boolean,
        issues: MutableList<Issue>,
        source: Source?
    ): Mod {
        return MinecraftParseTreeToASTTransformer(issues, source).transform(parseTreeRoot) as Mod
    }
}

class MinecraftParseTreeToASTTransformer(
    issues: MutableList<Issue>,
    source: Source?
) : ParseTreeToASTTransformer(issues, source = source) {
    init {
        registerNodeFactory<ModContext, Mod> {
            Mod(id.text, name.text, stringContents(version)!!, stringContents(license) ?: "")
        }
    }

    private fun stringContents(token: Token?): String? = token?.text?.substring(1, token.text.length - 1)
}
