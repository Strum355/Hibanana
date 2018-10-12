package xyz.noahsc.hibanana.ast

import java.util.LinkedList

public class Program {
    public var statements: LinkedList<Statement>

    init {
        statements = LinkedList()
    }

    public fun tokenLiteral(): String = if(statements.size > 0) statements[0].tokenLiteral() else ""
}