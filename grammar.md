In EBNF:

- `?` stands for "zero or one"
- `+` stands for "one or more"
- `*` stands for "zero or more"

The top level of any Imp program is the `program` rule.

```ebnf
program        → statement* EOF ;
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
               

export         → "export" statement ;
typeAlias      → "type" IDENTIFIER "=" "extern" STRING ;
struct         → "struct" IDENTIFIER "{" (parameter ","?)* ;
enum           → "enum" IDENTIFIER "{" (IDENTIFIER ","?)* "}" ;
function       → "func" IDENTIFIER "(" parameters ")" IDENTIFIER? block ;
if             → "if" expression block ("else" (block | if))? ;
variable       → ("mut"|"val") IDENTIFIER "=" expression ;



return         → "return" expression?;
block          → "{" (statement)* "};


```

Expressions:

```ebnf
expression     → assignment
               |
               ;
assignment     →

```

Some helper rules:

```ebnf
parameter      → IDENTIFIER IDENTIFIER;
parameters     → nameType ("," nameType)* ",";
```