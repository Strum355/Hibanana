package xyz.noahsc.hibanana.parser

public data class ParserError(val line: Int, val startChar: Int, val endChar: Int, val text: String)