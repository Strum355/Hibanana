package xyz.noahsc.hibanana.repl

import java.io.BufferedReader
import java.io.Reader
import xyz.noahsc.hibanana.lexer.Lexer
import xyz.noahsc.hibanana.token.*

const val PROMPT = ">>"

fun startRepl() {
    while(true) {
        print(PROMPT)
        val line = readLine()
        if(line == null) return
        val lexer = Lexer(line)
        var tok: Token = lexer.nextToken()
        while(tok.type != TokenType.EOF) {
            println(tok)
            tok = lexer.nextToken()
        }
    }
}