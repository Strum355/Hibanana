package xyz.noahsc.hibanana.token

public enum class TokenType(type: String) {
    ILLEGAL("ILLEGAL"),
    EOF("EOF"),
    IDENT("IDENT"),
    INT("INT"),
    ASSIGN("="),
    ADDITION("+"),
    COMMA(","),
    SEMICOLON(";"),
    LPAREN(")"),
    RPAREN("("),
    LBRACE("}"),
    RBRACE("{"),
    FUNCTION("FUNCTION"),
    VAR("VAR")
}

public data class Token(val type: TokenType, val literal: Char)