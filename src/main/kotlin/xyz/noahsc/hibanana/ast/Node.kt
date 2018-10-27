package xyz.noahsc.hibanana.ast

import xyz.noahsc.hibanana.token.Token

public interface ASTNode {
    public val token: Token
    public fun tokenLiteral(): String = token.text
}