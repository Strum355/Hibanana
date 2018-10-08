package xyz.noahsc.hibanana.lexer

import xyz.noahsc.hibanana.token.*
import java.util.Arrays

public class Lexer constructor(val input: String, var position: Int = 0, var readPosition: Int = 0, var char: Char = 0.toChar()) {
    init {
        readChar()
    }

    private fun readChar() {
        if (readPosition >= input.length) char = 0.toChar()
        else char = input.get(readPosition)
        position = readPosition
        readPosition++
    }

    public fun nextToken(): Token {
        var tok: Token
        
        jumpWhitespace()

        when(char) {
            '=' -> tok = createDualToken(TokenType.ASSIGN, char)
            ';' -> tok = Token(TokenType.SEMICOLON, char.toString())
            ':' -> tok = Token(TokenType.COLON, char.toString())
            '!' -> tok = createDualToken(TokenType.BANG, char)
            '+' -> tok = createDualToken(TokenType.PLUS, char)
            '-' -> tok = createDualToken(TokenType.DASH, char)
            '/' -> tok = createDualToken(TokenType.FSLASH, char)
            '*' -> tok = createDualToken(TokenType.ASTERISK, char)
            '<' -> tok = createDualToken(TokenType.LT, char)
            '>' -> tok = createDualToken(TokenType.GT, char)
            '.' -> tok = Token(TokenType.PERIOD, char.toString())
            '(' -> tok = Token(TokenType.LPAREN, char.toString())
            ')' -> tok = Token(TokenType.RPAREN, char.toString())
            ',' -> tok = Token(TokenType.COMMA, char.toString())
            '{' -> tok = Token(TokenType.LBRACE, char.toString())
            '}' -> tok = Token(TokenType.RBRACE, char.toString())
            0.toChar() -> tok = Token(TokenType.EOF, 0.toChar().toString())
            else -> {
                if(Lexer.isIdentChar(char)) {
                    val ident = readIdentifier()
                    return Token(lookupIdent(ident), ident)
                } else if(isDigit(char)) {
                    return Token(TokenType.INT, readNumber())
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

    private fun createDualToken(firstToken: TokenType, firstChar: Char): Token {
        when(firstChar) {
            '=', '<', '>', '!', '+', '*', '-', '/' -> {
                if(peekChar() == '=') {
                    var text = char.toString()
                    readChar()
                    text += char.toString()
                    return Token(TokenType.get(text), text)
                }
            }
        }
        return Token(firstToken, firstChar.toString())
    }

    private fun jumpWhitespace() {
        while(char == ' ' || char == '\n' || char == '\r') readChar()
    }

    private fun peekChar(): Char = if(readPosition >= input.length) 0.toChar() else input.get(readPosition)

    companion object {
        public fun isIdentChar(char: Char): Boolean = 'a' <= char && char <= 'z' || 'A' <= char && char <= 'Z' || char == '_' || char == '-' 

        public fun isDigit(char: Char): Boolean = '0' <= char && char <= '9'
    }
}