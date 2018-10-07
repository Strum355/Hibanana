package xyz.noahsc.hibanana.lexer

import xyz.noahsc.hibanana.token.Token
import xyz.noahsc.hibanana.token.TokenType

public class Lexer constructor(val input: String, var position: Int = 0, var readPosition: Int = 0, var char: Char = 0.toChar()) {
    init {
        readChar()
    }

    private fun readChar() {
        if (readPosition >= input.length) {
            char = 0.toChar()
        } else {
            char = input.get(readPosition)
        }
        position = readPosition
        readPosition++
    }

    public fun nextToken(): Token {
        lateinit var tok: Token
        when(char) {
            '=' -> tok = newToken(TokenType.ASSIGN, char)
            ';' -> tok = newToken(TokenType.SEMICOLON, char)
            '(' -> tok = newToken(TokenType.LPAREN, char)
            ')' -> tok = newToken(TokenType.RPAREN, char)
            ',' -> tok = newToken(TokenType.COMMA, char)
            '+' -> tok = newToken(TokenType.ADDITION, char)
            '{' -> tok = newToken(TokenType.LBRACE, char)
            '}' -> tok = newToken(TokenType.RBRACE, char)
            0.toChar() -> {
                tok = Token(TokenType.EOF, 0.toChar())
            }
        }
        readChar()
        return tok
    }

    private fun newToken(tokenType: TokenType, char: Char): Token = Token(tokenType, char)
}