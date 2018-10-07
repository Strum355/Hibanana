package xyz.noahsc.hibanana.token

import org.junit.Test
import org.junit.Assert.assertEquals
import xyz.noahsc.hibanana.token.Token
import xyz.noahsc.hibanana.token.TokenType
import xyz.noahsc.hibanana.lexer.Lexer

class TokenTest {
    @Test
    fun testNextToken() {
        val input = "=+(){},;"
        
        val tests = arrayOf(
            TokenTest.ExpectedToken(TokenType.ASSIGN, '='),
            TokenTest.ExpectedToken(TokenType.ADDITION, '+'),
            TokenTest.ExpectedToken(TokenType.LPAREN, '('),
            TokenTest.ExpectedToken(TokenType.RPAREN, ')'),
            TokenTest.ExpectedToken(TokenType.LBRACE, '{'),
            TokenTest.ExpectedToken(TokenType.RBRACE, '}'),
            TokenTest.ExpectedToken(TokenType.COMMA, ','),
            TokenTest.ExpectedToken(TokenType.SEMICOLON, ';')
        )

        println(tests[0].expectedLiteral)

        val lexed = Lexer(input)
        
        tests.forEachIndexed { _, it ->
            val token = lexed.nextToken()

            assertEquals(token.type, it.expectedType)
            assertEquals(token.literal, it.expectedLiteral)
        }
    }

    data class ExpectedToken(public val expectedType:  TokenType, public val expectedLiteral: Char)
}