// Generated from c:\Users\Matt\Documents\github\mh15\imp\proposal\grammar\ImpParser.g4 by ANTLR 4.8
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ImpParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WhiteSpace=1, Comment=2, CommentMultiLine=3, LOOP=4, IF=5, ELSE=6, FUNCTION=7, 
		RETURN=8, VAL=9, MUT=10, BOOL=11, INT=12, FLOAT=13, CHAR=14, STRING=15, 
		EXPORT=16, IMPORT=17, NEW=18, CLASS=19, ENUM=20, IN=21, LPAREN=22, RPAREN=23, 
		LBRACE=24, RBRACE=25, LBRACK=26, RBRACK=27, SEMICOLON=28, COMMA=29, DOT=30, 
		ASSIGN=31, GT=32, LT=33, LE=34, GE=35, EQUAL=36, NOTEQUAL=37, TILDE=38, 
		COLON=39, BANG=40, NOT=41, AND=42, INC=43, DEC=44, ADD=45, SUB=46, MUL=47, 
		DIV=48, BITAND=49, BITOR=50, POW=51, MOD=52, FATARROW=53, BooleanLiteral=54, 
		DECIMAL_LIT=55, FLOAT_LIT=56, IDENTIFIER=57, RAW_STRING_LIT=58, STRING_LITERAL=59;
	public static final int
		RULE_program = 0, RULE_sourceElements = 1, RULE_statement = 2, RULE_simpleStatement = 3, 
		RULE_statementList = 4, RULE_block = 5, RULE_expressionList = 6, RULE_expression = 7, 
		RULE_terminalExpr = 8, RULE_unaryExpr = 9, RULE_expressionStatement = 10, 
		RULE_assignment = 11, RULE_assign_op = 12, RULE_loopStatement = 13, RULE_loopCondition = 14, 
		RULE_incDecStatement = 15, RULE_returnStatement = 16, RULE_ifStatement = 17, 
		RULE_functionStatement = 18, RULE_arguments = 19, RULE_argument = 20, 
		RULE_type = 21, RULE_primitiveType = 22, RULE_listType = 23, RULE_functionType = 24, 
		RULE_variableStatement = 25, RULE_variableInitialize = 26, RULE_literal = 27, 
		RULE_identifier = 28, RULE_integerLiteral = 29, RULE_floatLiteral = 30, 
		RULE_listLiteral = 31, RULE_elementList = 32, RULE_listElement = 33, RULE_stringLiteral = 34;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "sourceElements", "statement", "simpleStatement", "statementList", 
			"block", "expressionList", "expression", "terminalExpr", "unaryExpr", 
			"expressionStatement", "assignment", "assign_op", "loopStatement", "loopCondition", 
			"incDecStatement", "returnStatement", "ifStatement", "functionStatement", 
			"arguments", "argument", "type", "primitiveType", "listType", "functionType", 
			"variableStatement", "variableInitialize", "literal", "identifier", "integerLiteral", 
			"floatLiteral", "listLiteral", "elementList", "listElement", "stringLiteral"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'loop'", "'if'", "'else'", "'function'", "'return'", 
			"'val'", "'mut'", "'bool'", "'int'", "'float'", "'char'", "'string'", 
			"'export'", "'import'", "'new'", "'class'", "'enum'", "'in'", "'('", 
			"')'", "'{'", "'}'", "'['", "']'", "';'", "','", "'.'", "'='", "'>'", 
			"'<'", "'<='", "'>='", "'=='", "'!='", "'~'", "':'", "'!'", "'not'", 
			null, "'++'", "'--'", "'+'", "'-'", "'*'", "'/'", "'&'", "'|'", "'^'", 
			"'%'", "'=>'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WhiteSpace", "Comment", "CommentMultiLine", "LOOP", "IF", "ELSE", 
			"FUNCTION", "RETURN", "VAL", "MUT", "BOOL", "INT", "FLOAT", "CHAR", "STRING", 
			"EXPORT", "IMPORT", "NEW", "CLASS", "ENUM", "IN", "LPAREN", "RPAREN", 
			"LBRACE", "RBRACE", "LBRACK", "RBRACK", "SEMICOLON", "COMMA", "DOT", 
			"ASSIGN", "GT", "LT", "LE", "GE", "EQUAL", "NOTEQUAL", "TILDE", "COLON", 
			"BANG", "NOT", "AND", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", "BITAND", 
			"BITOR", "POW", "MOD", "FATARROW", "BooleanLiteral", "DECIMAL_LIT", "FLOAT_LIT", 
			"IDENTIFIER", "RAW_STRING_LIT", "STRING_LITERAL"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ImpParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ImpParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ImpParser.EOF, 0); }
		public SourceElementsContext sourceElements() {
			return getRuleContext(SourceElementsContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOOP) | (1L << IF) | (1L << FUNCTION) | (1L << RETURN) | (1L << VAL) | (1L << MUT) | (1L << LPAREN) | (1L << LBRACE) | (1L << LBRACK) | (1L << BANG) | (1L << NOT) | (1L << ADD) | (1L << SUB) | (1L << BooleanLiteral) | (1L << DECIMAL_LIT) | (1L << FLOAT_LIT) | (1L << IDENTIFIER) | (1L << RAW_STRING_LIT) | (1L << STRING_LITERAL))) != 0)) {
				{
				setState(70);
				sourceElements();
				}
			}

			setState(73);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SourceElementsContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public SourceElementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sourceElements; }
	}

	public final SourceElementsContext sourceElements() throws RecognitionException {
		SourceElementsContext _localctx = new SourceElementsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_sourceElements);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(75);
				statement();
				}
				}
				setState(78); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOOP) | (1L << IF) | (1L << FUNCTION) | (1L << RETURN) | (1L << VAL) | (1L << MUT) | (1L << LPAREN) | (1L << LBRACE) | (1L << LBRACK) | (1L << BANG) | (1L << NOT) | (1L << ADD) | (1L << SUB) | (1L << BooleanLiteral) | (1L << DECIMAL_LIT) | (1L << FLOAT_LIT) | (1L << IDENTIFIER) | (1L << RAW_STRING_LIT) | (1L << STRING_LITERAL))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public SimpleStatementContext simpleStatement() {
			return getRuleContext(SimpleStatementContext.class,0);
		}
		public LoopStatementContext loopStatement() {
			return getRuleContext(LoopStatementContext.class,0);
		}
		public FunctionStatementContext functionStatement() {
			return getRuleContext(FunctionStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statement);
		try {
			setState(86);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				block();
				}
				break;
			case RETURN:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				returnStatement();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				ifStatement();
				}
				break;
			case VAL:
			case MUT:
			case LBRACK:
			case BANG:
			case NOT:
			case ADD:
			case SUB:
			case BooleanLiteral:
			case DECIMAL_LIT:
			case FLOAT_LIT:
			case IDENTIFIER:
			case RAW_STRING_LIT:
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(83);
				simpleStatement();
				}
				break;
			case LOOP:
				enterOuterAlt(_localctx, 5);
				{
				setState(84);
				loopStatement();
				}
				break;
			case FUNCTION:
			case LPAREN:
				enterOuterAlt(_localctx, 6);
				{
				setState(85);
				functionStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleStatementContext extends ParserRuleContext {
		public IncDecStatementContext incDecStatement() {
			return getRuleContext(IncDecStatementContext.class,0);
		}
		public VariableStatementContext variableStatement() {
			return getRuleContext(VariableStatementContext.class,0);
		}
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public SimpleStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleStatement; }
	}

	public final SimpleStatementContext simpleStatement() throws RecognitionException {
		SimpleStatementContext _localctx = new SimpleStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_simpleStatement);
		try {
			setState(92);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(88);
				incDecStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(89);
				variableStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(90);
				expressionStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(91);
				assignment();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementListContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public StatementListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementList; }
	}

	public final StatementListContext statementList() throws RecognitionException {
		StatementListContext _localctx = new StatementListContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_statementList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(94);
				statement();
				}
				}
				setState(97); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOOP) | (1L << IF) | (1L << FUNCTION) | (1L << RETURN) | (1L << VAL) | (1L << MUT) | (1L << LPAREN) | (1L << LBRACE) | (1L << LBRACK) | (1L << BANG) | (1L << NOT) | (1L << ADD) | (1L << SUB) | (1L << BooleanLiteral) | (1L << DECIMAL_LIT) | (1L << FLOAT_LIT) | (1L << IDENTIFIER) | (1L << RAW_STRING_LIT) | (1L << STRING_LITERAL))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(ImpParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(ImpParser.RBRACE, 0); }
		public StatementListContext statementList() {
			return getRuleContext(StatementListContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			match(LBRACE);
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOOP) | (1L << IF) | (1L << FUNCTION) | (1L << RETURN) | (1L << VAL) | (1L << MUT) | (1L << LPAREN) | (1L << LBRACE) | (1L << LBRACK) | (1L << BANG) | (1L << NOT) | (1L << ADD) | (1L << SUB) | (1L << BooleanLiteral) | (1L << DECIMAL_LIT) | (1L << FLOAT_LIT) | (1L << IDENTIFIER) | (1L << RAW_STRING_LIT) | (1L << STRING_LITERAL))) != 0)) {
				{
				setState(100);
				statementList();
				}
			}

			setState(103);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ImpParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ImpParser.COMMA, i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			expression(0);
			setState(110);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(106);
				match(COMMA);
				setState(107);
				expression(0);
				}
				}
				setState(112);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public TerminalExprContext terminalExpr() {
			return getRuleContext(TerminalExprContext.class,0);
		}
		public UnaryExprContext unaryExpr() {
			return getRuleContext(UnaryExprContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode POW() { return getToken(ImpParser.POW, 0); }
		public TerminalNode MUL() { return getToken(ImpParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(ImpParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(ImpParser.MOD, 0); }
		public TerminalNode ADD() { return getToken(ImpParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(ImpParser.SUB, 0); }
		public TerminalNode EQUAL() { return getToken(ImpParser.EQUAL, 0); }
		public TerminalNode NOTEQUAL() { return getToken(ImpParser.NOTEQUAL, 0); }
		public TerminalNode LE() { return getToken(ImpParser.LE, 0); }
		public TerminalNode LT() { return getToken(ImpParser.LT, 0); }
		public TerminalNode GE() { return getToken(ImpParser.GE, 0); }
		public TerminalNode GT() { return getToken(ImpParser.GT, 0); }
		public TerminalNode AND() { return getToken(ImpParser.AND, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACK:
			case BooleanLiteral:
			case DECIMAL_LIT:
			case FLOAT_LIT:
			case IDENTIFIER:
			case RAW_STRING_LIT:
			case STRING_LITERAL:
				{
				setState(114);
				terminalExpr();
				}
				break;
			case BANG:
			case NOT:
			case ADD:
			case SUB:
				{
				setState(115);
				unaryExpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(135);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(133);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(118);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(119);
						match(POW);
						setState(120);
						expression(5);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(121);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(122);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(123);
						expression(5);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(124);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(125);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(126);
						expression(4);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(127);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(128);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << LT) | (1L << LE) | (1L << GE) | (1L << EQUAL) | (1L << NOTEQUAL))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(129);
						expression(3);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(130);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						{
						setState(131);
						match(AND);
						}
						setState(132);
						expression(2);
						}
						break;
					}
					} 
				}
				setState(137);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TerminalExprContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terminalExpr; }
	}

	public final TerminalExprContext terminalExpr() throws RecognitionException {
		TerminalExprContext _localctx = new TerminalExprContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_terminalExpr);
		try {
			setState(140);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(138);
				identifier();
				}
				break;
			case LBRACK:
			case BooleanLiteral:
			case DECIMAL_LIT:
			case FLOAT_LIT:
			case RAW_STRING_LIT:
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(139);
				literal();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryExprContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ADD() { return getToken(ImpParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(ImpParser.SUB, 0); }
		public TerminalNode BANG() { return getToken(ImpParser.BANG, 0); }
		public TerminalNode NOT() { return getToken(ImpParser.NOT, 0); }
		public UnaryExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpr; }
	}

	public final UnaryExprContext unaryExpr() throws RecognitionException {
		UnaryExprContext _localctx = new UnaryExprContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_unaryExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ADD:
				{
				setState(142);
				match(ADD);
				}
				break;
			case SUB:
				{
				setState(143);
				match(SUB);
				}
				break;
			case BANG:
			case NOT:
				{
				setState(144);
				_la = _input.LA(1);
				if ( !(_la==BANG || _la==NOT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(147);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public List<ExpressionListContext> expressionList() {
			return getRuleContexts(ExpressionListContext.class);
		}
		public ExpressionListContext expressionList(int i) {
			return getRuleContext(ExpressionListContext.class,i);
		}
		public Assign_opContext assign_op() {
			return getRuleContext(Assign_opContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			expressionList();
			setState(152);
			assign_op();
			setState(153);
			expressionList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assign_opContext extends ParserRuleContext {
		public TerminalNode ASSIGN() { return getToken(ImpParser.ASSIGN, 0); }
		public TerminalNode ADD() { return getToken(ImpParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(ImpParser.SUB, 0); }
		public TerminalNode MUL() { return getToken(ImpParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(ImpParser.DIV, 0); }
		public TerminalNode POW() { return getToken(ImpParser.POW, 0); }
		public TerminalNode MOD() { return getToken(ImpParser.MOD, 0); }
		public Assign_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_op; }
	}

	public final Assign_opContext assign_op() throws RecognitionException {
		Assign_opContext _localctx = new Assign_opContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_assign_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << SUB) | (1L << MUL) | (1L << DIV) | (1L << POW) | (1L << MOD))) != 0)) {
				{
				setState(155);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << SUB) | (1L << MUL) | (1L << DIV) | (1L << POW) | (1L << MOD))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(158);
			match(ASSIGN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopStatementContext extends ParserRuleContext {
		public TerminalNode LOOP() { return getToken(ImpParser.LOOP, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public LoopConditionContext loopCondition() {
			return getRuleContext(LoopConditionContext.class,0);
		}
		public LoopStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopStatement; }
	}

	public final LoopStatementContext loopStatement() throws RecognitionException {
		LoopStatementContext _localctx = new LoopStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_loopStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			match(LOOP);
			setState(162);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==VAL || _la==MUT) {
				{
				setState(161);
				loopCondition();
				}
			}

			setState(164);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopConditionContext extends ParserRuleContext {
		public VariableStatementContext variableStatement() {
			return getRuleContext(VariableStatementContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ImpParser.SEMICOLON, 0); }
		public TerminalNode IN() { return getToken(ImpParser.IN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LoopConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopCondition; }
	}

	public final LoopConditionContext loopCondition() throws RecognitionException {
		LoopConditionContext _localctx = new LoopConditionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_loopCondition);
		try {
			setState(173);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(166);
				variableStatement();
				setState(167);
				match(SEMICOLON);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(169);
				variableStatement();
				setState(170);
				match(IN);
				setState(171);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IncDecStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode INC() { return getToken(ImpParser.INC, 0); }
		public TerminalNode DEC() { return getToken(ImpParser.DEC, 0); }
		public IncDecStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_incDecStatement; }
	}

	public final IncDecStatementContext incDecStatement() throws RecognitionException {
		IncDecStatementContext _localctx = new IncDecStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_incDecStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			expression(0);
			setState(176);
			_la = _input.LA(1);
			if ( !(_la==INC || _la==DEC) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(ImpParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(RETURN);
			setState(179);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(ImpParser.IF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(ImpParser.ELSE, 0); }
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			match(IF);
			setState(182);
			expression(0);
			setState(183);
			block();
			setState(189);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(184);
				match(ELSE);
				setState(187);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case IF:
					{
					setState(185);
					ifStatement();
					}
					break;
				case LBRACE:
					{
					setState(186);
					block();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionStatementContext extends ParserRuleContext {
		public TerminalNode FUNCTION() { return getToken(ImpParser.FUNCTION, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(ImpParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ImpParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode FATARROW() { return getToken(ImpParser.FATARROW, 0); }
		public FunctionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionStatement; }
	}

	public final FunctionStatementContext functionStatement() throws RecognitionException {
		FunctionStatementContext _localctx = new FunctionStatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_functionStatement);
		int _la;
		try {
			setState(210);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FUNCTION:
				enterOuterAlt(_localctx, 1);
				{
				setState(191);
				match(FUNCTION);
				setState(192);
				identifier();
				setState(193);
				match(LPAREN);
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(194);
					arguments();
					}
				}

				setState(197);
				match(RPAREN);
				setState(199);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << LPAREN) | (1L << IDENTIFIER))) != 0)) {
					{
					setState(198);
					type();
					}
				}

				setState(201);
				block();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(203);
				match(LPAREN);
				setState(205);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(204);
					arguments();
					}
				}

				setState(207);
				match(RPAREN);
				setState(208);
				match(FATARROW);
				setState(209);
				block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentsContext extends ParserRuleContext {
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ImpParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ImpParser.COMMA, i);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			argument();
			setState(217);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(213);
				match(COMMA);
				setState(214);
				argument();
				}
				}
				setState(219);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_argument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			identifier();
			setState(221);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public ListTypeContext listType() {
			return getRuleContext(ListTypeContext.class,0);
		}
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public FunctionTypeContext functionType() {
			return getRuleContext(FunctionTypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_type);
		try {
			setState(226);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(223);
				listType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(224);
				primitiveType();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(225);
				functionType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimitiveTypeContext extends ParserRuleContext {
		public TerminalNode BOOL() { return getToken(ImpParser.BOOL, 0); }
		public TerminalNode INT() { return getToken(ImpParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(ImpParser.FLOAT, 0); }
		public TerminalNode CHAR() { return getToken(ImpParser.CHAR, 0); }
		public TerminalNode STRING() { return getToken(ImpParser.STRING, 0); }
		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveType; }
	}

	public final PrimitiveTypeContext primitiveType() throws RecognitionException {
		PrimitiveTypeContext _localctx = new PrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListTypeContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(ImpParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(ImpParser.RBRACK, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ListTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listType; }
	}

	public final ListTypeContext listType() throws RecognitionException {
		ListTypeContext _localctx = new ListTypeContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_listType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				{
				setState(230);
				identifier();
				}
				break;
			case BOOL:
			case INT:
			case FLOAT:
			case CHAR:
			case STRING:
				{
				setState(231);
				primitiveType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(234);
			match(LBRACK);
			setState(235);
			match(RBRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionTypeContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(ImpParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ImpParser.RPAREN, 0); }
		public TerminalNode FATARROW() { return getToken(ImpParser.FATARROW, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public FunctionTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionType; }
	}

	public final FunctionTypeContext functionType() throws RecognitionException {
		FunctionTypeContext _localctx = new FunctionTypeContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_functionType);
		int _la;
		try {
			setState(245);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(237);
				identifier();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(238);
				match(LPAREN);
				setState(240);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(239);
					arguments();
					}
				}

				setState(242);
				match(RPAREN);
				setState(243);
				match(FATARROW);
				setState(244);
				type();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableStatementContext extends ParserRuleContext {
		public List<VariableInitializeContext> variableInitialize() {
			return getRuleContexts(VariableInitializeContext.class);
		}
		public VariableInitializeContext variableInitialize(int i) {
			return getRuleContext(VariableInitializeContext.class,i);
		}
		public TerminalNode VAL() { return getToken(ImpParser.VAL, 0); }
		public TerminalNode MUT() { return getToken(ImpParser.MUT, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ImpParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ImpParser.COMMA, i);
		}
		public VariableStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableStatement; }
	}

	public final VariableStatementContext variableStatement() throws RecognitionException {
		VariableStatementContext _localctx = new VariableStatementContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_variableStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			_la = _input.LA(1);
			if ( !(_la==VAL || _la==MUT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(248);
			variableInitialize();
			setState(253);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(249);
				match(COMMA);
				setState(250);
				variableInitialize();
				}
				}
				setState(255);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableInitializeContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(ImpParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableInitializeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableInitialize; }
	}

	public final VariableInitializeContext variableInitialize() throws RecognitionException {
		VariableInitializeContext _localctx = new VariableInitializeContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_variableInitialize);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			identifier();
			setState(259);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(257);
				match(ASSIGN);
				setState(258);
				expression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public ListLiteralContext listLiteral() {
			return getRuleContext(ListLiteralContext.class,0);
		}
		public StringLiteralContext stringLiteral() {
			return getRuleContext(StringLiteralContext.class,0);
		}
		public IntegerLiteralContext integerLiteral() {
			return getRuleContext(IntegerLiteralContext.class,0);
		}
		public FloatLiteralContext floatLiteral() {
			return getRuleContext(FloatLiteralContext.class,0);
		}
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_literal);
		try {
			setState(265);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACK:
				enterOuterAlt(_localctx, 1);
				{
				setState(261);
				listLiteral();
				}
				break;
			case RAW_STRING_LIT:
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(262);
				stringLiteral();
				}
				break;
			case BooleanLiteral:
			case DECIMAL_LIT:
				enterOuterAlt(_localctx, 3);
				{
				setState(263);
				integerLiteral();
				}
				break;
			case FLOAT_LIT:
				enterOuterAlt(_localctx, 4);
				{
				setState(264);
				floatLiteral();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ImpParser.IDENTIFIER, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntegerLiteralContext extends ParserRuleContext {
		public TerminalNode DECIMAL_LIT() { return getToken(ImpParser.DECIMAL_LIT, 0); }
		public TerminalNode BooleanLiteral() { return getToken(ImpParser.BooleanLiteral, 0); }
		public IntegerLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integerLiteral; }
	}

	public final IntegerLiteralContext integerLiteral() throws RecognitionException {
		IntegerLiteralContext _localctx = new IntegerLiteralContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_integerLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(269);
			_la = _input.LA(1);
			if ( !(_la==BooleanLiteral || _la==DECIMAL_LIT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FloatLiteralContext extends ParserRuleContext {
		public TerminalNode FLOAT_LIT() { return getToken(ImpParser.FLOAT_LIT, 0); }
		public FloatLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_floatLiteral; }
	}

	public final FloatLiteralContext floatLiteral() throws RecognitionException {
		FloatLiteralContext _localctx = new FloatLiteralContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_floatLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			match(FLOAT_LIT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListLiteralContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(ImpParser.LBRACK, 0); }
		public ElementListContext elementList() {
			return getRuleContext(ElementListContext.class,0);
		}
		public TerminalNode RBRACK() { return getToken(ImpParser.RBRACK, 0); }
		public ListLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listLiteral; }
	}

	public final ListLiteralContext listLiteral() throws RecognitionException {
		ListLiteralContext _localctx = new ListLiteralContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_listLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(273);
			match(LBRACK);
			setState(274);
			elementList();
			setState(275);
			match(RBRACK);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementListContext extends ParserRuleContext {
		public List<TerminalNode> COMMA() { return getTokens(ImpParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ImpParser.COMMA, i);
		}
		public List<ListElementContext> listElement() {
			return getRuleContexts(ListElementContext.class);
		}
		public ListElementContext listElement(int i) {
			return getRuleContext(ListElementContext.class,i);
		}
		public ElementListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementList; }
	}

	public final ElementListContext elementList() throws RecognitionException {
		ElementListContext _localctx = new ElementListContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_elementList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(277);
					match(COMMA);
					}
					} 
				}
				setState(282);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			}
			setState(284);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LBRACK) | (1L << BANG) | (1L << NOT) | (1L << ADD) | (1L << SUB) | (1L << BooleanLiteral) | (1L << DECIMAL_LIT) | (1L << FLOAT_LIT) | (1L << IDENTIFIER) | (1L << RAW_STRING_LIT) | (1L << STRING_LITERAL))) != 0)) {
				{
				setState(283);
				listElement();
				}
			}

			setState(294);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(287); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(286);
						match(COMMA);
						}
						}
						setState(289); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==COMMA );
					setState(291);
					listElement();
					}
					} 
				}
				setState(296);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			}
			setState(300);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(297);
				match(COMMA);
				}
				}
				setState(302);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListElementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ListElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listElement; }
	}

	public final ListElementContext listElement() throws RecognitionException {
		ListElementContext _localctx = new ListElementContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_listElement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringLiteralContext extends ParserRuleContext {
		public TerminalNode RAW_STRING_LIT() { return getToken(ImpParser.RAW_STRING_LIT, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(ImpParser.STRING_LITERAL, 0); }
		public StringLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringLiteral; }
	}

	public final StringLiteralContext stringLiteral() throws RecognitionException {
		StringLiteralContext _localctx = new StringLiteralContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_stringLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			_la = _input.LA(1);
			if ( !(_la==RAW_STRING_LIT || _la==STRING_LITERAL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 7:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 5);
		case 1:
			return precpred(_ctx, 4);
		case 2:
			return precpred(_ctx, 3);
		case 3:
			return precpred(_ctx, 2);
		case 4:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3=\u0136\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\3\2\5\2J\n\2\3\2\3\2\3\3\6\3O\n\3\r\3\16\3P\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\5\4Y\n\4\3\5\3\5\3\5\3\5\5\5_\n\5\3\6\6\6b\n\6"+
		"\r\6\16\6c\3\7\3\7\5\7h\n\7\3\7\3\7\3\b\3\b\3\b\7\bo\n\b\f\b\16\br\13"+
		"\b\3\t\3\t\3\t\5\tw\n\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\7\t\u0088\n\t\f\t\16\t\u008b\13\t\3\n\3\n\5\n\u008f\n\n"+
		"\3\13\3\13\3\13\5\13\u0094\n\13\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\16"+
		"\5\16\u009f\n\16\3\16\3\16\3\17\3\17\5\17\u00a5\n\17\3\17\3\17\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\5\20\u00b0\n\20\3\21\3\21\3\21\3\22\3\22"+
		"\3\22\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00be\n\23\5\23\u00c0\n\23\3"+
		"\24\3\24\3\24\3\24\5\24\u00c6\n\24\3\24\3\24\5\24\u00ca\n\24\3\24\3\24"+
		"\3\24\3\24\5\24\u00d0\n\24\3\24\3\24\3\24\5\24\u00d5\n\24\3\25\3\25\3"+
		"\25\7\25\u00da\n\25\f\25\16\25\u00dd\13\25\3\26\3\26\3\26\3\27\3\27\3"+
		"\27\5\27\u00e5\n\27\3\30\3\30\3\31\3\31\5\31\u00eb\n\31\3\31\3\31\3\31"+
		"\3\32\3\32\3\32\5\32\u00f3\n\32\3\32\3\32\3\32\5\32\u00f8\n\32\3\33\3"+
		"\33\3\33\3\33\7\33\u00fe\n\33\f\33\16\33\u0101\13\33\3\34\3\34\3\34\5"+
		"\34\u0106\n\34\3\35\3\35\3\35\3\35\5\35\u010c\n\35\3\36\3\36\3\37\3\37"+
		"\3 \3 \3!\3!\3!\3!\3\"\7\"\u0119\n\"\f\"\16\"\u011c\13\"\3\"\5\"\u011f"+
		"\n\"\3\"\6\"\u0122\n\"\r\"\16\"\u0123\3\"\7\"\u0127\n\"\f\"\16\"\u012a"+
		"\13\"\3\"\7\"\u012d\n\"\f\"\16\"\u0130\13\"\3#\3#\3$\3$\3$\2\3\20%\2\4"+
		"\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDF\2\f\4"+
		"\2\61\62\66\66\3\2/\60\3\2\"\'\3\2*+\4\2/\62\65\66\3\2-.\3\2\r\21\3\2"+
		"\13\f\3\289\3\2<=\2\u0141\2I\3\2\2\2\4N\3\2\2\2\6X\3\2\2\2\b^\3\2\2\2"+
		"\na\3\2\2\2\fe\3\2\2\2\16k\3\2\2\2\20v\3\2\2\2\22\u008e\3\2\2\2\24\u0093"+
		"\3\2\2\2\26\u0097\3\2\2\2\30\u0099\3\2\2\2\32\u009e\3\2\2\2\34\u00a2\3"+
		"\2\2\2\36\u00af\3\2\2\2 \u00b1\3\2\2\2\"\u00b4\3\2\2\2$\u00b7\3\2\2\2"+
		"&\u00d4\3\2\2\2(\u00d6\3\2\2\2*\u00de\3\2\2\2,\u00e4\3\2\2\2.\u00e6\3"+
		"\2\2\2\60\u00ea\3\2\2\2\62\u00f7\3\2\2\2\64\u00f9\3\2\2\2\66\u0102\3\2"+
		"\2\28\u010b\3\2\2\2:\u010d\3\2\2\2<\u010f\3\2\2\2>\u0111\3\2\2\2@\u0113"+
		"\3\2\2\2B\u011a\3\2\2\2D\u0131\3\2\2\2F\u0133\3\2\2\2HJ\5\4\3\2IH\3\2"+
		"\2\2IJ\3\2\2\2JK\3\2\2\2KL\7\2\2\3L\3\3\2\2\2MO\5\6\4\2NM\3\2\2\2OP\3"+
		"\2\2\2PN\3\2\2\2PQ\3\2\2\2Q\5\3\2\2\2RY\5\f\7\2SY\5\"\22\2TY\5$\23\2U"+
		"Y\5\b\5\2VY\5\34\17\2WY\5&\24\2XR\3\2\2\2XS\3\2\2\2XT\3\2\2\2XU\3\2\2"+
		"\2XV\3\2\2\2XW\3\2\2\2Y\7\3\2\2\2Z_\5 \21\2[_\5\64\33\2\\_\5\26\f\2]_"+
		"\5\30\r\2^Z\3\2\2\2^[\3\2\2\2^\\\3\2\2\2^]\3\2\2\2_\t\3\2\2\2`b\5\6\4"+
		"\2a`\3\2\2\2bc\3\2\2\2ca\3\2\2\2cd\3\2\2\2d\13\3\2\2\2eg\7\32\2\2fh\5"+
		"\n\6\2gf\3\2\2\2gh\3\2\2\2hi\3\2\2\2ij\7\33\2\2j\r\3\2\2\2kp\5\20\t\2"+
		"lm\7\37\2\2mo\5\20\t\2nl\3\2\2\2or\3\2\2\2pn\3\2\2\2pq\3\2\2\2q\17\3\2"+
		"\2\2rp\3\2\2\2st\b\t\1\2tw\5\22\n\2uw\5\24\13\2vs\3\2\2\2vu\3\2\2\2w\u0089"+
		"\3\2\2\2xy\f\7\2\2yz\7\65\2\2z\u0088\5\20\t\7{|\f\6\2\2|}\t\2\2\2}\u0088"+
		"\5\20\t\7~\177\f\5\2\2\177\u0080\t\3\2\2\u0080\u0088\5\20\t\6\u0081\u0082"+
		"\f\4\2\2\u0082\u0083\t\4\2\2\u0083\u0088\5\20\t\5\u0084\u0085\f\3\2\2"+
		"\u0085\u0086\7,\2\2\u0086\u0088\5\20\t\4\u0087x\3\2\2\2\u0087{\3\2\2\2"+
		"\u0087~\3\2\2\2\u0087\u0081\3\2\2\2\u0087\u0084\3\2\2\2\u0088\u008b\3"+
		"\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a\21\3\2\2\2\u008b"+
		"\u0089\3\2\2\2\u008c\u008f\5:\36\2\u008d\u008f\58\35\2\u008e\u008c\3\2"+
		"\2\2\u008e\u008d\3\2\2\2\u008f\23\3\2\2\2\u0090\u0094\7/\2\2\u0091\u0094"+
		"\7\60\2\2\u0092\u0094\t\5\2\2\u0093\u0090\3\2\2\2\u0093\u0091\3\2\2\2"+
		"\u0093\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0096\5\20\t\2\u0096\25"+
		"\3\2\2\2\u0097\u0098\5\20\t\2\u0098\27\3\2\2\2\u0099\u009a\5\16\b\2\u009a"+
		"\u009b\5\32\16\2\u009b\u009c\5\16\b\2\u009c\31\3\2\2\2\u009d\u009f\t\6"+
		"\2\2\u009e\u009d\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0"+
		"\u00a1\7!\2\2\u00a1\33\3\2\2\2\u00a2\u00a4\7\6\2\2\u00a3\u00a5\5\36\20"+
		"\2\u00a4\u00a3\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a7"+
		"\5\f\7\2\u00a7\35\3\2\2\2\u00a8\u00a9\5\64\33\2\u00a9\u00aa\7\36\2\2\u00aa"+
		"\u00b0\3\2\2\2\u00ab\u00ac\5\64\33\2\u00ac\u00ad\7\27\2\2\u00ad\u00ae"+
		"\5\20\t\2\u00ae\u00b0\3\2\2\2\u00af\u00a8\3\2\2\2\u00af\u00ab\3\2\2\2"+
		"\u00b0\37\3\2\2\2\u00b1\u00b2\5\20\t\2\u00b2\u00b3\t\7\2\2\u00b3!\3\2"+
		"\2\2\u00b4\u00b5\7\n\2\2\u00b5\u00b6\5\20\t\2\u00b6#\3\2\2\2\u00b7\u00b8"+
		"\7\7\2\2\u00b8\u00b9\5\20\t\2\u00b9\u00bf\5\f\7\2\u00ba\u00bd\7\b\2\2"+
		"\u00bb\u00be\5$\23\2\u00bc\u00be\5\f\7\2\u00bd\u00bb\3\2\2\2\u00bd\u00bc"+
		"\3\2\2\2\u00be\u00c0\3\2\2\2\u00bf\u00ba\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0"+
		"%\3\2\2\2\u00c1\u00c2\7\t\2\2\u00c2\u00c3\5:\36\2\u00c3\u00c5\7\30\2\2"+
		"\u00c4\u00c6\5(\25\2\u00c5\u00c4\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c7"+
		"\3\2\2\2\u00c7\u00c9\7\31\2\2\u00c8\u00ca\5,\27\2\u00c9\u00c8\3\2\2\2"+
		"\u00c9\u00ca\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cc\5\f\7\2\u00cc\u00d5"+
		"\3\2\2\2\u00cd\u00cf\7\30\2\2\u00ce\u00d0\5(\25\2\u00cf\u00ce\3\2\2\2"+
		"\u00cf\u00d0\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d2\7\31\2\2\u00d2\u00d3"+
		"\7\67\2\2\u00d3\u00d5\5\f\7\2\u00d4\u00c1\3\2\2\2\u00d4\u00cd\3\2\2\2"+
		"\u00d5\'\3\2\2\2\u00d6\u00db\5*\26\2\u00d7\u00d8\7\37\2\2\u00d8\u00da"+
		"\5*\26\2\u00d9\u00d7\3\2\2\2\u00da\u00dd\3\2\2\2\u00db\u00d9\3\2\2\2\u00db"+
		"\u00dc\3\2\2\2\u00dc)\3\2\2\2\u00dd\u00db\3\2\2\2\u00de\u00df\5:\36\2"+
		"\u00df\u00e0\5,\27\2\u00e0+\3\2\2\2\u00e1\u00e5\5\60\31\2\u00e2\u00e5"+
		"\5.\30\2\u00e3\u00e5\5\62\32\2\u00e4\u00e1\3\2\2\2\u00e4\u00e2\3\2\2\2"+
		"\u00e4\u00e3\3\2\2\2\u00e5-\3\2\2\2\u00e6\u00e7\t\b\2\2\u00e7/\3\2\2\2"+
		"\u00e8\u00eb\5:\36\2\u00e9\u00eb\5.\30\2\u00ea\u00e8\3\2\2\2\u00ea\u00e9"+
		"\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ed\7\34\2\2\u00ed\u00ee\7\35\2\2"+
		"\u00ee\61\3\2\2\2\u00ef\u00f8\5:\36\2\u00f0\u00f2\7\30\2\2\u00f1\u00f3"+
		"\5(\25\2\u00f2\u00f1\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4"+
		"\u00f5\7\31\2\2\u00f5\u00f6\7\67\2\2\u00f6\u00f8\5,\27\2\u00f7\u00ef\3"+
		"\2\2\2\u00f7\u00f0\3\2\2\2\u00f8\63\3\2\2\2\u00f9\u00fa\t\t\2\2\u00fa"+
		"\u00ff\5\66\34\2\u00fb\u00fc\7\37\2\2\u00fc\u00fe\5\66\34\2\u00fd\u00fb"+
		"\3\2\2\2\u00fe\u0101\3\2\2\2\u00ff\u00fd\3\2\2\2\u00ff\u0100\3\2\2\2\u0100"+
		"\65\3\2\2\2\u0101\u00ff\3\2\2\2\u0102\u0105\5:\36\2\u0103\u0104\7!\2\2"+
		"\u0104\u0106\5\20\t\2\u0105\u0103\3\2\2\2\u0105\u0106\3\2\2\2\u0106\67"+
		"\3\2\2\2\u0107\u010c\5@!\2\u0108\u010c\5F$\2\u0109\u010c\5<\37\2\u010a"+
		"\u010c\5> \2\u010b\u0107\3\2\2\2\u010b\u0108\3\2\2\2\u010b\u0109\3\2\2"+
		"\2\u010b\u010a\3\2\2\2\u010c9\3\2\2\2\u010d\u010e\7;\2\2\u010e;\3\2\2"+
		"\2\u010f\u0110\t\n\2\2\u0110=\3\2\2\2\u0111\u0112\7:\2\2\u0112?\3\2\2"+
		"\2\u0113\u0114\7\34\2\2\u0114\u0115\5B\"\2\u0115\u0116\7\35\2\2\u0116"+
		"A\3\2\2\2\u0117\u0119\7\37\2\2\u0118\u0117\3\2\2\2\u0119\u011c\3\2\2\2"+
		"\u011a\u0118\3\2\2\2\u011a\u011b\3\2\2\2\u011b\u011e\3\2\2\2\u011c\u011a"+
		"\3\2\2\2\u011d\u011f\5D#\2\u011e\u011d\3\2\2\2\u011e\u011f\3\2\2\2\u011f"+
		"\u0128\3\2\2\2\u0120\u0122\7\37\2\2\u0121\u0120\3\2\2\2\u0122\u0123\3"+
		"\2\2\2\u0123\u0121\3\2\2\2\u0123\u0124\3\2\2\2\u0124\u0125\3\2\2\2\u0125"+
		"\u0127\5D#\2\u0126\u0121\3\2\2\2\u0127\u012a\3\2\2\2\u0128\u0126\3\2\2"+
		"\2\u0128\u0129\3\2\2\2\u0129\u012e\3\2\2\2\u012a\u0128\3\2\2\2\u012b\u012d"+
		"\7\37\2\2\u012c\u012b\3\2\2\2\u012d\u0130\3\2\2\2\u012e\u012c\3\2\2\2"+
		"\u012e\u012f\3\2\2\2\u012fC\3\2\2\2\u0130\u012e\3\2\2\2\u0131\u0132\5"+
		"\20\t\2\u0132E\3\2\2\2\u0133\u0134\t\13\2\2\u0134G\3\2\2\2$IPX^cgpv\u0087"+
		"\u0089\u008e\u0093\u009e\u00a4\u00af\u00bd\u00bf\u00c5\u00c9\u00cf\u00d4"+
		"\u00db\u00e4\u00ea\u00f2\u00f7\u00ff\u0105\u010b\u011a\u011e\u0123\u0128"+
		"\u012e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}