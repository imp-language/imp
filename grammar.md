In EBNF:

- `?` stands for "zero or one"
- `+` stands for "one or more"
- `*` stands for "zero or more"

The top level of any Imp program is the `program` rule.

```ebnf
program        → import* statement* EOF ;
```

A program is a series of statements.

```ebnf
statement      → expression
               | export
               | typeAlias
               | struct
               | enum
               | function
               | if
               | variable 
               | loop
               | return
               | block
               
               ;
               
import         → "import" stringLiteral
               | "import" stringLiteral "as" identifier
               | "from" stringLiteral "import" identifierList // todo
               ;

export         → "export" statement ;
typeAlias      → "type" identifier "=" "extern" STRING ;
struct         → "struct" identifier "{" (parameter ","?)* ;
enum           → "enum" identifier "{" (IDENTIFIER ","?)* "}" ;
function       → "func" identifier "(" parameters ")" identifier? block ;
if             → "if" expression block ("else" (block | if))? ;
variable       → ("mut"|"val") identifier "=" expression ;
loop           → "for" loopCondition block ;

return         → "return" expression?;
block          → "{" (statement)* "};


```

Expressions:

```ebnf
expression     → assignment
               | identifier
               | literal
               | call
               | grouping
               | new
               | propertyAccess
               | indexAccess
               | emptyList
               ;
               
               
assignment     → expression "=" expression ;
identifier     → IDENTIFIER ;
literal        → NUMBER | TRUE | FALSE | STRING | listLiteral ;
call           → identifier "(" arguments ")" ;
grouping       → "(" expression ")" 
new            → "new" call ;
propertyAccess → expression "." expression ;
indexAccess    → expression "[" expression "]" ;
emptyList      → type "[" "]" ;


```

Some helper rules:

```ebnf
parameter      → identifier type;
parameters     → nameType ("," nameType)* ",";
arguments      → expression ("," expression)* ",";
listLiteral    → "[" arguments "]" ;
loopCondition  → identifier "in" expression
               | identifier "in" expression ".." expression
               
               ;
               
type           → identifier ("[" "]")? 
               | identifier "." type
               ;

```