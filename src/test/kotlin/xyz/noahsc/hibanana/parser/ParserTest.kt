package xyz.noahsc.hibanana.parser

import org.junit.Test
import org.junit.Assert.*
import xyz.noahsc.hibanana.token.Token
import xyz.noahsc.hibanana.token.TokenType
import xyz.noahsc.hibanana.lexer.Lexer
import xyz.noahsc.hibanana.ast.*
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
        assertNotNull("program must not be null", program)

        if(program != null) {
            assertEquals("program must contain 2 statements", program.statements.size, 2)

            val tests = arrayOf("x", "y")
            program.statements.withIndex().forEach { 
                checkVar(it.value, tests[it.index])
            }
        }
    }

    fun checkVar(statement: Statement, name: String) {
        assertEquals("token literally must be var", "var", statement.tokenLiteral())
        assertTrue("statement must be a var statement", statement is VarStatement)
        assertEquals("ident value must be $name", name, (statement as VarStatement).name.value)
        assertEquals(name, statement.name.tokenLiteral())
    }
}