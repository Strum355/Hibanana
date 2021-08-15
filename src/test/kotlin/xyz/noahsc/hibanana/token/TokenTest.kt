package xyz.noahsc.hibanana.token

import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test;
import xyz.noahsc.hibanana.lexer.Lexer

class TokenTest {
    @Test
    fun testNextToken() {
        val input = """
            var x: int = 5
    
            mut var y: int = 10
            
            func trolled(arg: int, otherArg: float): float {
                return 5 * 8 / otherArg + arg
            }
    
            if 55 > 4 {
                return false
            } else if 55 != 4.5 {
                return true
            } else {
                return 1
            }
        """.trimIndent()

        val lexer = Lexer(input)
        
        val tests = arrayOf(
            lexer.expect<Var>(),
            lexer.expect<Ident>("x"),
            lexer.expect<Colon>(),
            lexer.expect<Ident>("int"),
            lexer.expect<Assign>(),
            lexer.expect<Number>("5"),
            lexer.expect<Newline>(),
            lexer.expect<Newline>(),
            lexer.expect<Mut>(),
            lexer.expect<Var>(),
            lexer.expect<Ident>("y"),
            lexer.expect<Colon>(),
            lexer.expect<Ident>("int"),
            lexer.expect<Assign>(),
            lexer.expect<Number>("10"),
            lexer.expect<Newline>(),
            lexer.expect<Newline>(),
            lexer.expect<Function>(),
            lexer.expect<Ident>("trolled"),
            lexer.expect<LeftParen>(),
            lexer.expect<Ident>("arg"),
            lexer.expect<Colon>(),
            lexer.expect<Ident>("int"),
            lexer.expect<Comma>(),
            lexer.expect<Ident>("otherArg"),
            lexer.expect<Colon>(),
            lexer.expect<Ident>("float"),
            lexer.expect<RightParen>(),
            lexer.expect<Colon>(),
            lexer.expect<Ident>("float"),
            lexer.expect<LeftBrace>(),
            lexer.expect<Newline>(),
            lexer.expect<Return>(),
            lexer.expect<Number>("5"),
            lexer.expect<Asterisk>(),
            lexer.expect<Number>("8"),
            lexer.expect<ForwardSlash>(),
            lexer.expect<Ident>("otherArg"),
            lexer.expect<Plus>(),
            lexer.expect<Ident>("arg"),
            lexer.expect<Newline>(),
            lexer.expect<RightBrace>(),
            lexer.expect<Newline>(),
            lexer.expect<Newline>(),
            lexer.expect<If>(),
            lexer.expect<Number>("55"),
            lexer.expect<Gt>(),
            lexer.expect<Number>("4"),
            lexer.expect<LeftBrace>(),
            lexer.expect<Newline>(),
            lexer.expect<Return>(),
            lexer.expect<False>(),
            lexer.expect<Newline>(),
            lexer.expect<RightBrace>(),
            lexer.expect<Else>(),
            lexer.expect<If>(),
            lexer.expect<Number>("55"),
            lexer.expect<NotEqual>(),
            lexer.expect<Number>("4"),
            lexer.expect<Dot>(),
            lexer.expect<Number>("5"),
            lexer.expect<LeftBrace>(),
            lexer.expect<Newline>(),
            lexer.expect<Return>(),
            lexer.expect<True>(),
            lexer.expect<Newline>(),
            lexer.expect<RightBrace>(),
            lexer.expect<Else>(),
            lexer.expect<LeftBrace>(),
            lexer.expect<Newline>(),
            lexer.expect<Return>(),
            lexer.expect<Number>("1"),
            lexer.expect<Newline>(),
            lexer.expect<RightBrace>(),
            lexer.expect<EOF>()
        )

        tests.forEachIndexed { i, it -> it(i) }
    }

    private inline fun <reified T: Token> Lexer.expect(): (Int) -> Unit {
        return {
            withClue("token number $it") {
                val token = this.nextToken()
                withClue("token value $token") {
                    token.shouldBeInstanceOf<T>()
                }
            }
        }
    }

    private inline fun <reified T: TokenWithText> Lexer.expect(expected: String): (Int) -> Unit {
        return {
            withClue("token number $it") {
                val token = this.nextToken()
                withClue("token value $token") {
                    token.shouldBeInstanceOf<T>()
                    token.text shouldBe expected
                }
            }
        }
    }
}