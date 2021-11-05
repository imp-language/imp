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
statement      → export
               | typeAlias
               | struct
               | enum
               | function
               | variable 
               | loop
               | if
               | return
               | block
               
               ;
               

export         → "export" statement;               
typeAlias      → "type" IDENTIFIER "=" "extern" STRING;
struct         → "struct" IDENTIFIER "{" (parameter ","?)*;
enum           → "enum" IDENTIFIER "{" (IDENTIFIER ","?)* "}";
function       → "func" IDENTIFIER "(" parameters ")" IDENTIFIER? block;


block          → "{" (statement)* "};


```

Expressions:

```ebnf

```

Some helper rules:

```ebnf
parameter      → IDENTIFIER IDENTIFIER;
parameters     → nameType ("," nameType)* ",";
```