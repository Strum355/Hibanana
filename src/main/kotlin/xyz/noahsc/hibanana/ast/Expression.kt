package xyz.noahsc.hibanana.ast

import xyz.noahsc.hibanana.token.Token

public interface Expression: Node

public data class Identifier(val value: String, override val token: Token): Expression 