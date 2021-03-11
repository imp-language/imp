lexer grammar ImpLexer;
// remember: lexer rules are uppercase


//
// Whitespace and comments
//
WhiteSpace : [ \t\r\n\u000C]+ -> skip;
Comment: '//' ~[\r\n]* -> skip;
CommentMultiLine : '/*' .*? '*/' -> skip;




// Keywords
LOOP : 'loop';
IF : 'if';
ELSE : 'else';
FUNCTION : 'function';
RETURN : 'return';
VAL : 'val';
MUT : 'mut';

BOOL : 'bool';
INT : 'int';
FLOAT : 'float';
CHAR : 'char';
STRING : 'string';

EXPORT : 'export';
IMPORT : 'import';
FROM : 'from';
AS : 'as';

NEW : 'new';
CLASS : 'class';
INTERFACE : 'interface';
ENUM : 'enum';
PUBLIC : 'public';

IN : 'in';



// Separators
LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
LBRACK : '[';
RBRACK : ']';
SEMICOLON : ';';
COMMA : ',';
DOT : '.';


// Operators
ASSIGN : '=';
GT : '>';
LT : '<';
LE : '<=';
GE : '>=';
EQUAL : '==';
NOTEQUAL : '!=';
TILDE : '~';
COLON : ':';
BANG : '!';
NOT: 'not';
AND : '&&' | 'and';
// OR : '||' | 'or';
INC : '++';
DEC : '--';
ADD : '+';
SUB : '-';
MUL : '*';
DIV : '/';
BITAND : '&';
BITOR : '|';
POW : '^';
MOD : '%';
FATARROW : '=>';


// Boolean Literals
BooleanLiteral
	:	'true'
	|	'false'
	;

// Decimal Literals
DECIMAL_LIT : ([1-9] [0-9]*) | '0'; // number that doesn't start with 0, or just 0
FLOAT_LIT : DECIMALS ('.' DECIMALS? EXPONENT? | EXPONENT)
                       | '.' DECIMALS EXPONENT?
                       ;


IDENTIFIER : ALPHA (ALPHA | DECIMALS)*;

// String literals
RAW_STRING_LIT         : '`' ~'`'*                      '`';

STRING_LITERAL : ('"' DoubleStringCharacter* '"');

// Fragments

fragment DoubleStringCharacter
    : ~["\\\r\n]
    // | '\\' EscapeSequence
    // | LineContinuation
    ;


fragment DECIMALS
    : [0-9]+
    ;
fragment OCTAL_DIGIT
    : [0-7]
    ;
fragment HEX_DIGIT
    : [0-9a-fA-F]
    ;
fragment EXPONENT
    : [eE] [+-]? DECIMALS
    ;

fragment ALPHA
    : [a-zA-Z_]
    ;

