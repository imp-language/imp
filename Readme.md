# Imp (README TODO)

Imp is a statically typed and compiled scripting language with the goal of increasing programmer confidence.

Note that this project is under development and functionality can be expected to change rapidly. I'm blogging my
progress [here](https://matthall.codes/tags/imp/).

## Example

```go
func testUnion(param int | float[] | int[]) {
    match param as id {
        int -> {
            log(id)
            log("int")
        }
        int[] -> {
            log(id)
            log("int[]")
        }
        // Comment this case out for a MatchCoverage error
        float[] -> {
            log(id)
            log("float[]")
        }
    }
}

testUnion(4)
testUnion([4,3,2,1])
testUnion([4.0])
```

## About

### Goals

* **Fast Iteration** - support rapid iteration through quick compilation and feedback
* **Familiar Syntax** - a C-style syntax with curly brackets, no semicolons
* **Scripting Language Characteristics** - execution begins at the first statement of the entry file
* **Statically Typed** - catch errors at compile-time
* **Write Less Code** - the syntax should be succinct and readable
* **Module System** - import what you need from other files in the project

### Roadmap

- [x] Grammars
- [x] Parser (custom Pratt parser)
- [x] AST
- [x] Type inference
    - [x] Variable assignments from literals
    - [X] Variable assignments from expressions
- [x] JVM Codegen
- [ ] Core
    - [x] Loops
    - [x] Control flow
    - [x] Structs
    - [x] Functions
    - [x] Pattern matching
    - [ ] Enums
- [ ] First-class functions
    - [ ] Closures
    - [ ] Storing functions in a variable
- [ ] Event loop
- [ ] Debugger
- [ ] Incremental compilation
- [ ] REPL

## Usage

### Windows

```shell
imp.bat <filename>
```

### Unix

```shell
chmod u+x imp # the first time only
./imp <filename>
```

### Notes

To compile Java files using the Imp runtime:

```bash
javac --enable-preview -source 14 -cp .compile;target/classes .compile/main/Example.java
```

To run the packaged Imp compiler JAR:

```bash
java --enable-preview -jar target/imp-0.1.jar examples/scratch.imp
```

To see a CLOC visualization, https://codeflower.la/?name=imp&owner=mh15&branch=main.
