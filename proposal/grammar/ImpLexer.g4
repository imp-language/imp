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

BOOL : 'bool';
INT : 'int';
FLOAT : 'float';
CHAR : 'char';

EXPORT : 'export';
IMPORT : 'import';

NEW : 'new';
CLASS : 'class';
ENUM : 'enum';



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
// BANG : '!' | 'not';
// AND : '&&' | 'and';
// OR : '||' | 'or';
INC : '++';
DEC : '--';
ADD : '+';
SUB : '-';
MUL : '*';
DIV : '/';
BITAND : '&';
BITOR : '|';
CARET : '^';
MOD : '%';
FATARROW : '=>';


// Boolean Literals
BooleanLiteral
	:	'true'
	|	'false'
	;

// Decimal Literals
DECIMAL_LIT : [1-9] [0-9]*;
FLOAT_LIT : DECIMALS ('.' DECIMALS? EXPONENT? | EXPONENT)
                       | '.' DECIMALS EXPONENT?
                       ;

// Fragments


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