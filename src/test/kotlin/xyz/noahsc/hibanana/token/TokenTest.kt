package xyz.noahsc.hibanana.token

import org.junit.Test
import org.junit.Assert.assertEquals
import xyz.noahsc.hibanana.token.Token
import xyz.noahsc.hibanana.token.TokenType
import xyz.noahsc.hibanana.lexer.Lexer

class TokenTest {
    @Test
    fun testNextToken() {
        val input = """
        var x: int = 5
        
        func trolled(arg: string, arg1: float): string {
            return "ok this is epic"
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
            TokenTest.ExpectedToken(TokenType.IDENT, "string"),
            TokenTest.ExpectedToken(TokenType.COMMA, ","),
            TokenTest.ExpectedToken(TokenType.IDENT, "arg1"),
            TokenTest.ExpectedToken(TokenType.COMMA, ":"),
            TokenTest.ExpectedToken(TokenType.IDENT, "float"),
            TokenTest.ExpectedToken(TokenType.COLON, ":"),
            TokenTest.ExpectedToken(TokenType.IDENT, "string"),
            TokenTest.ExpectedToken(TokenType.LBRACE, "{"),
            TokenTest.ExpectedToken(TokenType.RETURN, "return"),
            TokenTest.ExpectedToken(TokenType.STRING, "\"ok this is epic\""),
            TokenTest.ExpectedToken(TokenType.RBRACE, "}")
        )

        val lexed = Lexer(input)
        
        tests.forEachIndexed { _, it ->
            val token = lexed.nextToken()
            assertEquals(token.type, it.expectedType)
            assertEquals(token.text, it.expectedString)
        }
    }

    data class ExpectedToken(public val expectedType:  TokenType, public val expectedString: String)
}