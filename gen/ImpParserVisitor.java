// Generated from C:/Users/Matt/Documents/github/mh15/imp\ImpParser.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ImpParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ImpParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ImpParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(ImpParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ImpParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#simpleStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleStatement(ImpParser.SimpleStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#statementList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementList(ImpParser.StatementListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ImpParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(ImpParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ImpParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#terminalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminalExpr(ImpParser.TerminalExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpr(ImpParser.UnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(ImpParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(ImpParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#assign_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_op(ImpParser.Assign_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(ImpParser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#loopCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopCondition(ImpParser.LoopConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#incDecStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncDecStatement(ImpParser.IncDecStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(ImpParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ImpParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#functionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionStatement(ImpParser.FunctionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(ImpParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(ImpParser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#callStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStatement(ImpParser.CallStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(ImpParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(ImpParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#listType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListType(ImpParser.ListTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#functionType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionType(ImpParser.FunctionTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#variableStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableStatement(ImpParser.VariableStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#variableInitialize}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableInitialize(ImpParser.VariableInitializeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ImpParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(ImpParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#integerLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(ImpParser.IntegerLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#floatLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(ImpParser.FloatLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#listLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListLiteral(ImpParser.ListLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#elementList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementList(ImpParser.ElementListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#listElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListElement(ImpParser.ListElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#stringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(ImpParser.StringLiteralContext ctx);
}