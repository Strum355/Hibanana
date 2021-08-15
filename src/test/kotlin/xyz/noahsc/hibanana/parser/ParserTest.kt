package xyz.noahsc.hibanana.parser

import io.kotest.assertions.withClue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test;
import xyz.noahsc.hibanana.lexer.Lexer
import xyz.noahsc.hibanana.ast.*

class ParserTest {
    @Test
    fun testVarStatement() {
        val input = """
            var x: int = 5
            var y: int = 10
        """.trimIndent()

        val parser = Parser(Lexer(input))
        val program = parser.parse()

        parser.errors.shouldBeEmpty()
        program.statements.shouldHaveSize(2)

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
        parser.errors.shouldHaveSize(1)
    }

    private fun checkVar(statement: Statement, name: String) {
        statement.shouldBeInstanceOf<VarStatement>()
        name shouldBe statement.name.value
        name shouldBe statement.name.value
    }

    @Test
    fun testReturnStatement() {
        val input = """
            return 5
            return 123
            return value
        """.trimIndent()

        val program = Parser(Lexer(input)).parse()
        program.statements.size shouldBeExactly 3

        program.statements.forEach {
            withClue("each statement must be a return statement") {
                it.shouldBeInstanceOf<ReturnStatement>()
            }
        }
    }
}