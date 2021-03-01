@REM @ECHO OFF
@REM SET TEST_CURRENT_DIR=%CLASSPATH:.;=%
@REM if "%TEST_CURRENT_DIR%" == "%CLASSPATH%" ( SET CLASSPATH=.;%CLASSPATH% )
@REM @ECHO ON
java -cp "grammar/proposal/.;antlr-4.9.1-complete.jar;antlr-4.9.1-runtime.jar" org.antlr.v4.gui.TestRig %*