package xyz.noahsc.hibanana.repl

import java.io.BufferedReader
import java.io.Reader
import xyz.noahsc.hibanana.lexer.Lexer
import xyz.noahsc.hibanana.token.*
import xyz.noahsc.hibanana.parser.Parser

const val PROMPT = ">>"

fun startRepl() {
    while(true) {
        print(PROMPT)
        val line = readLine()
        if(line == null) return
        val parser = Parser(Lexer(line))
        val program = parser.parse()
        println(parser.errors)
        println(program?.statements)
    }
}