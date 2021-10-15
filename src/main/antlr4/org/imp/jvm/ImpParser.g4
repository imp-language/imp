parser grammar ImpParser;

//    Question mark stands for: zero or one
//    Plus stands for: one or more
//    Star stands for: zero or more

options {
    tokenVocab = ImpLexer;
}

program : EOL* (importStatement EOL*)* (statement EOL*)* EOF;

statement
    : block
    | structStatement
    | returnStatement
    | ifStatement
    | loopStatement
    | assignment
    | expression
    | variableStatement
    | exportStatement
    | enumStatement
//    | typeAliasStatement
    ;

statementList
    : (statement EOL*)+
    ;

block
    : LBRACE EOL* statementList? RBRACE EOL*
    ;

// Comma-separated list of one or more expressions
expressionList
    : expression (COMMA expression)*
    ;

expression
    : callStatement                                    #CallStatementExpression // Todo: rename to "callExpression" or "functionCall"
    | function                                         #FunctionDefinition
    | expression DOT callStatement                     #MethodCallExpression
    | expression (DOT identifier)+                     #PropertyAccessExpression
    | expression LBRACK expression RBRACK              #MemberIndexExpression
    | identifier                                       #IdentifierReferenceExpression
    | literal                                          #LiteralExpression
    | LPAREN expression RPAREN                         #WrappedExpression
    | (BANG | NOT) expression                          #UnaryNotExpression
    | (ADD | SUB) expression                           #UnaryAdditiveExpression
    | <assoc=right> expression POW expression          #PowerExpression
    | expression (ADD | SUB | MUL | DIV | MOD) expression #AdditiveExpression
    | expression cmp=(LE | LT | GE | GT | EQUAL | NOTEQUAL) expression        #RelationalExpression
    | expression cmp=(AND | OR) expression             #LogicalExpression
    | expression (INC | DEC)                           #PostIncrementExpression
    | expression ASSIGN expression                     #AssignmentExpression
    | NEW identifier LPAREN (expressionList)? RPAREN   #NewObjectExpression
    ;

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
    : modifiers? FUNCTION identifier (LT type GT)? LPAREN (arguments)? RPAREN (type)? block
    | LPAREN (arguments)? RPAREN FATARROW block
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


// Enums
enumStatement
    : ENUM identifier LBRACE (EOL* enumDef COMMA* EOL*)* EOL* RBRACE
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
    | anonymousTuple        #TypeAnonymousTuple
    ;

primitiveType
    : BOOL | INT | FLOAT | CHAR | STRING | VOID;

anonymousTuple
    : LPAREN (type (COMMA type)* COMMA?) RPAREN
    ;

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
    : stringLiteral
    | doubleLiteral
    | integerLiteral
    | floatLiteral
    | booleanLiteral
    | collectionLiteral
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
    : LBRACK (type | expressionList)? RBRACK
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
