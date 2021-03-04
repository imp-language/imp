parser grammar ImpLiterals;

import ImpParser;

/*
 * Literals
 */
literal
    : listLiteral
    | stringLiteral
    | integerLiteral
    | floatLiteral
    ;

identifier
    : IDENTIFIER
    ;

// Integers and booleans
integerLiteral
    : DECIMAL_LIT
    | BooleanLiteral
    ;

floatLiteral
    : FLOAT_LIT
    ;



// Lists
listLiteral
    : (LBRACK elementList RBRACK)
    ;

elementList
    : COMMA* expression? (COMMA+ expression)* COMMA* // Yes, everything is optional
    ;


// Strings
stringLiteral
    : RAW_STRING_LIT
    | STRING_LITERAL
    ;
    // TODO: template string literals?