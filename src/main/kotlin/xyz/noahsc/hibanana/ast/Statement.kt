package xyz.noahsc.hibanana.ast

import xyz.noahsc.hibanana.token.Token

public interface Statement: Node

public data class VarStatement(val expression: Expression?, val name: Identifier, override val token: Token): Statement