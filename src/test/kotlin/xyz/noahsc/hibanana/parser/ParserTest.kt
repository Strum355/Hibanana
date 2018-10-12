package xyz.noahsc.hibanana.parser

import org.junit.Test
import org.junit.Assert.*
import xyz.noahsc.hibanana.token.Token
import xyz.noahsc.hibanana.token.TokenType
import xyz.noahsc.hibanana.lexer.Lexer
import java.util.logging.Logger

class ParserTest {
    @Test
    fun testVarStatement() {
        val input = """
        var x: int = 5
        var y: int = 10
        """.trimIndent()

        val parser = Parser(Lexer(input))

        val program = parser.parse()
        assertNotNull(program)

        if(program != null) {
            assertEquals(program.statements.size, 2)

            val tests = arrayOf("x", "y")
            /* program.statements.forEachIndexed {

            } */
        }
    }
}