package xyz.noahsc.hibanana.parser

import xyz.noahsc.hibanana.ast.*
import xyz.noahsc.hibanana.token.*
import xyz.noahsc.hibanana.lexer.*
import xyz.noahsc.hibanana.token.Token.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class Parser(private val lexer: Lexer) {
    private lateinit var currToken: Token
    private var peekToken: Token
    val errors: ArrayList<ParserError> = arrayListOf()

    init {
        peekToken = lexer.nextToken()
        nextToken()
    }

    private fun nextToken(): Token {
        currToken = peekToken
        peekToken = lexer.nextToken()
        return currToken
    }

    fun parse(): Program {
        val program = Program()
        while(currToken !is EOF) {
            val statement: Statement? = parseStatement()
            if(statement != null) {
                program.statements.add(statement)
            }
            nextToken()
        }
        return program
    }

    @OptIn(ExperimentalContracts::class)
    private fun Token?.not(): Boolean {
        contract { returns(false) implies (this@not != null) }
        return this == null
    }

    @OptIn(ExperimentalContracts::class)
    private inline fun <reified T: Token> Token.expectPeek(): T? {
        contract { returnsNotNull() implies (this@expectPeek is T) }
        if(peekToken is T) {
            nextToken()
            return this as T
        }
        peekError<T>()
        return null
    }

    private inline fun <reified T: Token> peekError() {
        // TODO actual numbers
        errors.add(ParserError(1, 1, 1, "expected ${T::class}, got $peekToken instead"))
    }

    private fun parseStatement() = when(currToken) {
        is Var -> parseVar()
        is Return ->  parseReturn()
        else -> null
    }

    private fun parseVar(): VarStatement? {
        val firstToken = currToken

        val nextToken = peekToken.expectPeek<Ident>() ?: return null

        val ident = Identifier(nextToken.text, nextToken)

        if(peekToken.expectPeek<Colon>().not() && peekToken.expectPeek<Ident>().not() && peekToken.expectPeek<Assign>().not())
            return null

        skipToEnd()

        return VarStatement(null, ident, firstToken)
    }

    private fun parseReturn(): ReturnStatement {
        val firstToken = currToken

        nextToken()
        skipToEnd()

        return ReturnStatement(null, firstToken)
    }

    private fun skipToEnd() {
        while(currToken !is Newline && currToken !is EOF && currToken !is Semicolon) {
            nextToken()
        }
    }
}