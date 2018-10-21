package xyz.noahsc.hibanana.token

import xyz.noahsc.hibanana.ast.*

val keywords = mapOf(
    "func" to TokenType.FUNCTION,
    "var" to TokenType.VAR,
    "true" to TokenType.TRUE,
    "false" to TokenType.FALSE,
    "if" to TokenType.IF,
    "else" to TokenType.ELSE,
    "return" to TokenType.RETURN
)

fun lookupIdent(ident: String): TokenType {
    return keywords.getOrElse(ident, { TokenType.IDENT })
}

public data class Token(val type: TokenType, val text: String)

public enum class TokenType(val type: String) {
    ILLEGAL("ILLEGAL"),
    EOF("EOF"),
    IDENT("IDENT"),
    INT("INT"),
    FLOAT("FLOAT"),
    STRING("STRING"),
    FUNCTION("FUNCTION"),
    VAR("VAR"),
    MUT("MUT"),
    TRUE("TRUE"),
    FALSE("FALSE"),
    IF("IF"),
    ELSE("ELSE"),
    RETURN("RETURN"),
    ASSIGN("="),
    EQUAL("=="),
    GT_EQUAL(">="),
    LT_EQUAL("<="),
    NOT_EQUAL("!="),
    PLUS_EQUAL("+="),
    ASTERISK_EQUAL("*="),
    DASH_EQUAL("-="),
    FLASH_EQUAL("/="),
    PLUS("+"),
    DASH("-"),
    ASTERISK("*"),
    FSLASH("/"),
    BSLASH("\\"),
    GT(">"),
    LT("<"),
    BANG("!"),
    QUESTION("?"),
    COMMA(","),
    PERIOD("."),
    SEMICOLON(";"),
    COLON(":"),
    LPAREN(")"),
    RPAREN("("),
    LBRACE("}"),
    RBRACE("{"),
    NEWLINE("\n");

    companion object {
        private val tokens: HashMap<String, TokenType> = HashMap()

        init {
            for(token in TokenType.values()) {
                tokens.put(token.type, token)
            }
        }

        public fun get(type: String): TokenType = this.tokens.getOrDefault(type, TokenType.ILLEGAL)
    }
}
