@ECHO OFF
echo Building Lexer...
call antlr4 -o build proposal\grammar\ImpLexer.g4
echo Building Parser...
call antlr4 -o build proposal\grammar\ImpParser.g4 -lib build\proposal\grammar


echo Compiling Java class files...
call javac -cp "antlr-4.9.1-complete.jar" build\proposal\grammar\Imp*.java


call copy antlr-4.9.1-complete.jar build\proposal\grammar\antlr-4.9.1-complete.jar

@REM call run


@ECHO ON