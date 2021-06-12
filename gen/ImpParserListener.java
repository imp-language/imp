// Generated from C:/Users/Matt/Documents/github/mh15/imp\ImpParser.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ImpParser}.
 */
public interface ImpParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ImpParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(ImpParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(ImpParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ImpParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ImpParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#simpleStatement}.
	 * @param ctx the parse tree
	 */
	void enterSimpleStatement(ImpParser.SimpleStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#simpleStatement}.
	 * @param ctx the parse tree
	 */
	void exitSimpleStatement(ImpParser.SimpleStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#statementList}.
	 * @param ctx the parse tree
	 */
	void enterStatementList(ImpParser.StatementListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#statementList}.
	 * @param ctx the parse tree
	 */
	void exitStatementList(ImpParser.StatementListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ImpParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ImpParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(ImpParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(ImpParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ImpParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ImpParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#terminalExpr}.
	 * @param ctx the parse tree
	 */
	void enterTerminalExpr(ImpParser.TerminalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#terminalExpr}.
	 * @param ctx the parse tree
	 */
	void exitTerminalExpr(ImpParser.TerminalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(ImpParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(ImpParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(ImpParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(ImpParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(ImpParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(ImpParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#assign_op}.
	 * @param ctx the parse tree
	 */
	void enterAssign_op(ImpParser.Assign_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#assign_op}.
	 * @param ctx the parse tree
	 */
	void exitAssign_op(ImpParser.Assign_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(ImpParser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(ImpParser.LoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#loopCondition}.
	 * @param ctx the parse tree
	 */
	void enterLoopCondition(ImpParser.LoopConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#loopCondition}.
	 * @param ctx the parse tree
	 */
	void exitLoopCondition(ImpParser.LoopConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#incDecStatement}.
	 * @param ctx the parse tree
	 */
	void enterIncDecStatement(ImpParser.IncDecStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#incDecStatement}.
	 * @param ctx the parse tree
	 */
	void exitIncDecStatement(ImpParser.IncDecStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(ImpParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(ImpParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ImpParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ImpParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#functionStatement}.
	 * @param ctx the parse tree
	 */
	void enterFunctionStatement(ImpParser.FunctionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#functionStatement}.
	 * @param ctx the parse tree
	 */
	void exitFunctionStatement(ImpParser.FunctionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(ImpParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(ImpParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(ImpParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(ImpParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#callStatement}.
	 * @param ctx the parse tree
	 */
	void enterCallStatement(ImpParser.CallStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#callStatement}.
	 * @param ctx the parse tree
	 */
	void exitCallStatement(ImpParser.CallStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(ImpParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(ImpParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(ImpParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(ImpParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#listType}.
	 * @param ctx the parse tree
	 */
	void enterListType(ImpParser.ListTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#listType}.
	 * @param ctx the parse tree
	 */
	void exitListType(ImpParser.ListTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#functionType}.
	 * @param ctx the parse tree
	 */
	void enterFunctionType(ImpParser.FunctionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#functionType}.
	 * @param ctx the parse tree
	 */
	void exitFunctionType(ImpParser.FunctionTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#variableStatement}.
	 * @param ctx the parse tree
	 */
	void enterVariableStatement(ImpParser.VariableStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#variableStatement}.
	 * @param ctx the parse tree
	 */
	void exitVariableStatement(ImpParser.VariableStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#variableInitialize}.
	 * @param ctx the parse tree
	 */
	void enterVariableInitialize(ImpParser.VariableInitializeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#variableInitialize}.
	 * @param ctx the parse tree
	 */
	void exitVariableInitialize(ImpParser.VariableInitializeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(ImpParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(ImpParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(ImpParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(ImpParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(ImpParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(ImpParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(ImpParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(ImpParser.FloatLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void enterListLiteral(ImpParser.ListLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void exitListLiteral(ImpParser.ListLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#elementList}.
	 * @param ctx the parse tree
	 */
	void enterElementList(ImpParser.ElementListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#elementList}.
	 * @param ctx the parse tree
	 */
	void exitElementList(ImpParser.ElementListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#listElement}.
	 * @param ctx the parse tree
	 */
	void enterListElement(ImpParser.ListElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#listElement}.
	 * @param ctx the parse tree
	 */
	void exitListElement(ImpParser.ListElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(ImpParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(ImpParser.StringLiteralContext ctx);
}