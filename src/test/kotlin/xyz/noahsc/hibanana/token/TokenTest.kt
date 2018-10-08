package xyz.noahsc.hibanana.token

import org.junit.Test
import org.junit.Assert.assertEquals
import xyz.noahsc.hibanana.token.Token
import xyz.noahsc.hibanana.token.TokenType
import xyz.noahsc.hibanana.lexer.Lexer
import java.util.logging.Logger

class TokenTest {
    @Test
    fun testNextToken() {
        val input = """
        var x: int = 5
        
        func trolled(arg: int, otherArg: float): float {
            return 5 * 8 / otherArg + arg
        }

        if 55 > 4 {
            return false
        } else if 55 != 4 {
            return true
        } else {
            return 1
        }
        """.trimIndent()
        
        val tests = arrayOf(
            TokenTest.ExpectedToken(TokenType.VAR, "var"),
            TokenTest.ExpectedToken(TokenType.IDENT, "x"),
            TokenTest.ExpectedToken(TokenType.COLON, ":"),
            TokenTest.ExpectedToken(TokenType.IDENT, "int"),
            TokenTest.ExpectedToken(TokenType.ASSIGN, "="),
            TokenTest.ExpectedToken(TokenType.INT, "5"),
            TokenTest.ExpectedToken(TokenType.FUNCTION, "func"),
            TokenTest.ExpectedToken(TokenType.IDENT, "trolled"),
            TokenTest.ExpectedToken(TokenType.LPAREN, "("),
            TokenTest.ExpectedToken(TokenType.IDENT, "arg"),
            TokenTest.ExpectedToken(TokenType.COLON, ":"),
            TokenTest.ExpectedToken(TokenType.IDENT, "int"),
            TokenTest.ExpectedToken(TokenType.COMMA, ","),
            TokenTest.ExpectedToken(TokenType.IDENT, "otherArg"),
            TokenTest.ExpectedToken(TokenType.COLON, ":"),
            TokenTest.ExpectedToken(TokenType.IDENT, "float"),
            TokenTest.ExpectedToken(TokenType.RPAREN, ")"),
            TokenTest.ExpectedToken(TokenType.COLON, ":"),
            TokenTest.ExpectedToken(TokenType.IDENT, "float"),
            TokenTest.ExpectedToken(TokenType.LBRACE, "{"),
            TokenTest.ExpectedToken(TokenType.RETURN, "return"),
            TokenTest.ExpectedToken(TokenType.INT, "5"),
            TokenTest.ExpectedToken(TokenType.ASTERISK, "*"),
            TokenTest.ExpectedToken(TokenType.INT, "8"),
            TokenTest.ExpectedToken(TokenType.FSLASH, "/"),
            TokenTest.ExpectedToken(TokenType.IDENT, "otherArg"),
            TokenTest.ExpectedToken(TokenType.PLUS, "+"),
            TokenTest.ExpectedToken(TokenType.IDENT, "arg"),
            TokenTest.ExpectedToken(TokenType.RBRACE, "}"),
            TokenTest.ExpectedToken(TokenType.IF, "if"),
            TokenTest.ExpectedToken(TokenType.INT, "55"),
            TokenTest.ExpectedToken(TokenType.GT, ">"),
            TokenTest.ExpectedToken(TokenType.INT, "4"),
            TokenTest.ExpectedToken(TokenType.LBRACE, "{"),
            TokenTest.ExpectedToken(TokenType.RETURN, "return"),
            TokenTest.ExpectedToken(TokenType.FALSE, "false"),
            TokenTest.ExpectedToken(TokenType.RBRACE, "}"),
            TokenTest.ExpectedToken(TokenType.ELSE, "else"),
            TokenTest.ExpectedToken(TokenType.IF, "if"),
            TokenTest.ExpectedToken(TokenType.INT, "55"),
            TokenTest.ExpectedToken(TokenType.NOT_EQUAL, "!="),
            TokenTest.ExpectedToken(TokenType.INT, "4"),
            TokenTest.ExpectedToken(TokenType.LBRACE, "{"),
            TokenTest.ExpectedToken(TokenType.RETURN, "return"),
            TokenTest.ExpectedToken(TokenType.TRUE, "true"),
            TokenTest.ExpectedToken(TokenType.RBRACE, "}"),
            TokenTest.ExpectedToken(TokenType.ELSE, "else"),
            TokenTest.ExpectedToken(TokenType.LBRACE, "{"),
            TokenTest.ExpectedToken(TokenType.RETURN, "return"),
            TokenTest.ExpectedToken(TokenType.INT, "1"),
            TokenTest.ExpectedToken(TokenType.RBRACE, "}"),
            TokenTest.ExpectedToken(TokenType.EOF, 0.toChar().toString())
        )

        val lexed = Lexer(input)
        
        tests.forEachIndexed { _, it ->
            val token = lexed.nextToken()
            println("${token.type.toString()} ${token.text} ${it.expectedType} ${it.expectedString}")
            assertEquals(token.type, it.expectedType)
            assertEquals(token.text, it.expectedString)
        }
    }

    data class ExpectedToken(public val expectedType:  TokenType, public val expectedString: String)
}