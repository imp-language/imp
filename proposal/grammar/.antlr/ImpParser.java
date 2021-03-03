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
		COLON=39, INC=40, DEC=41, ADD=42, SUB=43, MUL=44, DIV=45, BITAND=46, BITOR=47, 
		CARET=48, MOD=49, FATARROW=50, BooleanLiteral=51, DECIMAL_LIT=52, FLOAT_LIT=53, 
		IDENTIFIER=54, RAW_STRING_LIT=55, STRING_LITERAL=56;
	public static final int
		RULE_program = 0, RULE_sourceElements = 1, RULE_statement = 2, RULE_simpleStatement = 3, 
		RULE_statementList = 4, RULE_block = 5, RULE_expressionStatement = 6, 
		RULE_loopStatement = 7, RULE_loopCondition = 8, RULE_incDecStatement = 9, 
		RULE_returnStatement = 10, RULE_ifStatement = 11, RULE_functionStatement = 12, 
		RULE_arguments = 13, RULE_argument = 14, RULE_type = 15, RULE_primitiveType = 16, 
		RULE_listType = 17, RULE_functionType = 18, RULE_variableStatement = 19, 
		RULE_variableInitialize = 20, RULE_expressionList = 21, RULE_expression = 22, 
		RULE_literal = 23, RULE_identifier = 24, RULE_integerLiteral = 25, RULE_floatLiteral = 26, 
		RULE_listLiteral = 27, RULE_elementList = 28, RULE_listElement = 29, RULE_stringLiteral = 30;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "sourceElements", "statement", "simpleStatement", "statementList", 
			"block", "expressionStatement", "loopStatement", "loopCondition", "incDecStatement", 
			"returnStatement", "ifStatement", "functionStatement", "arguments", "argument", 
			"type", "primitiveType", "listType", "functionType", "variableStatement", 
			"variableInitialize", "expressionList", "expression", "literal", "identifier", 
			"integerLiteral", "floatLiteral", "listLiteral", "elementList", "listElement", 
			"stringLiteral"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'loop'", "'if'", "'else'", "'function'", "'return'", 
			"'val'", "'mut'", "'bool'", "'int'", "'float'", "'char'", "'string'", 
			"'export'", "'import'", "'new'", "'class'", "'enum'", "'in'", "'('", 
			"')'", "'{'", "'}'", "'['", "']'", "';'", "','", "'.'", "'='", "'>'", 
			"'<'", "'<='", "'>='", "'=='", "'!='", "'~'", "':'", "'++'", "'--'", 
			"'+'", "'-'", "'*'", "'/'", "'&'", "'|'", "'^'", "'%'", "'=>'"
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
			"INC", "DEC", "ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", "CARET", 
			"MOD", "FATARROW", "BooleanLiteral", "DECIMAL_LIT", "FLOAT_LIT", "IDENTIFIER", 
			"RAW_STRING_LIT", "STRING_LITERAL"
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
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOOP) | (1L << IF) | (1L << FUNCTION) | (1L << RETURN) | (1L << VAL) | (1L << MUT) | (1L << LPAREN) | (1L << LBRACE) | (1L << LBRACK) | (1L << BooleanLiteral) | (1L << DECIMAL_LIT) | (1L << FLOAT_LIT) | (1L << IDENTIFIER) | (1L << RAW_STRING_LIT) | (1L << STRING_LITERAL))) != 0)) {
				{
				setState(62);
				sourceElements();
				}
			}

			setState(65);
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
			setState(68); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(67);
				statement();
				}
				}
				setState(70); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOOP) | (1L << IF) | (1L << FUNCTION) | (1L << RETURN) | (1L << VAL) | (1L << MUT) | (1L << LPAREN) | (1L << LBRACE) | (1L << LBRACK) | (1L << BooleanLiteral) | (1L << DECIMAL_LIT) | (1L << FLOAT_LIT) | (1L << IDENTIFIER) | (1L << RAW_STRING_LIT) | (1L << STRING_LITERAL))) != 0) );
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
			setState(78);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(72);
				block();
				}
				break;
			case RETURN:
				enterOuterAlt(_localctx, 2);
				{
				setState(73);
				returnStatement();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 3);
				{
				setState(74);
				ifStatement();
				}
				break;
			case VAL:
			case MUT:
			case LBRACK:
			case BooleanLiteral:
			case DECIMAL_LIT:
			case FLOAT_LIT:
			case IDENTIFIER:
			case RAW_STRING_LIT:
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(75);
				simpleStatement();
				}
				break;
			case LOOP:
				enterOuterAlt(_localctx, 5);
				{
				setState(76);
				loopStatement();
				}
				break;
			case FUNCTION:
			case LPAREN:
				enterOuterAlt(_localctx, 6);
				{
				setState(77);
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
		public SimpleStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleStatement; }
	}

	public final SimpleStatementContext simpleStatement() throws RecognitionException {
		SimpleStatementContext _localctx = new SimpleStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_simpleStatement);
		try {
			setState(83);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				incDecStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				variableStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				expressionStatement();
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
			setState(86); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(85);
				statement();
				}
				}
				setState(88); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOOP) | (1L << IF) | (1L << FUNCTION) | (1L << RETURN) | (1L << VAL) | (1L << MUT) | (1L << LPAREN) | (1L << LBRACE) | (1L << LBRACK) | (1L << BooleanLiteral) | (1L << DECIMAL_LIT) | (1L << FLOAT_LIT) | (1L << IDENTIFIER) | (1L << RAW_STRING_LIT) | (1L << STRING_LITERAL))) != 0) );
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
			setState(90);
			match(LBRACE);
			setState(92);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOOP) | (1L << IF) | (1L << FUNCTION) | (1L << RETURN) | (1L << VAL) | (1L << MUT) | (1L << LPAREN) | (1L << LBRACE) | (1L << LBRACK) | (1L << BooleanLiteral) | (1L << DECIMAL_LIT) | (1L << FLOAT_LIT) | (1L << IDENTIFIER) | (1L << RAW_STRING_LIT) | (1L << STRING_LITERAL))) != 0)) {
				{
				setState(91);
				statementList();
				}
			}

			setState(94);
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
		enterRule(_localctx, 12, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			expression();
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
		enterRule(_localctx, 14, RULE_loopStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(LOOP);
			setState(100);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==VAL || _la==MUT) {
				{
				setState(99);
				loopCondition();
				}
			}

			setState(102);
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
		enterRule(_localctx, 16, RULE_loopCondition);
		try {
			setState(111);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(104);
				variableStatement();
				setState(105);
				match(SEMICOLON);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(107);
				variableStatement();
				setState(108);
				match(IN);
				setState(109);
				expression();
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
		enterRule(_localctx, 18, RULE_incDecStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			expression();
			setState(114);
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
		enterRule(_localctx, 20, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			match(RETURN);
			setState(117);
			expression();
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
		enterRule(_localctx, 22, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			match(IF);
			setState(120);
			expression();
			setState(121);
			block();
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(122);
				match(ELSE);
				setState(125);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case IF:
					{
					setState(123);
					ifStatement();
					}
					break;
				case LBRACE:
					{
					setState(124);
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
		enterRule(_localctx, 24, RULE_functionStatement);
		int _la;
		try {
			setState(148);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FUNCTION:
				enterOuterAlt(_localctx, 1);
				{
				setState(129);
				match(FUNCTION);
				setState(130);
				identifier();
				setState(131);
				match(LPAREN);
				setState(133);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(132);
					arguments();
					}
				}

				setState(135);
				match(RPAREN);
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << FLOAT) | (1L << CHAR) | (1L << STRING) | (1L << LPAREN) | (1L << IDENTIFIER))) != 0)) {
					{
					setState(136);
					type();
					}
				}

				setState(139);
				block();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(141);
				match(LPAREN);
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(142);
					arguments();
					}
				}

				setState(145);
				match(RPAREN);
				setState(146);
				match(FATARROW);
				setState(147);
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
		enterRule(_localctx, 26, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			argument();
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(151);
				match(COMMA);
				setState(152);
				argument();
				}
				}
				setState(157);
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
		enterRule(_localctx, 28, RULE_argument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			identifier();
			setState(159);
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
		enterRule(_localctx, 30, RULE_type);
		try {
			setState(164);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(161);
				listType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(162);
				primitiveType();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(163);
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
		enterRule(_localctx, 32, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
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
		enterRule(_localctx, 34, RULE_listType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				{
				setState(168);
				identifier();
				}
				break;
			case BOOL:
			case INT:
			case FLOAT:
			case CHAR:
			case STRING:
				{
				setState(169);
				primitiveType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(172);
			match(LBRACK);
			setState(173);
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
		enterRule(_localctx, 36, RULE_functionType);
		int _la;
		try {
			setState(183);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(175);
				identifier();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(176);
				match(LPAREN);
				setState(178);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(177);
					arguments();
					}
				}

				setState(180);
				match(RPAREN);
				setState(181);
				match(FATARROW);
				setState(182);
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
		enterRule(_localctx, 38, RULE_variableStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			_la = _input.LA(1);
			if ( !(_la==VAL || _la==MUT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(186);
			variableInitialize();
			setState(191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(187);
				match(COMMA);
				setState(188);
				variableInitialize();
				}
				}
				setState(193);
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
		enterRule(_localctx, 40, RULE_variableInitialize);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			identifier();
			setState(197);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(195);
				match(ASSIGN);
				setState(196);
				expression();
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
		enterRule(_localctx, 42, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			expression();
			setState(204);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(200);
				match(COMMA);
				setState(201);
				expression();
				}
				}
				setState(206);
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
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_expression);
		try {
			setState(209);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(207);
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
				setState(208);
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
		enterRule(_localctx, 46, RULE_literal);
		try {
			setState(215);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACK:
				enterOuterAlt(_localctx, 1);
				{
				setState(211);
				listLiteral();
				}
				break;
			case RAW_STRING_LIT:
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(212);
				stringLiteral();
				}
				break;
			case BooleanLiteral:
			case DECIMAL_LIT:
				enterOuterAlt(_localctx, 3);
				{
				setState(213);
				integerLiteral();
				}
				break;
			case FLOAT_LIT:
				enterOuterAlt(_localctx, 4);
				{
				setState(214);
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
		enterRule(_localctx, 48, RULE_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
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
		enterRule(_localctx, 50, RULE_integerLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
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
		enterRule(_localctx, 52, RULE_floatLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
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
		enterRule(_localctx, 54, RULE_listLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(223);
			match(LBRACK);
			setState(224);
			elementList();
			setState(225);
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
		enterRule(_localctx, 56, RULE_elementList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(230);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(227);
					match(COMMA);
					}
					} 
				}
				setState(232);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			setState(234);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LBRACK) | (1L << BooleanLiteral) | (1L << DECIMAL_LIT) | (1L << FLOAT_LIT) | (1L << IDENTIFIER) | (1L << RAW_STRING_LIT) | (1L << STRING_LITERAL))) != 0)) {
				{
				setState(233);
				listElement();
				}
			}

			setState(244);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(237); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(236);
						match(COMMA);
						}
						}
						setState(239); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==COMMA );
					setState(241);
					listElement();
					}
					} 
				}
				setState(246);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			}
			setState(250);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(247);
				match(COMMA);
				}
				}
				setState(252);
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
		enterRule(_localctx, 58, RULE_listElement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			expression();
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
		enterRule(_localctx, 60, RULE_stringLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(255);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3:\u0104\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \3\2"+
		"\5\2B\n\2\3\2\3\2\3\3\6\3G\n\3\r\3\16\3H\3\4\3\4\3\4\3\4\3\4\3\4\5\4Q"+
		"\n\4\3\5\3\5\3\5\5\5V\n\5\3\6\6\6Y\n\6\r\6\16\6Z\3\7\3\7\5\7_\n\7\3\7"+
		"\3\7\3\b\3\b\3\t\3\t\5\tg\n\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n"+
		"r\n\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0080\n\r"+
		"\5\r\u0082\n\r\3\16\3\16\3\16\3\16\5\16\u0088\n\16\3\16\3\16\5\16\u008c"+
		"\n\16\3\16\3\16\3\16\3\16\5\16\u0092\n\16\3\16\3\16\3\16\5\16\u0097\n"+
		"\16\3\17\3\17\3\17\7\17\u009c\n\17\f\17\16\17\u009f\13\17\3\20\3\20\3"+
		"\20\3\21\3\21\3\21\5\21\u00a7\n\21\3\22\3\22\3\23\3\23\5\23\u00ad\n\23"+
		"\3\23\3\23\3\23\3\24\3\24\3\24\5\24\u00b5\n\24\3\24\3\24\3\24\5\24\u00ba"+
		"\n\24\3\25\3\25\3\25\3\25\7\25\u00c0\n\25\f\25\16\25\u00c3\13\25\3\26"+
		"\3\26\3\26\5\26\u00c8\n\26\3\27\3\27\3\27\7\27\u00cd\n\27\f\27\16\27\u00d0"+
		"\13\27\3\30\3\30\5\30\u00d4\n\30\3\31\3\31\3\31\3\31\5\31\u00da\n\31\3"+
		"\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\35\3\35\3\36\7\36\u00e7\n\36"+
		"\f\36\16\36\u00ea\13\36\3\36\5\36\u00ed\n\36\3\36\6\36\u00f0\n\36\r\36"+
		"\16\36\u00f1\3\36\7\36\u00f5\n\36\f\36\16\36\u00f8\13\36\3\36\7\36\u00fb"+
		"\n\36\f\36\16\36\u00fe\13\36\3\37\3\37\3 \3 \3 \2\2!\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>\2\7\3\2*+\3\2\r\21\3\2"+
		"\13\f\3\2\65\66\3\29:\2\u0109\2A\3\2\2\2\4F\3\2\2\2\6P\3\2\2\2\bU\3\2"+
		"\2\2\nX\3\2\2\2\f\\\3\2\2\2\16b\3\2\2\2\20d\3\2\2\2\22q\3\2\2\2\24s\3"+
		"\2\2\2\26v\3\2\2\2\30y\3\2\2\2\32\u0096\3\2\2\2\34\u0098\3\2\2\2\36\u00a0"+
		"\3\2\2\2 \u00a6\3\2\2\2\"\u00a8\3\2\2\2$\u00ac\3\2\2\2&\u00b9\3\2\2\2"+
		"(\u00bb\3\2\2\2*\u00c4\3\2\2\2,\u00c9\3\2\2\2.\u00d3\3\2\2\2\60\u00d9"+
		"\3\2\2\2\62\u00db\3\2\2\2\64\u00dd\3\2\2\2\66\u00df\3\2\2\28\u00e1\3\2"+
		"\2\2:\u00e8\3\2\2\2<\u00ff\3\2\2\2>\u0101\3\2\2\2@B\5\4\3\2A@\3\2\2\2"+
		"AB\3\2\2\2BC\3\2\2\2CD\7\2\2\3D\3\3\2\2\2EG\5\6\4\2FE\3\2\2\2GH\3\2\2"+
		"\2HF\3\2\2\2HI\3\2\2\2I\5\3\2\2\2JQ\5\f\7\2KQ\5\26\f\2LQ\5\30\r\2MQ\5"+
		"\b\5\2NQ\5\20\t\2OQ\5\32\16\2PJ\3\2\2\2PK\3\2\2\2PL\3\2\2\2PM\3\2\2\2"+
		"PN\3\2\2\2PO\3\2\2\2Q\7\3\2\2\2RV\5\24\13\2SV\5(\25\2TV\5\16\b\2UR\3\2"+
		"\2\2US\3\2\2\2UT\3\2\2\2V\t\3\2\2\2WY\5\6\4\2XW\3\2\2\2YZ\3\2\2\2ZX\3"+
		"\2\2\2Z[\3\2\2\2[\13\3\2\2\2\\^\7\32\2\2]_\5\n\6\2^]\3\2\2\2^_\3\2\2\2"+
		"_`\3\2\2\2`a\7\33\2\2a\r\3\2\2\2bc\5.\30\2c\17\3\2\2\2df\7\6\2\2eg\5\22"+
		"\n\2fe\3\2\2\2fg\3\2\2\2gh\3\2\2\2hi\5\f\7\2i\21\3\2\2\2jk\5(\25\2kl\7"+
		"\36\2\2lr\3\2\2\2mn\5(\25\2no\7\27\2\2op\5.\30\2pr\3\2\2\2qj\3\2\2\2q"+
		"m\3\2\2\2r\23\3\2\2\2st\5.\30\2tu\t\2\2\2u\25\3\2\2\2vw\7\n\2\2wx\5.\30"+
		"\2x\27\3\2\2\2yz\7\7\2\2z{\5.\30\2{\u0081\5\f\7\2|\177\7\b\2\2}\u0080"+
		"\5\30\r\2~\u0080\5\f\7\2\177}\3\2\2\2\177~\3\2\2\2\u0080\u0082\3\2\2\2"+
		"\u0081|\3\2\2\2\u0081\u0082\3\2\2\2\u0082\31\3\2\2\2\u0083\u0084\7\t\2"+
		"\2\u0084\u0085\5\62\32\2\u0085\u0087\7\30\2\2\u0086\u0088\5\34\17\2\u0087"+
		"\u0086\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008b\7\31"+
		"\2\2\u008a\u008c\5 \21\2\u008b\u008a\3\2\2\2\u008b\u008c\3\2\2\2\u008c"+
		"\u008d\3\2\2\2\u008d\u008e\5\f\7\2\u008e\u0097\3\2\2\2\u008f\u0091\7\30"+
		"\2\2\u0090\u0092\5\34\17\2\u0091\u0090\3\2\2\2\u0091\u0092\3\2\2\2\u0092"+
		"\u0093\3\2\2\2\u0093\u0094\7\31\2\2\u0094\u0095\7\64\2\2\u0095\u0097\5"+
		"\f\7\2\u0096\u0083\3\2\2\2\u0096\u008f\3\2\2\2\u0097\33\3\2\2\2\u0098"+
		"\u009d\5\36\20\2\u0099\u009a\7\37\2\2\u009a\u009c\5\36\20\2\u009b\u0099"+
		"\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e"+
		"\35\3\2\2\2\u009f\u009d\3\2\2\2\u00a0\u00a1\5\62\32\2\u00a1\u00a2\5 \21"+
		"\2\u00a2\37\3\2\2\2\u00a3\u00a7\5$\23\2\u00a4\u00a7\5\"\22\2\u00a5\u00a7"+
		"\5&\24\2\u00a6\u00a3\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a5\3\2\2\2\u00a7"+
		"!\3\2\2\2\u00a8\u00a9\t\3\2\2\u00a9#\3\2\2\2\u00aa\u00ad\5\62\32\2\u00ab"+
		"\u00ad\5\"\22\2\u00ac\u00aa\3\2\2\2\u00ac\u00ab\3\2\2\2\u00ad\u00ae\3"+
		"\2\2\2\u00ae\u00af\7\34\2\2\u00af\u00b0\7\35\2\2\u00b0%\3\2\2\2\u00b1"+
		"\u00ba\5\62\32\2\u00b2\u00b4\7\30\2\2\u00b3\u00b5\5\34\17\2\u00b4\u00b3"+
		"\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b7\7\31\2\2"+
		"\u00b7\u00b8\7\64\2\2\u00b8\u00ba\5 \21\2\u00b9\u00b1\3\2\2\2\u00b9\u00b2"+
		"\3\2\2\2\u00ba\'\3\2\2\2\u00bb\u00bc\t\4\2\2\u00bc\u00c1\5*\26\2\u00bd"+
		"\u00be\7\37\2\2\u00be\u00c0\5*\26\2\u00bf\u00bd\3\2\2\2\u00c0\u00c3\3"+
		"\2\2\2\u00c1\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2)\3\2\2\2\u00c3\u00c1"+
		"\3\2\2\2\u00c4\u00c7\5\62\32\2\u00c5\u00c6\7!\2\2\u00c6\u00c8\5.\30\2"+
		"\u00c7\u00c5\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8+\3\2\2\2\u00c9\u00ce\5"+
		".\30\2\u00ca\u00cb\7\37\2\2\u00cb\u00cd\5.\30\2\u00cc\u00ca\3\2\2\2\u00cd"+
		"\u00d0\3\2\2\2\u00ce\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf-\3\2\2\2"+
		"\u00d0\u00ce\3\2\2\2\u00d1\u00d4\5\62\32\2\u00d2\u00d4\5\60\31\2\u00d3"+
		"\u00d1\3\2\2\2\u00d3\u00d2\3\2\2\2\u00d4/\3\2\2\2\u00d5\u00da\58\35\2"+
		"\u00d6\u00da\5> \2\u00d7\u00da\5\64\33\2\u00d8\u00da\5\66\34\2\u00d9\u00d5"+
		"\3\2\2\2\u00d9\u00d6\3\2\2\2\u00d9\u00d7\3\2\2\2\u00d9\u00d8\3\2\2\2\u00da"+
		"\61\3\2\2\2\u00db\u00dc\78\2\2\u00dc\63\3\2\2\2\u00dd\u00de\t\5\2\2\u00de"+
		"\65\3\2\2\2\u00df\u00e0\7\67\2\2\u00e0\67\3\2\2\2\u00e1\u00e2\7\34\2\2"+
		"\u00e2\u00e3\5:\36\2\u00e3\u00e4\7\35\2\2\u00e49\3\2\2\2\u00e5\u00e7\7"+
		"\37\2\2\u00e6\u00e5\3\2\2\2\u00e7\u00ea\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e8"+
		"\u00e9\3\2\2\2\u00e9\u00ec\3\2\2\2\u00ea\u00e8\3\2\2\2\u00eb\u00ed\5<"+
		"\37\2\u00ec\u00eb\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00f6\3\2\2\2\u00ee"+
		"\u00f0\7\37\2\2\u00ef\u00ee\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00ef\3"+
		"\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f5\5<\37\2\u00f4"+
		"\u00ef\3\2\2\2\u00f5\u00f8\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f6\u00f7\3\2"+
		"\2\2\u00f7\u00fc\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f9\u00fb\7\37\2\2\u00fa"+
		"\u00f9\3\2\2\2\u00fb\u00fe\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fc\u00fd\3\2"+
		"\2\2\u00fd;\3\2\2\2\u00fe\u00fc\3\2\2\2\u00ff\u0100\5.\30\2\u0100=\3\2"+
		"\2\2\u0101\u0102\t\6\2\2\u0102?\3\2\2\2\37AHPUZ^fq\177\u0081\u0087\u008b"+
		"\u0091\u0096\u009d\u00a6\u00ac\u00b4\u00b9\u00c1\u00c7\u00ce\u00d3\u00d9"+
		"\u00e8\u00ec\u00f1\u00f6\u00fc";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}