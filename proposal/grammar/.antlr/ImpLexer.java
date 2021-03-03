// Generated from c:\Users\Matt\Documents\github\mh15\imp\proposal\grammar\ImpLexer.g4 by ANTLR 4.8
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ImpLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WhiteSpace", "Comment", "CommentMultiLine", "LOOP", "IF", "ELSE", "FUNCTION", 
			"RETURN", "VAL", "MUT", "BOOL", "INT", "FLOAT", "CHAR", "STRING", "EXPORT", 
			"IMPORT", "NEW", "CLASS", "ENUM", "IN", "LPAREN", "RPAREN", "LBRACE", 
			"RBRACE", "LBRACK", "RBRACK", "SEMICOLON", "COMMA", "DOT", "ASSIGN", 
			"GT", "LT", "LE", "GE", "EQUAL", "NOTEQUAL", "TILDE", "COLON", "INC", 
			"DEC", "ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", "CARET", "MOD", 
			"FATARROW", "BooleanLiteral", "DECIMAL_LIT", "FLOAT_LIT", "IDENTIFIER", 
			"RAW_STRING_LIT", "STRING_LITERAL", "DoubleStringCharacter", "DECIMALS", 
			"OCTAL_DIGIT", "HEX_DIGIT", "EXPONENT", "ALPHA"
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


	public ImpLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ImpLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2:\u0191\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\3\2\6\2\u0081\n\2\r\2\16\2\u0082\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\7\3\u008b\n\3\f\3\16\3\u008e\13\3\3\3\3\3\3\4\3\4\3\4\3\4\7\4\u0096\n"+
		"\4\f\4\16\4\u0099\13\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6"+
		"\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f"+
		"\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17"+
		"\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27"+
		"\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36"+
		"\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3#\3$\3$\3$\3%\3%\3%\3&\3&\3&\3\'"+
		"\3\'\3(\3(\3)\3)\3)\3*\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3"+
		"\61\3\61\3\62\3\62\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3"+
		"\64\3\64\5\64\u014a\n\64\3\65\3\65\7\65\u014e\n\65\f\65\16\65\u0151\13"+
		"\65\3\66\3\66\3\66\5\66\u0156\n\66\3\66\5\66\u0159\n\66\3\66\5\66\u015c"+
		"\n\66\3\66\3\66\3\66\5\66\u0161\n\66\5\66\u0163\n\66\3\67\3\67\3\67\7"+
		"\67\u0168\n\67\f\67\16\67\u016b\13\67\38\38\78\u016f\n8\f8\168\u0172\13"+
		"8\38\38\39\39\79\u0178\n9\f9\169\u017b\139\39\39\3:\3:\3;\6;\u0182\n;"+
		"\r;\16;\u0183\3<\3<\3=\3=\3>\3>\5>\u018c\n>\3>\3>\3?\3?\3\u0097\2@\3\3"+
		"\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!"+
		"A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o9q:s"+
		"\2u\2w\2y\2{\2}\2\3\2\r\5\2\13\f\16\17\"\"\4\2\f\f\17\17\3\2\63;\3\2\62"+
		";\3\2bb\6\2\f\f\17\17$$^^\3\2\629\5\2\62;CHch\4\2GGgg\4\2--//\5\2C\\a"+
		"ac|\2\u019a\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2"+
		"\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2"+
		"\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2"+
		"\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2"+
		"\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S"+
		"\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2"+
		"\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2"+
		"\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\3\u0080\3\2\2\2\5\u0086\3\2\2\2\7\u0091"+
		"\3\2\2\2\t\u009f\3\2\2\2\13\u00a4\3\2\2\2\r\u00a7\3\2\2\2\17\u00ac\3\2"+
		"\2\2\21\u00b5\3\2\2\2\23\u00bc\3\2\2\2\25\u00c0\3\2\2\2\27\u00c4\3\2\2"+
		"\2\31\u00c9\3\2\2\2\33\u00cd\3\2\2\2\35\u00d3\3\2\2\2\37\u00d8\3\2\2\2"+
		"!\u00df\3\2\2\2#\u00e6\3\2\2\2%\u00ed\3\2\2\2\'\u00f1\3\2\2\2)\u00f7\3"+
		"\2\2\2+\u00fc\3\2\2\2-\u00ff\3\2\2\2/\u0101\3\2\2\2\61\u0103\3\2\2\2\63"+
		"\u0105\3\2\2\2\65\u0107\3\2\2\2\67\u0109\3\2\2\29\u010b\3\2\2\2;\u010d"+
		"\3\2\2\2=\u010f\3\2\2\2?\u0111\3\2\2\2A\u0113\3\2\2\2C\u0115\3\2\2\2E"+
		"\u0117\3\2\2\2G\u011a\3\2\2\2I\u011d\3\2\2\2K\u0120\3\2\2\2M\u0123\3\2"+
		"\2\2O\u0125\3\2\2\2Q\u0127\3\2\2\2S\u012a\3\2\2\2U\u012d\3\2\2\2W\u012f"+
		"\3\2\2\2Y\u0131\3\2\2\2[\u0133\3\2\2\2]\u0135\3\2\2\2_\u0137\3\2\2\2a"+
		"\u0139\3\2\2\2c\u013b\3\2\2\2e\u013d\3\2\2\2g\u0149\3\2\2\2i\u014b\3\2"+
		"\2\2k\u0162\3\2\2\2m\u0164\3\2\2\2o\u016c\3\2\2\2q\u0175\3\2\2\2s\u017e"+
		"\3\2\2\2u\u0181\3\2\2\2w\u0185\3\2\2\2y\u0187\3\2\2\2{\u0189\3\2\2\2}"+
		"\u018f\3\2\2\2\177\u0081\t\2\2\2\u0080\177\3\2\2\2\u0081\u0082\3\2\2\2"+
		"\u0082\u0080\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0085"+
		"\b\2\2\2\u0085\4\3\2\2\2\u0086\u0087\7\61\2\2\u0087\u0088\7\61\2\2\u0088"+
		"\u008c\3\2\2\2\u0089\u008b\n\3\2\2\u008a\u0089\3\2\2\2\u008b\u008e\3\2"+
		"\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008f\3\2\2\2\u008e"+
		"\u008c\3\2\2\2\u008f\u0090\b\3\2\2\u0090\6\3\2\2\2\u0091\u0092\7\61\2"+
		"\2\u0092\u0093\7,\2\2\u0093\u0097\3\2\2\2\u0094\u0096\13\2\2\2\u0095\u0094"+
		"\3\2\2\2\u0096\u0099\3\2\2\2\u0097\u0098\3\2\2\2\u0097\u0095\3\2\2\2\u0098"+
		"\u009a\3\2\2\2\u0099\u0097\3\2\2\2\u009a\u009b\7,\2\2\u009b\u009c\7\61"+
		"\2\2\u009c\u009d\3\2\2\2\u009d\u009e\b\4\2\2\u009e\b\3\2\2\2\u009f\u00a0"+
		"\7n\2\2\u00a0\u00a1\7q\2\2\u00a1\u00a2\7q\2\2\u00a2\u00a3\7r\2\2\u00a3"+
		"\n\3\2\2\2\u00a4\u00a5\7k\2\2\u00a5\u00a6\7h\2\2\u00a6\f\3\2\2\2\u00a7"+
		"\u00a8\7g\2\2\u00a8\u00a9\7n\2\2\u00a9\u00aa\7u\2\2\u00aa\u00ab\7g\2\2"+
		"\u00ab\16\3\2\2\2\u00ac\u00ad\7h\2\2\u00ad\u00ae\7w\2\2\u00ae\u00af\7"+
		"p\2\2\u00af\u00b0\7e\2\2\u00b0\u00b1\7v\2\2\u00b1\u00b2\7k\2\2\u00b2\u00b3"+
		"\7q\2\2\u00b3\u00b4\7p\2\2\u00b4\20\3\2\2\2\u00b5\u00b6\7t\2\2\u00b6\u00b7"+
		"\7g\2\2\u00b7\u00b8\7v\2\2\u00b8\u00b9\7w\2\2\u00b9\u00ba\7t\2\2\u00ba"+
		"\u00bb\7p\2\2\u00bb\22\3\2\2\2\u00bc\u00bd\7x\2\2\u00bd\u00be\7c\2\2\u00be"+
		"\u00bf\7n\2\2\u00bf\24\3\2\2\2\u00c0\u00c1\7o\2\2\u00c1\u00c2\7w\2\2\u00c2"+
		"\u00c3\7v\2\2\u00c3\26\3\2\2\2\u00c4\u00c5\7d\2\2\u00c5\u00c6\7q\2\2\u00c6"+
		"\u00c7\7q\2\2\u00c7\u00c8\7n\2\2\u00c8\30\3\2\2\2\u00c9\u00ca\7k\2\2\u00ca"+
		"\u00cb\7p\2\2\u00cb\u00cc\7v\2\2\u00cc\32\3\2\2\2\u00cd\u00ce\7h\2\2\u00ce"+
		"\u00cf\7n\2\2\u00cf\u00d0\7q\2\2\u00d0\u00d1\7c\2\2\u00d1\u00d2\7v\2\2"+
		"\u00d2\34\3\2\2\2\u00d3\u00d4\7e\2\2\u00d4\u00d5\7j\2\2\u00d5\u00d6\7"+
		"c\2\2\u00d6\u00d7\7t\2\2\u00d7\36\3\2\2\2\u00d8\u00d9\7u\2\2\u00d9\u00da"+
		"\7v\2\2\u00da\u00db\7t\2\2\u00db\u00dc\7k\2\2\u00dc\u00dd\7p\2\2\u00dd"+
		"\u00de\7i\2\2\u00de \3\2\2\2\u00df\u00e0\7g\2\2\u00e0\u00e1\7z\2\2\u00e1"+
		"\u00e2\7r\2\2\u00e2\u00e3\7q\2\2\u00e3\u00e4\7t\2\2\u00e4\u00e5\7v\2\2"+
		"\u00e5\"\3\2\2\2\u00e6\u00e7\7k\2\2\u00e7\u00e8\7o\2\2\u00e8\u00e9\7r"+
		"\2\2\u00e9\u00ea\7q\2\2\u00ea\u00eb\7t\2\2\u00eb\u00ec\7v\2\2\u00ec$\3"+
		"\2\2\2\u00ed\u00ee\7p\2\2\u00ee\u00ef\7g\2\2\u00ef\u00f0\7y\2\2\u00f0"+
		"&\3\2\2\2\u00f1\u00f2\7e\2\2\u00f2\u00f3\7n\2\2\u00f3\u00f4\7c\2\2\u00f4"+
		"\u00f5\7u\2\2\u00f5\u00f6\7u\2\2\u00f6(\3\2\2\2\u00f7\u00f8\7g\2\2\u00f8"+
		"\u00f9\7p\2\2\u00f9\u00fa\7w\2\2\u00fa\u00fb\7o\2\2\u00fb*\3\2\2\2\u00fc"+
		"\u00fd\7k\2\2\u00fd\u00fe\7p\2\2\u00fe,\3\2\2\2\u00ff\u0100\7*\2\2\u0100"+
		".\3\2\2\2\u0101\u0102\7+\2\2\u0102\60\3\2\2\2\u0103\u0104\7}\2\2\u0104"+
		"\62\3\2\2\2\u0105\u0106\7\177\2\2\u0106\64\3\2\2\2\u0107\u0108\7]\2\2"+
		"\u0108\66\3\2\2\2\u0109\u010a\7_\2\2\u010a8\3\2\2\2\u010b\u010c\7=\2\2"+
		"\u010c:\3\2\2\2\u010d\u010e\7.\2\2\u010e<\3\2\2\2\u010f\u0110\7\60\2\2"+
		"\u0110>\3\2\2\2\u0111\u0112\7?\2\2\u0112@\3\2\2\2\u0113\u0114\7@\2\2\u0114"+
		"B\3\2\2\2\u0115\u0116\7>\2\2\u0116D\3\2\2\2\u0117\u0118\7>\2\2\u0118\u0119"+
		"\7?\2\2\u0119F\3\2\2\2\u011a\u011b\7@\2\2\u011b\u011c\7?\2\2\u011cH\3"+
		"\2\2\2\u011d\u011e\7?\2\2\u011e\u011f\7?\2\2\u011fJ\3\2\2\2\u0120\u0121"+
		"\7#\2\2\u0121\u0122\7?\2\2\u0122L\3\2\2\2\u0123\u0124\7\u0080\2\2\u0124"+
		"N\3\2\2\2\u0125\u0126\7<\2\2\u0126P\3\2\2\2\u0127\u0128\7-\2\2\u0128\u0129"+
		"\7-\2\2\u0129R\3\2\2\2\u012a\u012b\7/\2\2\u012b\u012c\7/\2\2\u012cT\3"+
		"\2\2\2\u012d\u012e\7-\2\2\u012eV\3\2\2\2\u012f\u0130\7/\2\2\u0130X\3\2"+
		"\2\2\u0131\u0132\7,\2\2\u0132Z\3\2\2\2\u0133\u0134\7\61\2\2\u0134\\\3"+
		"\2\2\2\u0135\u0136\7(\2\2\u0136^\3\2\2\2\u0137\u0138\7~\2\2\u0138`\3\2"+
		"\2\2\u0139\u013a\7`\2\2\u013ab\3\2\2\2\u013b\u013c\7\'\2\2\u013cd\3\2"+
		"\2\2\u013d\u013e\7?\2\2\u013e\u013f\7@\2\2\u013ff\3\2\2\2\u0140\u0141"+
		"\7v\2\2\u0141\u0142\7t\2\2\u0142\u0143\7w\2\2\u0143\u014a\7g\2\2\u0144"+
		"\u0145\7h\2\2\u0145\u0146\7c\2\2\u0146\u0147\7n\2\2\u0147\u0148\7u\2\2"+
		"\u0148\u014a\7g\2\2\u0149\u0140\3\2\2\2\u0149\u0144\3\2\2\2\u014ah\3\2"+
		"\2\2\u014b\u014f\t\4\2\2\u014c\u014e\t\5\2\2\u014d\u014c\3\2\2\2\u014e"+
		"\u0151\3\2\2\2\u014f\u014d\3\2\2\2\u014f\u0150\3\2\2\2\u0150j\3\2\2\2"+
		"\u0151\u014f\3\2\2\2\u0152\u015b\5u;\2\u0153\u0155\7\60\2\2\u0154\u0156"+
		"\5u;\2\u0155\u0154\3\2\2\2\u0155\u0156\3\2\2\2\u0156\u0158\3\2\2\2\u0157"+
		"\u0159\5{>\2\u0158\u0157\3\2\2\2\u0158\u0159\3\2\2\2\u0159\u015c\3\2\2"+
		"\2\u015a\u015c\5{>\2\u015b\u0153\3\2\2\2\u015b\u015a\3\2\2\2\u015c\u0163"+
		"\3\2\2\2\u015d\u015e\7\60\2\2\u015e\u0160\5u;\2\u015f\u0161\5{>\2\u0160"+
		"\u015f\3\2\2\2\u0160\u0161\3\2\2\2\u0161\u0163\3\2\2\2\u0162\u0152\3\2"+
		"\2\2\u0162\u015d\3\2\2\2\u0163l\3\2\2\2\u0164\u0169\5}?\2\u0165\u0168"+
		"\5}?\2\u0166\u0168\5u;\2\u0167\u0165\3\2\2\2\u0167\u0166\3\2\2\2\u0168"+
		"\u016b\3\2\2\2\u0169\u0167\3\2\2\2\u0169\u016a\3\2\2\2\u016an\3\2\2\2"+
		"\u016b\u0169\3\2\2\2\u016c\u0170\7b\2\2\u016d\u016f\n\6\2\2\u016e\u016d"+
		"\3\2\2\2\u016f\u0172\3\2\2\2\u0170\u016e\3\2\2\2\u0170\u0171\3\2\2\2\u0171"+
		"\u0173\3\2\2\2\u0172\u0170\3\2\2\2\u0173\u0174\7b\2\2\u0174p\3\2\2\2\u0175"+
		"\u0179\7$\2\2\u0176\u0178\5s:\2\u0177\u0176\3\2\2\2\u0178\u017b\3\2\2"+
		"\2\u0179\u0177\3\2\2\2\u0179\u017a\3\2\2\2\u017a\u017c\3\2\2\2\u017b\u0179"+
		"\3\2\2\2\u017c\u017d\7$\2\2\u017dr\3\2\2\2\u017e\u017f\n\7\2\2\u017ft"+
		"\3\2\2\2\u0180\u0182\t\5\2\2\u0181\u0180\3\2\2\2\u0182\u0183\3\2\2\2\u0183"+
		"\u0181\3\2\2\2\u0183\u0184\3\2\2\2\u0184v\3\2\2\2\u0185\u0186\t\b\2\2"+
		"\u0186x\3\2\2\2\u0187\u0188\t\t\2\2\u0188z\3\2\2\2\u0189\u018b\t\n\2\2"+
		"\u018a\u018c\t\13\2\2\u018b\u018a\3\2\2\2\u018b\u018c\3\2\2\2\u018c\u018d"+
		"\3\2\2\2\u018d\u018e\5u;\2\u018e|\3\2\2\2\u018f\u0190\t\f\2\2\u0190~\3"+
		"\2\2\2\23\2\u0082\u008c\u0097\u0149\u014f\u0155\u0158\u015b\u0160\u0162"+
		"\u0167\u0169\u0170\u0179\u0183\u018b\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}