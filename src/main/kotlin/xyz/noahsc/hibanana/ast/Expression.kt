package xyz.noahsc.hibanana.ast

import xyz.noahsc.hibanana.token.Token

interface Expression: ASTNode

data class Identifier(val value: String, override val token: Token): Expression