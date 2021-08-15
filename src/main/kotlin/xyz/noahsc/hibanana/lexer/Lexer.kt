package xyz.noahsc.hibanana.lexer

import xyz.noahsc.hibanana.token.*

class Lexer(private val input: String) {
    private var position = 0
    private var readPosition = 0
    private var char: Char = Char.MIN_VALUE

    init {
        readChar()
    }

    private fun readChar() {
        char = if (readPosition >= input.length) 0.toChar()
        else input[readPosition]
        position = readPosition
        readPosition++
    }

    fun nextToken(): Token {
        val tok: Token

        jumpWhitespace()

        when(char) {
            '\n' -> tok = Newline
            ';' -> tok = Semicolon
            ':' -> tok = Colon
            '=', '!', '+', '-', '/', '*', '<', '>' -> tok = compoundOrSingleOperator()
            '.' -> tok = Dot
            '(' -> tok = LeftParen
            ')' -> tok = RightParen
            ',' -> tok = Comma
            '{' -> tok = LeftBrace
            '}' -> tok = RightBrace
            Char.MIN_VALUE -> tok = EOF
            else -> {
                if(isIdentChar(char)) {
                    val ident = readIdentifier()
                    return TokenWithText.getKeywordOrIdent(ident)
                } else if(isDigit(char)) {
                    return Number(readNumber())
                } else {
                    tok = Illegal(char.toString())
                }
            }
        }
        readChar()
        return tok
    }

    private fun readIdentifier(): String {
        val position = this.position
        while(isIdentChar(char)) readChar()
        return input.slice(position until this.position)
    }

    private fun readNumber(): String {
        val position = this.position
        while(isDigit(char)) readChar()
        return input.slice(position until this.position)
    }

    private fun compoundOrSingleOperator(): Token {
        if(peekChar() == '=') {
            var lexemes = char.toString()
            readChar()
            lexemes += char.toString()
            return CompoundOperator.getOrIllegal(lexemes)
        }
        return SingularOperator.getOrIllegal(char)
    }

    private fun jumpWhitespace() {
        while(char == ' ' || char == '\r' || char == '\t') readChar()
    }

    private fun peekChar(): Char = if(readPosition >= input.length) 0.toChar() else input[readPosition]

    companion object {
        fun isIdentChar(char: Char): Boolean = char in 'a'..'z' || char in 'A'..'Z' || char == '_'

        fun isDigit(char: Char): Boolean = char in '0'..'9'
    }
}