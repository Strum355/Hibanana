package xyz.noahsc.hibanana.parser

import xyz.noahsc.hibanana.ast.*
import xyz.noahsc.hibanana.token.*
import xyz.noahsc.hibanana.lexer.*

public class Parser(private val lexer: Lexer) {
    // Must be set to lateinit :(
    private lateinit var currToken: Token
    private var peekToken: Token
    private val errors: ArrayList<String> = arrayListOf()

    init {
        peekToken = lexer.nextToken()
        nextToken()
    }


    private fun nextToken() {
        currToken = peekToken
        peekToken = lexer.nextToken()
    }

    public fun parse(): Program? {
        val program = Program()
        while(currToken.type != TokenType.EOF) {
            val statement: Statement? = parseStatement()
            if(statement != null) {
                program.statements.add(statement)
            }
            nextToken()
        }
        return program
    }

    public fun getErrors() = errors.clone() as ArrayList<String>

    private fun currTokenIs(t: TokenType) = currToken.type == t

    private fun peekTokenIs(t: TokenType) = peekToken.type == t

    private fun expectPeek(t: TokenType): Boolean {
        if(peekTokenIs(t)) {
            nextToken()
            return true
        }
        peekError(t)
        return false
    }

    private fun peekError(t: TokenType) {
        errors.add("expected ${t.name}, got ${peekToken.type.name} instead")
    }

    private fun parseStatement() = when(currToken.type) {
        TokenType.VAR -> parseVar()
        else -> null
    }

    private fun parseVar(): VarStatement? {
        val firstToken = currToken

        if(!expectPeek(TokenType.IDENT)) return null
        
        val ident = Identifier(currToken.text, currToken)

        if(!expectPeek(TokenType.COLON) && !expectPeek(TokenType.IDENT) && !expectPeek(TokenType.ASSIGN)) return null

        skipToEnd()

        return VarStatement(null, ident, firstToken)
    }

    private fun skipToEnd() {
        while(!currTokenIs(TokenType.NEWLINE) && !currTokenIs(TokenType.EOF) && !currTokenIs(TokenType.SEMICOLON)) {
            nextToken()
        }
    }
}