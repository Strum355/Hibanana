package xyz.noahsc.hibanana.parser

import xyz.noahsc.hibanana.ast.*
import xyz.noahsc.hibanana.token.*
import xyz.noahsc.hibanana.lexer.*

public class Parser(private val lexer: Lexer) {
    // Must be set to lateinit :(
    private lateinit var currToken: Token
    private var peekToken: Token

    init {
        peekToken = lexer.nextToken()
        nextToken()
    }


    private fun nextToken() {
        currToken = peekToken
        peekToken = lexer.nextToken()
    }

    public fun parse(): Program? {
        return null
    }
}