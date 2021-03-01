@ECHO OFF
cd build\proposal\grammar

echo Enter source code to parse:
call java -cp ".;antlr-4.9.1-complete.jar" org.antlr.v4.gui.TestRig Imp %1% -gui



cd ../../../

@ECHO ON