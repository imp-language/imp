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

program : EOL* (statement EOL*)* EOF;

/*
 * Core
 */

// Core Language Constructs
// If statements, Loops, Returns, Switch, etc
statement
    : block
    | functionStatement
    | classStatement
    | structStatement
    | returnStatement
    | ifStatement
    | loopStatement
    | assignment
    | expression
    | variableStatement
    | importStatement
    | exportStatement
    ;


statementList
    : (statement EOL*)+
    ;


block
    : LBRACE EOL* statementList? RBRACE EOL*
    ;

/*
 * Expressions
 */
// Comma-separated list of one or more expressions
expressionList
    : expression (COMMA expression)*
    ;

expression
    : callStatement                                    #CallStatementExpression
    | identifier                                       #IdentifierReferenceExpression
    | literal                                          #LiteralExpression
    | (BANG | NOT) expression                          #UnaryNotExpression
    | (ADD | SUB) expression                           #UnaryAdditiveExpression
    | <assoc=right> expression POW expression          #PowerExpression
    | expression (MUL | DIV | MOD) expression          #MultiplicativeExpression
    | expression (ADD | SUB) expression                #AdditiveExpression
    | expression cmp=(LE | LT | GE | GT | EQUAL | NOTEQUAL) expression        #RelationalExpression
    | expression (AND | OR) expression                 #LogicalExpression
    | expression (DOT identifier)+                     #PropertyAccessExpression
    | expression DOT callStatement                     #MethodCallExpression
    | expression (INC | DEC)                           #PostIncrementExpression
    | NEW identifier LPAREN (expressionList)? RPAREN   #NewObjectExpression
    | expression LBRACK expression RBRACK              #MemberIndexExpression
    | expression ASSIGN expression                     #AssignmentExpression
    | LPAREN expression RPAREN                         #WrappedExpression
    ;


/*
 * Statements
 */

assignment
//    : expressionList assign_op expressionList
    : expression assign_op expression;

// +=, -=, *=, /=, ^=, %=, or of course =
assign_op
    : (ADD | SUB | MUL | DIV | POW | MOD)? ASSIGN
    ;

// Loops
loopStatement
    : LOOP (loopCondition)? block
    ;

loopCondition
    : variableStatement SEMICOLON expression SEMICOLON expression SEMICOLON? #ForLoopCondition // val i = 0; i < 10; i++
    | variableStatement IN expression #ForInLoopCondition // val item, idx in list
    | expression #WhileLoopCondition
    ;


// return statement
returnStatement
    : RETURN (expression)?
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



// Classes
classStatement
    : INTERFACE identifier LBRACE interfaceBlock RBRACE
    | CLASS identifier (COLON identifier)? LBRACE classBlock RBRACE
    | ENUM identifier LBRACE enumBlock RBRACE
    ;

// Structs
structStatement
    : STRUCT identifier LBRACE structBlock EOL* RBRACE
    ;

structBlock
    : (EOL* fieldDef COMMA* EOL)*
    ;

fieldDef
    : identifier type?
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
    : (identifier | primitiveType) LBRACK RBRACK   #TypeList
    | primitiveType         #TypePrimitive
    | identifier            #TypeStruct
    ;

primitiveType
    : BOOL | INT | FLOAT | CHAR | STRING | VOID;


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
    | doubleLiteral
    ;

identifier
    : IDENTIFIER
    ;

// Integers and booleans
integerLiteral
    : (SUB)? DECIMAL_LIT
    ;

booleanLiteral
    : BooleanLiteral
    ;

floatLiteral
    : (SUB)? FLOAT_LIT
    ;

doubleLiteral
    : (SUB)? DOUBLE_LIT
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
