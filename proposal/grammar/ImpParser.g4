parser grammar ImpParser;
// remember: parser rules are lowercase

options {
    tokenVocab = ImpLexer;
}

// program : sourceElements? EOF;

// sourceElements : statement+;

statement
    : block
    ;

statementList
    : statement+
    ;


block
    : LBRACE statementList? RBRACE
    ;


// Statements

returnStatement
    : RETURN
    ;

ifStatement
    : IF ()? 


expression
    : primaryExpr
    | unaryExpr
    


// Literals

arrayLiteral
    : ('[' elementList ']')
    ;

elementList
    : ','* arrayElement? (','+ arrayElement)* ','* // Yes, everything is optional
    ;

arrayElement
    : COMMA // todo
    ;
