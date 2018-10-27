package xyz.noahsc.hibanana.ast

import xyz.noahsc.hibanana.token.Token

public interface Statement: ASTNode

public data class VarStatement(val expression: Expression?, val name: Identifier, override val token: Token): Statement

public data class ReturnStatement(val expression: Expression?, override val token: Token): Statement