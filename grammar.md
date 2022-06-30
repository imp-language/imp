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
               | match
               | while
               
               ;
               
import         → "import" stringLiteral
               | "import" stringLiteral "as" identifier
               | "from" stringLiteral "import" identifierList // todo
               ;

export         → "export" (struct | typeAlias | variable | enum | function) ;
typeAlias      → "type" identifier "=" type ;
struct         → "struct" identifier "{" (parameter ","?)* ;
enum           → "enum" identifier "{" (IDENTIFIER ","?)* "}" ;
function       → "func" identifier "(" parameters ")" identifier? block ;
if             → "if" expression block ("else" (block | if))? ;
variable       → ("mut"|"val") identifier "=" expression ;
loop           → "for" loopCondition block ;

return         → "return" expression?;
block          → "{" (statement)* "};

match          → "match" expression "{" (type identifier "->" expression)* "}"

while          → "while" expression block

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
propertyAccess → expression ("." identifier)+ ;
indexAccess    → expression "[" expression "]" ;
emptyList      → type "[" "]" ;


```

Some helper rules:

```ebnf
parameter      → identifier type;
parameters     → nameType ("," nameType)* ",";
arguments      → expression ("," expression)* ","?;
listLiteral    → "[" arguments "]" ;
loopCondition  → identifier "in" expression
               | identifier "in" expression ".." expression
               
               ;
               
type           → identifier ("[" "]")? 
               | identifier "." type
               | union ;
union          →  type ("," type)* ","? ;

```