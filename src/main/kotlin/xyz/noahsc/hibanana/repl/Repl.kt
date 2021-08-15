package xyz.noahsc.hibanana.repl

import xyz.noahsc.hibanana.lexer.Lexer
import xyz.noahsc.hibanana.parser.Parser

const val PROMPT = ">>"

fun startRepl() {
    while(true) {
        print(PROMPT)
        val line = readLine() ?: return
        val parser = Parser(Lexer(line))
        val program = parser.parse()
        println(parser.errors)
        println(program.statements)
    }
}