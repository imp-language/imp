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

program : EOL* (importStatement EOL*)* (statement EOL*)* EOF;




/*
 * Core
 */

// Core Language Constructs
// If statements, Loops, Returns, Switch, etc
statement
    : block
    | classStatement
    | structStatement
    | returnStatement
    | ifStatement
    | loopStatement
    | assignment
    | expression
    | variableStatement
//    | importStatement
    | exportStatement
    | enumStatement
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
    | function                                         #FunctionDefinition
    | expression (DOT identifier)+                     #PropertyAccessExpression
    | expression LBRACK expression RBRACK              #MemberIndexExpression
    | literal                                          #LiteralExpression
    | identifier                                       #IdentifierReferenceExpression
    | LPAREN expression RPAREN                         #WrappedExpression
    | (BANG | NOT) expression                          #UnaryNotExpression
    | (ADD | SUB) expression                           #UnaryAdditiveExpression
    | <assoc=right> expression POW expression          #PowerExpression
    | expression (MUL | DIV | MOD) expression          #MultiplicativeExpression
    | expression (ADD | SUB) expression                #AdditiveExpression
    | expression cmp=(LE | LT | GE | GT | EQUAL | NOTEQUAL) expression        #RelationalExpression
    | expression cmp=(AND | OR) expression             #LogicalExpression
    | expression DOT callStatement                     #MethodCallExpression
    | expression (INC | DEC)                           #PostIncrementExpression
    | expression ASSIGN expression                     #AssignmentExpression
    | NEW identifier LPAREN (expressionList)? RPAREN   #NewObjectExpression
    ;

propertyAccess
    : (DOT identifier)+
    ;

/*
 * Statements
 */

//    : expressionList assign_op expressionList
assignment
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
function
    : modifiers? FUNCTION identifier LPAREN (arguments)? RPAREN (type)? block
    | LPAREN (arguments)? RPAREN FATARROW block
    ;

operator
    : (INC|DEC|ADD|SUB|MUL|DIV)
    ;

modifiers
    : EXPORT
    ;

arguments
    : argument (COMMA argument)*
    ;

argument
    : identifier type
    ;

// Function call
callStatement
    : (identifier|type) LPAREN (expressionList)? RPAREN
    ;



// Classes
classStatement
    : INTERFACE identifier LBRACE interfaceBlock RBRACE
    | CLASS identifier (COLON identifier)? LBRACE classBlock RBRACE
    ;


// Enums
enumStatement
    : ENUM identifier LBRACE enumBlock EOL* RBRACE
    ;

enumBlock
    : (EOL* enumDef COMMA* EOL*)*
    ;

enumDef
    : identifier (ASSIGN expression)?
    ;

// Structs
structStatement
    : STRUCT identifier LBRACE structBlock EOL* RBRACE
    ;

structBlock
    : (EOL* fieldDef COMMA* EOL*)*
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


// Import/Export
importStatement
    : IMPORT stringLiteral                       #ImportFile // import "io"
    | IMPORT stringLiteral AS identifier         #ImportFileAsIdentifier // import "io" as fs
    | FROM stringLiteral IMPORT identifierList   #ImportFromFile // from "io" import read, write
;

exportStatement
    : EXPORT (function | structStatement) // class, function, interface, enum
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
    : collectionLiteral
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
collectionLiteral
    : identifier? LBRACK (type | expressionList)? RBRACK
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
