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
		RETURN=8, BOOL=9, INT=10, FLOAT=11, CHAR=12, EXPORT=13, IMPORT=14, NEW=15, 
		CLASS=16, ENUM=17, LPAREN=18, RPAREN=19, LBRACE=20, RBRACE=21, LBRACK=22, 
		RBRACK=23, SEMICOLON=24, COMMA=25, DOT=26, ASSIGN=27, GT=28, LT=29, LE=30, 
		GE=31, EQUAL=32, NOTEQUAL=33, TILDE=34, COLON=35, INC=36, DEC=37, ADD=38, 
		SUB=39, MUL=40, DIV=41, BITAND=42, BITOR=43, CARET=44, MOD=45, FATARROW=46, 
		BooleanLiteral=47, DECIMAL_LIT=48, FLOAT_LIT=49;
	public static final int
		RULE_statement = 0, RULE_statementList = 1, RULE_block = 2, RULE_returnStatement = 3, 
		RULE_arrayLiteral = 4, RULE_elementList = 5, RULE_arrayElement = 6;
	private static String[] makeRuleNames() {
		return new String[] {
			"statement", "statementList", "block", "returnStatement", "arrayLiteral", 
			"elementList", "arrayElement"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'loop'", "'if'", "'else'", "'function'", "'return'", 
			"'bool'", "'int'", "'float'", "'char'", "'export'", "'import'", "'new'", 
			"'class'", "'enum'", "'('", "')'", "'{'", "'}'", "'['", "']'", "';'", 
			"','", "'.'", "'='", "'>'", "'<'", "'<='", "'>='", "'=='", "'!='", "'~'", 
			"':'", "'++'", "'--'", "'+'", "'-'", "'*'", "'/'", "'&'", "'|'", "'^'", 
			"'%'", "'=>'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WhiteSpace", "Comment", "CommentMultiLine", "LOOP", "IF", "ELSE", 
			"FUNCTION", "RETURN", "BOOL", "INT", "FLOAT", "CHAR", "EXPORT", "IMPORT", 
			"NEW", "CLASS", "ENUM", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", 
			"RBRACK", "SEMICOLON", "COMMA", "DOT", "ASSIGN", "GT", "LT", "LE", "GE", 
			"EQUAL", "NOTEQUAL", "TILDE", "COLON", "INC", "DEC", "ADD", "SUB", "MUL", 
			"DIV", "BITAND", "BITOR", "CARET", "MOD", "FATARROW", "BooleanLiteral", 
			"DECIMAL_LIT", "FLOAT_LIT"
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

	public static class StatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(14);
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
		enterRule(_localctx, 2, RULE_statementList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(17); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(16);
				statement();
				}
				}
				setState(19); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==LBRACE );
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
		enterRule(_localctx, 4, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			match(LBRACE);
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACE) {
				{
				setState(22);
				statementList();
				}
			}

			setState(25);
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

	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(ImpParser.RETURN, 0); }
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			match(RETURN);
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

	public static class ArrayLiteralContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(ImpParser.LBRACK, 0); }
		public ElementListContext elementList() {
			return getRuleContext(ElementListContext.class,0);
		}
		public TerminalNode RBRACK() { return getToken(ImpParser.RBRACK, 0); }
		public ArrayLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayLiteral; }
	}

	public final ArrayLiteralContext arrayLiteral() throws RecognitionException {
		ArrayLiteralContext _localctx = new ArrayLiteralContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_arrayLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(29);
			match(LBRACK);
			setState(30);
			elementList();
			setState(31);
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
		public List<ArrayElementContext> arrayElement() {
			return getRuleContexts(ArrayElementContext.class);
		}
		public ArrayElementContext arrayElement(int i) {
			return getRuleContext(ArrayElementContext.class,i);
		}
		public ElementListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementList; }
	}

	public final ElementListContext elementList() throws RecognitionException {
		ElementListContext _localctx = new ElementListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_elementList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(33);
					match(COMMA);
					}
					} 
				}
				setState(38);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			setState(40);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(39);
				arrayElement();
				}
				break;
			}
			setState(50);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(43); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(42);
							match(COMMA);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(45); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(47);
					arrayElement();
					}
					} 
				}
				setState(52);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(53);
				match(COMMA);
				}
				}
				setState(58);
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

	public static class ArrayElementContext extends ParserRuleContext {
		public TerminalNode COMMA() { return getToken(ImpParser.COMMA, 0); }
		public ArrayElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayElement; }
	}

	public final ArrayElementContext arrayElement() throws RecognitionException {
		ArrayElementContext _localctx = new ArrayElementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_arrayElement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			match(COMMA);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\63@\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3\2\3\3\6\3\24\n\3\r\3"+
		"\16\3\25\3\4\3\4\5\4\32\n\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\7\7\7%\n"+
		"\7\f\7\16\7(\13\7\3\7\5\7+\n\7\3\7\6\7.\n\7\r\7\16\7/\3\7\7\7\63\n\7\f"+
		"\7\16\7\66\13\7\3\7\7\79\n\7\f\7\16\7<\13\7\3\b\3\b\3\b\2\2\t\2\4\6\b"+
		"\n\f\16\2\2\2?\2\20\3\2\2\2\4\23\3\2\2\2\6\27\3\2\2\2\b\35\3\2\2\2\n\37"+
		"\3\2\2\2\f&\3\2\2\2\16=\3\2\2\2\20\21\5\6\4\2\21\3\3\2\2\2\22\24\5\2\2"+
		"\2\23\22\3\2\2\2\24\25\3\2\2\2\25\23\3\2\2\2\25\26\3\2\2\2\26\5\3\2\2"+
		"\2\27\31\7\26\2\2\30\32\5\4\3\2\31\30\3\2\2\2\31\32\3\2\2\2\32\33\3\2"+
		"\2\2\33\34\7\27\2\2\34\7\3\2\2\2\35\36\7\n\2\2\36\t\3\2\2\2\37 \7\30\2"+
		"\2 !\5\f\7\2!\"\7\31\2\2\"\13\3\2\2\2#%\7\33\2\2$#\3\2\2\2%(\3\2\2\2&"+
		"$\3\2\2\2&\'\3\2\2\2\'*\3\2\2\2(&\3\2\2\2)+\5\16\b\2*)\3\2\2\2*+\3\2\2"+
		"\2+\64\3\2\2\2,.\7\33\2\2-,\3\2\2\2./\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60"+
		"\61\3\2\2\2\61\63\5\16\b\2\62-\3\2\2\2\63\66\3\2\2\2\64\62\3\2\2\2\64"+
		"\65\3\2\2\2\65:\3\2\2\2\66\64\3\2\2\2\679\7\33\2\28\67\3\2\2\29<\3\2\2"+
		"\2:8\3\2\2\2:;\3\2\2\2;\r\3\2\2\2<:\3\2\2\2=>\7\33\2\2>\17\3\2\2\2\t\25"+
		"\31&*/\64:";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}