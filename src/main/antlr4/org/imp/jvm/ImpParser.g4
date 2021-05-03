parser grammar ImpParser;
// remember: parser rules are lowercase

//    Question mark stands for: zero or one
//    Plus stands for: one or more
//    Star stands for: zero or more


options {
    tokenVocab = ImpLexer;
}

//@header {
//    package org.imp.jvm
//}

program : statement* EOF;

/*
 * Core
 */

// Core Language Constructs
// If statements, Loops, Returns, Switch, etc
statement
    : block
    | functionStatement
    | classStatement
    | returnStatement
    | ifStatement
    | loopStatement
    | expression
    | variableStatement
    | assignment
    | importStatement
    | exportStatement
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
    : identifier                                       #IdentifierReferenceExpression
    | literal                                          #LiteralExpression
    | (BANG | NOT) expression                          #UnaryNotExpression
    | (ADD | SUB) expression                           #UnaryAdditiveExpression
    | <assoc=right> expression POW expression          #PowerExpression
    | expression (MUL | DIV | MOD) expression          #MultiplicativeExpression
    | expression (ADD | SUB) expression                #AdditiveExpression
    | expression cmp=(LE | LT | GE | GT | EQUAL | NOTEQUAL) expression        #RelationalExpression
    | expression (AND | OR) expression                 #LogicalExpression
    | expression DOT expression                        #PropertyAccessExpression
    | expression DOT callStatement                     #MethodCallExpression
    | expression (INC | DEC)                           #PostIncrementExpression
    | callStatement                                    #CallStatementExpression
    | newObjectStatement                               #NewObjectExpression
    | expression LBRACK expression RBRACK              #MemberIndexExpression
    ;


/*
 * Statements
 */

assignment
//    : expressionList assign_op expressionList
    : identifier assign_op expression;

// +=, -=, *=, /=, ^=, %=, or of course =
assign_op
    : (ADD | SUB | MUL | DIV | POW | MOD)? ASSIGN
    ;

// Loops
loopStatement
    : LOOP (loopCondition)? block
    ;

loopCondition
    : variableStatement SEMICOLON expression SEMICOLON statement SEMICOLON? #ForLoopCondition // val i = 0; i < 10; i++
    | variableStatement IN expression #ForInLoopCondition // val item, idx in list
    | expression #WhileLoopCondition
    ;


// return expression
returnStatement
    : RETURN expression
    ;

// if condition { } else if condition { } else { }
ifStatement
    : IF expression trueStatement=statement (ELSE falseStatement=statement)?
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

// Function call
callStatement
    : identifier LPAREN (expressionList)? RPAREN
    ;

// New Object
newObjectStatement
    : NEW identifier LPAREN (expressionList)? RPAREN
    ;

// Classes
classStatement
    : INTERFACE identifier LBRACE interfaceBlock RBRACE
    | CLASS identifier (COLON identifier)? LBRACE classBlock RBRACE
    | ENUM identifier LBRACE enumBlock RBRACE
    ;


interfaceBlock
    : (methodSignature | (property type))*
    ;

methodSignature
    : identifier LPAREN (arguments)? RPAREN (type)?
    ;

property
    : (PUBLIC)? (VAL | MUT) identifier;

classProperty
    : property (type)? ASSIGN expression
    | property type
    ;
classBlock
     :  (((PUBLIC)? methodSignature block) | (classProperty))*
     ;

enumBlock
     : enumMember (COMMA enumMember)* (COMMA)?
     ;

enumMember: identifier (ASSIGN expression)?;

// Import/Export
importStatement
    : IMPORT identifier (AS identifier)?
    | IMPORT identifierList FROM identifier;

exportStatement
    : EXPORT (classStatement | functionStatement) // class, function, interface, enum
    | EXPORT identifierList // identifiers
    ;




// Type
type
    : listType                   // lists
    | primitiveType   // single instances of a type
    | objectType               // functions passed as parameters to functions
    ;

primitiveType
    : BOOL | INT | FLOAT | CHAR | STRING;


// function acceptsList(words string[])
listType
    : (identifier | primitiveType) LBRACK RBRACK
    ;

objectType
    : identifier  // function or class type saved to a variable
    | LPAREN (arguments)? RPAREN FATARROW type // anonymous type signature
    ;


// Declare and optionally initialize variables
variableStatement
    : (VAL | MUT) variableInitialize/* (COMMA variableInitialize)* COMMA?*/
    | (VAL | MUT) iteratorDestructuring
    ;

// Initialize a single variable
variableInitialize
    : identifier (ASSIGN expression)?
    ;

// val a, b, _ = getPosts("matthall")
iteratorDestructuring
    : identifierList (ASSIGN expression)?;

/*
 * Literals
 */
literal
    : listLiteral
    | stringLiteral
    | integerLiteral
    | floatLiteral
    | booleanLiteral
    ;

identifier
    : IDENTIFIER
    ;

// Integers and booleans
integerLiteral
    : DECIMAL_LIT
    ;

booleanLiteral
    : BooleanLiteral
    ;

floatLiteral
    : FLOAT_LIT
    ;


//    Question mark stands for: zero or one
//    Plus stands for: one or more
//    Star stands for: zero or more

// Lists
listLiteral
    : (LBRACK elementList RBRACK)
    ;

elementList
    : COMMA* expression? (COMMA+ expression)* COMMA* // Yes, everything is optional
    ;

// used in imports/exports
identifierList
    : identifier (COMMA identifier)* COMMA?
    ; // TODO: do we allow ( ) around identifier lists


// Strings
stringLiteral
    : RAW_STRING_LIT
    | STRING_LITERAL
    ;
    // TODO: template string literals?
