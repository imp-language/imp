// Generated from org\imp\jvm\ImpParser.g4 by ANTLR 4.9.1
package org.imp.jvm;
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
	 * Visit a parse tree produced by the {@code UnaryNotExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryNotExpression(ImpParser.UnaryNotExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AdditiveExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(ImpParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RelationalExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(ImpParser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PostIncrementExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostIncrementExpression(ImpParser.PostIncrementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryAddExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryAddExpression(ImpParser.UnaryAddExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnarySubtractExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnarySubtractExpression(ImpParser.UnarySubtractExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LogicalAndExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpression(ImpParser.LogicalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PowerExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowerExpression(ImpParser.PowerExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpression(ImpParser.LiteralExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LogicalOrExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOrExpression(ImpParser.LogicalOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MemberIndexExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberIndexExpression(ImpParser.MemberIndexExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierExpression(ImpParser.IdentifierExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewObjectExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewObjectExpression(ImpParser.NewObjectExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PropertyAccessExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyAccessExpression(ImpParser.PropertyAccessExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PostDecrementExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostDecrementExpression(ImpParser.PostDecrementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualityExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(ImpParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MultiplicativeExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(ImpParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallStatementExpression}
	 * labeled alternative in {@link ImpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStatementExpression(ImpParser.CallStatementExpressionContext ctx);
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
	 * Visit a parse tree produced by {@link ImpParser#newObjectStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewObjectStatement(ImpParser.NewObjectStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#classStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassStatement(ImpParser.ClassStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#interfaceBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceBlock(ImpParser.InterfaceBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#methodSignature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodSignature(ImpParser.MethodSignatureContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperty(ImpParser.PropertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#classProperty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassProperty(ImpParser.ClassPropertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#classBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBlock(ImpParser.ClassBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#enumBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumBlock(ImpParser.EnumBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#enumMember}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumMember(ImpParser.EnumMemberContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#importStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStatement(ImpParser.ImportStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#exportStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExportStatement(ImpParser.ExportStatementContext ctx);
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
	 * Visit a parse tree produced by {@link ImpParser#objectType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectType(ImpParser.ObjectTypeContext ctx);
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
	 * Visit a parse tree produced by {@link ImpParser#iteratorDestructuring}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIteratorDestructuring(ImpParser.IteratorDestructuringContext ctx);
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
	 * Visit a parse tree produced by {@link ImpParser#identifierList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierList(ImpParser.IdentifierListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpParser#stringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(ImpParser.StringLiteralContext ctx);
}