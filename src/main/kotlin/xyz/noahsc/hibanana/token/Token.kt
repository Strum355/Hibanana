package xyz.noahsc.hibanana.token

public enum class TokenType(type: String) {
    ILLEGAL("ILLEGAL"),
    EOF("EOF"),
    IDENT("IDENT"),
    INT("INT"),
    FLOAT("FLOAT"),
    STRING("STRING"),
    FUNCTION("FUNCTION"),
    VAR("VAR"),
    RETURN("RETURN"),
    ASSIGN("="),
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
    RBRACE("{")
}

public data class Token(val type: TokenType, val text: String)