package org.imp.jvm.exception;


import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

interface TemplateFunction {
    String run(ParserRuleContext token);
}

public enum SemanticErrors implements ErrorContext {
    MissingFieldType(0, "Each struct field must have a type. Consider adding 'string' or another primitive type after the field name.",
            ctx -> "Missing type declaration on struct field '" + ctx.getText() + "' near " + getLocation(ctx.getStart())
    ),
    TypeNotFound(1, "Make sure all types you are referencing have been defined.",
            ctx -> "Type '" + ctx.getText() + "' does not exist in the current scope."),
    PrimitiveTypePropertyAccess(2, "Property access cannot be used on variables with primitive types like int, float, or bool.",
            ctx -> "Variable '" + ctx.getStart().getText() + "' does not support property access as it has a primitive type."),
    StructFieldNotFound(3, "Check the type definition of custom types to find the correct field name, or to add a field of this name.",
            ctx -> "Identifier '" + ctx.getText() + "' does not exist as a field on the parent struct."),
    IncrementInvalidType(4, "Increment and decrement operations can only be called on numeric types",
            ctx -> "Expression '" + ctx.getText() + "' cannot be incremented or decremented.")
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

    private static String getLocation(Token token) {
        int line = token.getLine();
        int col = token.getTokenIndex();
        String location = line + ":" + col;
        return location;
    }

    public String template(ParserRuleContext ctx) {
        Token token = ctx.getStart();
        String s = "filename@" + getLocation(token);
        s += " SyntaxError[" + code + "]: ";
        s += template.run(ctx);
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
