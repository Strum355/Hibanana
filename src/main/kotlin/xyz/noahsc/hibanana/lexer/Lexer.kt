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

        jumpWhitespace()

        when(char) {
            '=' -> tok = Token(TokenType.ASSIGN, char.toString())
            ';' -> tok = Token(TokenType.SEMICOLON, char.toString())
            ':' -> tok = Token(TokenType.COLON, char.toString())
            '(' -> tok = Token(TokenType.LPAREN, char.toString())
            ')' -> tok = Token(TokenType.RPAREN, char.toString())
            ',' -> tok = Token(TokenType.COMMA, char.toString())
            '+' -> tok = Token(TokenType.ADDITION, char.toString())
            '{' -> tok = Token(TokenType.LBRACE, char.toString())
            '}' -> tok = Token(TokenType.RBRACE, char.toString())
            0.toChar() -> tok = Token(TokenType.EOF, 0.toString())
            else -> {
                if(Lexer.isIdentChar(char)) {
                    val ident = readIdentifier()
                    when(ident) {
                        "var" -> return Token(TokenType.VAR, ident)
                        "func" -> return Token(TokenType.FUNCTION, ident)
                        "return" -> return Token(TokenType.RETURN, ident)
                    }
                    return Token(TokenType.IDENT, ident)
                } else if(isDigit(char)) {
                    val num = readNumber()
                    return Token(TokenType.INT, num)
                } else {
                    tok = Token(TokenType.ILLEGAL, char.toString())
                }
            }
        }
        readChar()
        return tok
    }

    private fun readIdentifier(): String {
        val position = this.position
        while(Lexer.isIdentChar(char)) readChar()
        return input.slice(position..this.position-1)
    }

    private fun readNumber(): String {
        val position = this.position
        while(Lexer.isDigit(char)) readChar()
        return input.slice(position..this.position-1)
    }

    private fun jumpWhitespace() {
        while(char == ' ' || char == '\n' || char == '\r') readChar()
    }

    companion object {
        public fun isIdentChar(char: Char): Boolean = 'a' <= char && char <= 'z' || 'A' <= char && char <= 'Z' || char == '_' || char == '-' 

        public fun isDigit(char: Char): Boolean = '0' <= char && char <= '9'
    }
}