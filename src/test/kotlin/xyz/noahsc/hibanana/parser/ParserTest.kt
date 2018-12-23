package xyz.noahsc.hibanana.parser

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
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

        assertNotNull(program)
        program!! // asserted null, no need for safe checks anymore, but compiler doesnt know that

        assertEquals(2, program.statements.size)

        val tests = arrayOf("x", "y")
        program.statements.withIndex().forEach { 
            checkVar(it.value, tests[it.index])
        }
    }

    @Test
    fun testVarStatementFail() {
        val input = """
        var x: int = 5
        var y int = 10
        """.trimIndent()

        val parser = Parser(Lexer(input))
        parser.parse()
        assertEquals(1, parser.errors.size)
    }

    fun checkVar(statement: Statement, name: String) {
        assertEquals("var", statement.tokenLiteral())
        assertTrue(statement is VarStatement)
        assertEquals(name, (statement as VarStatement).name.value)
        assertEquals(name, statement.name.tokenLiteral())
    }

    @Test
    fun testReturnStatement() {
        val input = """
        return 5
        return 123
        return value
        """.trimIndent()

        val parser = Parser(Lexer(input))
        val program = parser.parse()!!
        assertEquals(3, program.statements.size)

        program.statements.forEach {
            assert(it is ReturnStatement, { "each statement must be a return statement" })
        }
    }
}