parser grammar ImpParser;
// remember: parser rules are lowercase

//    Question mark stands for: zero or one
//    Plus stands for: one or more
//    Star stands for: zero or more


options {
    tokenVocab = ImpLexer;
}

// program : sourceElements? EOF;

// sourceElements : statement+;

/*
 * Core
 */

// If statements, Loops, Returns, Switch, etc
statement
    : block
    | returnStatement
    | ifStatement
    | simpleStatement
    | loopStatement
    | functionStatement
    ;

// Increment/Decrement, Variables, Expressions
simpleStatement
    : incDecStatement
    | variableStatement
    | expressionStatement
    ;

statementList
    : statement+
    ;


block
    : LBRACE statementList? RBRACE
    ;


/*
 * Statements
 */
// Simple expression
expressionStatement
    : expression
    ;

// Loops
loopStatement
    : LOOP (loopCondition)? block
    ;

loopCondition
    : variableStatement SEMICOLON // val i = 0; i < 10; i++
    | variableStatement IN expression // val item, idx in list
    ;

// var++ var--
incDecStatement
    : expression (INC | DEC)
    ;

// return expression
returnStatement
    : RETURN expression
    ;

// if condition { } else if condition { } else { }
ifStatement
    : IF expression block (ELSE (ifStatement | block))?
    ; 


// Function definition
functionStatement
    : FUNCTION identifier LPAREN (arguments)? RPAREN (type)? block
    | LPAREN (arguments)? RPAREN FATARROW block
    ;

arguments
    : argument (COMMA argument)*
    ;

argument
    : identifier type
    ;


// Type
type
    : listType                   // lists
    | primitiveType   // single instances of a type
    | functionType               // functions passed as parameters to functions
    ;

primitiveType
    : BOOL | INT | FLOAT | CHAR | STRING;


// function acceptsList(words string[])
listType
    : (identifier | primitiveType) LBRACK RBRACK
    ;

functionType
    : identifier  // function type saved to a variable
    | LPAREN (arguments)? RPAREN FATARROW type // anonymous type signature
    ;


variableStatement
    : (VAL | MUT) variableDeclaration (COMMA variableDeclaration)*
    ;

variableDeclaration
    // : identifier+ ('=' singleExpression)?
    : identifier+ (EQUAL expression)?
    ;


/*
 * Expressions
 */
// Comma-separated list of one or more expressions
expressionList
    : expression (COMMA expression)*
    ;

expression
    : identifier
    // | primaryExpr
    // | unaryExpr
    ;





/*
 * Literals
 */

identifier
    : IDENTIFIER
    ;

arrayLiteral
    : (LBRACK elementList RBRACK)
    ;

elementList
    : COMMA* arrayElement? (COMMA+ arrayElement)* COMMA* // Yes, everything is optional
    ;

arrayElement
    : COMMA // todo
    ;
