package xyz.noahsc.hibanana.token

import xyz.noahsc.hibanana.token.TokenWithText.*
import kotlin.reflect.KClass

sealed interface Token

sealed interface TokenWithText: Token {
    val text: String

    sealed class KeywordToken(override val text: String): Token, TokenWithText

    companion object {
        private val keywords = mapOf(
            Function.text to Function,
            Var.text to Var,
            True.text to True,
            False.text to False,
            If.text to If,
            Else.text to Else,
            Return.text to Return,
            Mut.text to Mut
        )

        fun getKeywordOrIdent(ident: String): TokenWithText =
            keywords[ident] ?: Ident(ident)
    }
}

sealed interface Operator: Token {
    val textual: String
}

sealed class SingularOperator(override val textual: String): Operator {
    companion object {
        fun getOrIllegal(char: Char): Token = SingularOperator::class.sealedSubclasses.find {
            it.objectInstance?.textual == char.toString()
        }?.objectInstance ?: Illegal(char.toString())
    }
}

sealed class CompoundOperator(
    private val first: SingularOperator,
    private val second: SingularOperator,
): Operator, Token {
    override val textual get() = "${first.textual}${second.textual}"

    companion object {
        fun getOrIllegal(lexemes: String): Token = CompoundOperator::class.get(lexemes) ?: Illegal(lexemes)

        private fun KClass<out CompoundOperator>.get(lexemes: String): Token? {
            return this.sealedSubclasses.find {
                it.objectInstance?.textual == lexemes
            }?.objectInstance
        }
    }
}

sealed class CompoundAssignmentOperator(op: SingularOperator): Token, CompoundOperator(op, Assign)

data class Illegal(override val text: String): TokenWithText
data class Ident(override val text: String): TokenWithText
data class Number(override val text: String): TokenWithText {
    val value: Int get() = this.text.toInt()
}
object Function: KeywordToken("func")
object Var: KeywordToken("var")
object Mut: KeywordToken("mut")
object True: KeywordToken("true")
object False: KeywordToken("false")
object If: KeywordToken("if")
object Else: KeywordToken("else")
object Return: KeywordToken("return")

object Equal: CompoundAssignmentOperator(Assign)
object GtEqual: CompoundAssignmentOperator(Gt)
object LtEqual: CompoundAssignmentOperator(Lt)
object NotEqual: CompoundAssignmentOperator(Bang)
object PlusEqual: CompoundAssignmentOperator(Plus)
object AsteriskEqual: CompoundAssignmentOperator(Asterisk)
object DashEqual: CompoundAssignmentOperator(Dash)
object ForwardSlashEqual: CompoundAssignmentOperator(ForwardSlash)

object Assign: SingularOperator("=")
object Plus: SingularOperator("+")
object Dash: SingularOperator("-")
object Asterisk: SingularOperator("*")
object ForwardSlash: SingularOperator("/")
object BackSlash: SingularOperator("\\")
object Gt: SingularOperator(">")
object Lt: SingularOperator("<")
object Bang: SingularOperator("!")

object EOF: Token
object Question: Token
object Comma: Token
object Dot: Token
object Semicolon: Token
object Colon: Token
object LeftParen: Token
object RightParen: Token
object LeftBrace: Token
object RightBrace: Token
object Newline: Token

