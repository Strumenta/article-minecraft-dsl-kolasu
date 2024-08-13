package com.strumenta.articles.minecraftdsl

import com.strumenta.articles.minecraftdsl.ast.MinecraftModParser
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ParserTest {

    @Test
    fun testMalformedMod() {
        val result = MinecraftModParser().parse("""mod 123""")
        assertEquals(5, result.issues.size)
    }

    @Test
    fun testEmptyMod() {
        var result = MinecraftModParser().parse("""mod foo (com.strumenta.foo@"1.2.3") {}""")
        assertEquals(listOf(), result.issues)
        var mod = result.root
        assertNotNull(mod)
        assertEquals("foo", mod.name)
        assertEquals("com.strumenta.foo", mod.id)
        assertEquals("1.2.3", mod.version)
        assertEquals(null, mod.license)

        result = MinecraftModParser().parse("""mod "my foo mod" (com.strumenta.foo@"1.2.3") {}""")
        assertEquals(listOf(), result.issues)
        mod = result.root
        assertNotNull(mod)
        assertEquals("my foo mod", mod.name)
        assertEquals("com.strumenta.foo", mod.id)
        assertEquals("1.2.3", mod.version)
        assertEquals(null, mod.license)
    }

    @Test
    fun testModWithBlock() {
        val result = MinecraftModParser().parse(
            """mod foo (com.strumenta.foo@"1.2.3") {
            |   block blbl1 {}
            |   block blbl2 {}
            |}""".trimMargin())
        assertEquals(listOf(), result.issues)
        val mod = result.root
        assertNotNull(mod)
        assertEquals(listOf("blbl1", "blbl2"), mod.blocks.map { it.name })
    }
}
