lexer grammar ImpLexer;
// remember: lexer rules are uppercase


channels {
    WHITESPACE_CHANNEL
}

//
// Whitespace and comments
//
EOL : '\r'? '\n';
//WhiteSpace : [ \t\r\n\u000C]+ -> channel(WHITESPACE_CHANNEL);
WhiteSpace : [ \t\u000C]+ -> skip;
Comment: '//' ~[\r\n]* -> skip;
CommentMultiLine : '/*' .*? '*/' -> skip;
//Space : [ \t\r\n\u000C]+;


// Keywords
LOOP : 'loop' | 'for';
IF : 'if';
ELSE : 'else';
FUNCTION : 'function' | 'func';
RETURN : 'return';
VAL : 'val';
MUT : 'mut';

BOOL : 'bool';
INT : 'int';
FLOAT : 'float';
CHAR : 'char';
STRING : 'string';
VOID : 'void';
ANY : 'any';

EXPORT : 'export';
IMPORT : 'import';
FROM : 'from';
AS : 'as';
EXTERN : 'extern';
TYPE: 'type';

NEW : 'new';
ENUM : 'enum';
STRUCT : 'struct';

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
VARARGS : '...';
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
OR : '||' | 'or';
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

IDENTIFIER : ALPHA (ALPHA | DECIMALS)*;

// Decimal Literals
DECIMAL_LIT : ([1-9] [0-9]*) | '0'; // number that doesn't start with 0, or just 0
FLOAT_LIT : DECIMALS ('.' DECIMALS? EXPONENT? | EXPONENT)
                       | '.' DECIMALS EXPONENT?
                       ;
DOUBLE_LIT
    : DECIMALS ('.' DECIMALS? EXPONENT? | EXPONENT) DOUBLE_SUFFIX
    | '.' DECIMALS EXPONENT? DOUBLE_SUFFIX
    | DECIMALS DOUBLE_SUFFIX
    ;

DOUBLE_SUFFIX : 'd';

// String literals
RAW_STRING_LIT         : '`' ~'`'*                      '`';

//STRING_LITERAL : ('"' DoubleStringCharacter* '"');

STRING_LITERAL
 : '"' ( '\\' [btnfr"'\\] | ~[\r\n\\"] )* '"'
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

fragment ALPHA
    : [a-zA-Z_]
    ;

