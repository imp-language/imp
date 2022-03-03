## For Potential Contributors

### Join the Community

This is a project in the early stages of development with little value in production
software, but we're always looking for feedback and contributions! Currently discussions
are held in the http://proglangdesign.net/ Discord community.

### Start a Project Using Imp

Soon we'd like to begin dogfooding the project with libraries and applications developed
in Imp. The project still has a way to go before that's feasible, but we're getting there!

## Contributing Code

We welcome any contributions but may deny PRs if they do not meet the goals of the 
project. Please communicate with the maintainer before spending time on a feature.

### Setup

To contribute to the Imp programming language you'll need to install the following:

- Git
- Java/JDK 17
- An IDE, preferrably IntelliJ

Open the project and make sure Maven has installed all dependencies.

### File Structure

Java projects are often large, sprawling codebases, so here's an attempt to break it down a bit.

- `src/main/java/org/imp/` main package.
  - `codegen/` codegen package. JVM bytecode generators for classes, methods, fields, etc.
  - `errors/` compiler error logging and template script.
  - `parser/` manually created Pratt-style parser.
  - `tokenizer/` tokenizer package.
  - `tool/` tooling package.
    - `cli/` the imp cli.
    - `manifest/` maifest parsing.
    - `API.java` core functions of the compiler.
    - `Compile.java` compiler method.
  - `types/` classes representing types at compile time.
  - `visitors/` walk the tree, each visitor implements a single pass on the AST.
  - `legacy/` imp is undergoing a rewrite, don't write code that depends on stuff in this package.
  - `Environment.java` describes the variables in a single scope.
  - `SourceFile.java` maps one-to-one with a single Imp source file at compile time.
  - `Util.java` utilities!
- `src/test/java/org/imp/test/` test package.
  - `CompilerTest.java` current automated tests.


