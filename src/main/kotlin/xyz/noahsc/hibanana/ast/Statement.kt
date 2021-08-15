package xyz.noahsc.hibanana.ast

import xyz.noahsc.hibanana.token.Token

interface Statement: ASTNode

data class VarStatement(val expression: Expression?, val name: Identifier, override val token: Token): Statement

data class ReturnStatement(val expression: Expression?, override val token: Token): Statement