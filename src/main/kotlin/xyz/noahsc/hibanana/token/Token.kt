package xyz.noahsc.hibanana.token

public enum class TokenType(type: String) {
    ILLEGAL("ILLEGAL"),
    EOF("EOF"),
    IDENT("IDENT"),
    INT("INT"),
    FLOAT("FLOAT"),
    STRING("STRING"),
    ASSIGN("="),
    ADDITION("+"),
    COMMA(","),
    SEMICOLON(";"),
    COLON(":"),
    LPAREN(")"),
    RPAREN("("),
    LBRACE("}"),
    RBRACE("{"),
    FUNCTION("FUNCTION"),
    VAR("VAR"),
    RETURN("RETURN")
}

public data class Token(val type: TokenType, val text: String)