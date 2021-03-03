parser grammar ImpParser;
// remember: parser rules are lowercase

//    Question mark stands for: zero or one
//    Plus stands for: one or more
//    Star stands for: zero or more


options {
    tokenVocab = ImpLexer;
}

program : sourceElements? EOF;

sourceElements : statement+;

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
    | assignment
    ;

statementList
    : statement+
    ;


block
    : LBRACE statementList? RBRACE
    ;

/*
 * Expressions
 */
// Comma-separated list of one or more expressions
expressionList
    : expression (COMMA expression)*
    ;

expression
    : terminalExpr
    | unaryExpr
    | <assoc=right> expression POW expression
    | expression (MUL | DIV | MOD) expression
    | expression (ADD | SUB) expression
    | expression (EQUAL | NOTEQUAL | LE | LT | GE | GT) expression
    | expression (AND) expression
    ;

// literals and the like
terminalExpr
    : identifier
    | literal
    ;

// not equals, negation, etc
unaryExpr
    : (ADD | SUB | (BANG | NOT)) expression
    ;

/*
 * Statements
 */
// Simple expression
expressionStatement
    : expression
    ;

assignment
    : expressionList assign_op expressionList;

// +=, -=, *=, /=, ^=, %=, or of course =
assign_op
    : (ADD | SUB | MUL | DIV | POW | MOD)? ASSIGN
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


// Declare and optionally initialize variables
variableStatement
    : (VAL | MUT) variableInitialize (COMMA variableInitialize)*
    ;

// Initialize a single variable
variableInitialize
    : identifier (ASSIGN expression)?
    ;






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
    : COMMA* listElement? (COMMA+ listElement)* COMMA* // Yes, everything is optional
    ;

listElement
    : expression // todo
    ;


// Strings
stringLiteral
    : RAW_STRING_LIT
    | STRING_LITERAL
    ;
    // TODO: template string literals?