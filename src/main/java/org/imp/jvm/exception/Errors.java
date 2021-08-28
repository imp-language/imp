package org.imp.jvm.exception;


import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.text.MessageFormat;
import java.util.Map;


public enum Errors implements ErrorContext {
    ImplementationError(-1, "If you are seeing this message it indicates a regression in the Imp compiler. Please contact the developers.",
            "This error message should never occur. "),
    MissingFieldType(0, "Each struct field must have a type. Consider adding `string` or another primitive type after the field name.",
            "Missing type declaration on struct field `{0}` near {1}."
    ),
    TypeNotFound(1, "Make sure all types are defined or builtin.",
            "Type `{0}` does not exist in the current scope."),
    PrimitiveTypePropertyAccess(2, "Property access cannot be used on variables with primitive types like int, float, or bool.",
            "Variable `{0}` does not support property access as it has a primitive type."),
    StructFieldNotFound(3, "Check the type definition of custom types to find the correct field name, or to add a field of this name.",
            "Identifier `{0}` does not exist as a field on the parent struct."),
    IncrementInvalidType(4, "Increment and decrement operations can only be called on numeric types",
            "Expression `{0}` cannot be incremented or decremented."),
    LogicalOperationInvalidType(5, "Logical operations such as `and` and `or` can only be applied on expressions that evaluate to booleans.",
            "Expression `{0}` does not evaluate to a boolean value."),
    StructConstructorMismatch(6, "Check the struct definition to make sure you are providing a value for all fields in the struct.",
            "Constructor call for struct `{0}` does not provide values for all fields in the struct."),
    FunctionNotFound(7, "Make sure to define or import all functions being called.",
            "No functions named `{0}` exist in the current scope."),
    // Todo: print attempted signature or available signatures instead
    FunctionSignatureMismatch(8, "Check the parameter positions and types of the called function.",
            "No function overloads exist on `{0}` that match the parameters `{1}`."),
    LocalVariableNotFound(9, "Make sure all variables referenced in this file are defined or imported.",
            "No variable named `{0}` exists in the current scope."),
    DuplicateFunctionOverloads(10, "Two functions with the same name cannot have the same parameters.",
            "Function `{0}` has two or more overloads with the same signature."),
    ModuleNotImported(11, "Make sure to import all modules referenced in the file.",
            "Module `{0}` has not been imported."),
    ModuleNotFound(12, "Is the module misspelled?",
            "Module `{0}` is not found.")

    //
    ;

    public final String suggestion;
    public final int code;
    public final String templateString;


    Errors(int code, String suggestion, String templateString) {


        this.suggestion = suggestion;
        this.code = code;
        this.templateString = templateString;
    }


    public static String getLocation(Token token) {
        int line = token.getLine();
        int col = token.getCharPositionInLine() + 1;
        return line + ":" + col;
    }

    public String template(String filename, ParserRuleContext ctx, Object... varargs) {
        filename += ".imp";
        if (ctx != null) {
            Token token = ctx.getStart();
            String s = filename + "@" + getLocation(token);
            s += " SyntaxError[" + code + "]: ";

            String text = MessageFormat.format(templateString, varargs);
            s += text;
            s += "\n\t" + suggestion;
            return s;
        }
        return filename + "@[null] SyntaxError[" + code + "]: [null]";
    }

    @Override
    public int getLine() {
        return 0;
    }

    @Override
    public int getCol() {
        return 0;
    }

    @Override
    public String getText() {
        return null;
    }
}
