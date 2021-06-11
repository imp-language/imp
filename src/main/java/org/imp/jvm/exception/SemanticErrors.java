package org.imp.jvm.exception;


import org.antlr.v4.runtime.Token;

interface TemplateFunction {
    String run(Token token);
}

public enum SemanticErrors implements ErrorContext {
    MissingFieldType(0, "Each struct field must have a type. Consider adding 'string' or another primitive type after the field name.",
            token -> "Missing type declaration on struct field '" + token.getText() + "' near " + getLocation(token)
    ),
    TypeNotFound(1, "Make sure all types you are referencing have been defined.",
            token -> "Type '" + token.getText() + "' does not exist in the current scope."),
    PrimitiveTypePropertyAccess(2, "Property access cannot be used on variables with primitive types like int, float, or bool.",
            token -> "Variable '" + token.getText() + "' does not support property access as it has a primitive type."),
    StructFieldNotFound(3, "Check the type definition of custom types to find the correct field name, or to add a field of this name.",
            token -> "Identifier '" + token.getText() + "' does not exist as a field on the parent struct.")
    //
    ;

    public String suggestion;
    public int code;
    public TemplateFunction template;


    SemanticErrors(int code, String suggestion, TemplateFunction template) {
        this.suggestion = suggestion;
        this.code = code;
        this.template = template;
    }

    private static String getLocation(Token t) {
        int line = t.getLine();
        int col = t.getTokenIndex();
        String location = line + ":" + col;
        return location;
    }

    public String template(Token token) {
        String s = "filename@" + getLocation(token);
        s += " SyntaxError[" + code + "]: ";
        s += template.run(token);
        s += "\n\t" + suggestion;
        return s;
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