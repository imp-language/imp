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
		CARET=48, MOD=49, FATARROW=50, BooleanLiteral=51, DECIMAL_LIT=52, FLOAT_LIT=53;
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
			"FATARROW", "BooleanLiteral", "DECIMAL_LIT", "FLOAT_LIT", "DECIMALS", 
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
			"MOD", "FATARROW", "BooleanLiteral", "DECIMAL_LIT", "FLOAT_LIT"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\67\u016d\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\3\2\6\2y\n"+
		"\2\r\2\16\2z\3\2\3\2\3\3\3\3\3\3\3\3\7\3\u0083\n\3\f\3\16\3\u0086\13\3"+
		"\3\3\3\3\3\4\3\4\3\4\3\4\7\4\u008e\n\4\f\4\16\4\u0091\13\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n"+
		"\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25"+
		"\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33"+
		"\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3"+
		"#\3#\3$\3$\3$\3%\3%\3%\3&\3&\3&\3\'\3\'\3(\3(\3)\3)\3)\3*\3*\3*\3+\3+"+
		"\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\63"+
		"\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\5\64\u0142\n\64\3\65\3\65"+
		"\7\65\u0146\n\65\f\65\16\65\u0149\13\65\3\66\3\66\3\66\5\66\u014e\n\66"+
		"\3\66\5\66\u0151\n\66\3\66\5\66\u0154\n\66\3\66\3\66\3\66\5\66\u0159\n"+
		"\66\5\66\u015b\n\66\3\67\6\67\u015e\n\67\r\67\16\67\u015f\38\38\39\39"+
		"\3:\3:\5:\u0168\n:\3:\3:\3;\3;\3\u008f\2<\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+"+
		"\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+"+
		"U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m\2o\2q\2s\2u\2\3\2\13\5\2\13"+
		"\f\16\17\"\"\4\2\f\f\17\17\3\2\63;\3\2\62;\3\2\629\5\2\62;CHch\4\2GGg"+
		"g\4\2--//\5\2C\\aac|\2\u0173\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3"+
		"\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2"+
		"\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37"+
		"\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3"+
		"\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2"+
		"\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C"+
		"\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2"+
		"\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2"+
		"\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i"+
		"\3\2\2\2\2k\3\2\2\2\3x\3\2\2\2\5~\3\2\2\2\7\u0089\3\2\2\2\t\u0097\3\2"+
		"\2\2\13\u009c\3\2\2\2\r\u009f\3\2\2\2\17\u00a4\3\2\2\2\21\u00ad\3\2\2"+
		"\2\23\u00b4\3\2\2\2\25\u00b8\3\2\2\2\27\u00bc\3\2\2\2\31\u00c1\3\2\2\2"+
		"\33\u00c5\3\2\2\2\35\u00cb\3\2\2\2\37\u00d0\3\2\2\2!\u00d7\3\2\2\2#\u00de"+
		"\3\2\2\2%\u00e5\3\2\2\2\'\u00e9\3\2\2\2)\u00ef\3\2\2\2+\u00f4\3\2\2\2"+
		"-\u00f7\3\2\2\2/\u00f9\3\2\2\2\61\u00fb\3\2\2\2\63\u00fd\3\2\2\2\65\u00ff"+
		"\3\2\2\2\67\u0101\3\2\2\29\u0103\3\2\2\2;\u0105\3\2\2\2=\u0107\3\2\2\2"+
		"?\u0109\3\2\2\2A\u010b\3\2\2\2C\u010d\3\2\2\2E\u010f\3\2\2\2G\u0112\3"+
		"\2\2\2I\u0115\3\2\2\2K\u0118\3\2\2\2M\u011b\3\2\2\2O\u011d\3\2\2\2Q\u011f"+
		"\3\2\2\2S\u0122\3\2\2\2U\u0125\3\2\2\2W\u0127\3\2\2\2Y\u0129\3\2\2\2["+
		"\u012b\3\2\2\2]\u012d\3\2\2\2_\u012f\3\2\2\2a\u0131\3\2\2\2c\u0133\3\2"+
		"\2\2e\u0135\3\2\2\2g\u0141\3\2\2\2i\u0143\3\2\2\2k\u015a\3\2\2\2m\u015d"+
		"\3\2\2\2o\u0161\3\2\2\2q\u0163\3\2\2\2s\u0165\3\2\2\2u\u016b\3\2\2\2w"+
		"y\t\2\2\2xw\3\2\2\2yz\3\2\2\2zx\3\2\2\2z{\3\2\2\2{|\3\2\2\2|}\b\2\2\2"+
		"}\4\3\2\2\2~\177\7\61\2\2\177\u0080\7\61\2\2\u0080\u0084\3\2\2\2\u0081"+
		"\u0083\n\3\2\2\u0082\u0081\3\2\2\2\u0083\u0086\3\2\2\2\u0084\u0082\3\2"+
		"\2\2\u0084\u0085\3\2\2\2\u0085\u0087\3\2\2\2\u0086\u0084\3\2\2\2\u0087"+
		"\u0088\b\3\2\2\u0088\6\3\2\2\2\u0089\u008a\7\61\2\2\u008a\u008b\7,\2\2"+
		"\u008b\u008f\3\2\2\2\u008c\u008e\13\2\2\2\u008d\u008c\3\2\2\2\u008e\u0091"+
		"\3\2\2\2\u008f\u0090\3\2\2\2\u008f\u008d\3\2\2\2\u0090\u0092\3\2\2\2\u0091"+
		"\u008f\3\2\2\2\u0092\u0093\7,\2\2\u0093\u0094\7\61\2\2\u0094\u0095\3\2"+
		"\2\2\u0095\u0096\b\4\2\2\u0096\b\3\2\2\2\u0097\u0098\7n\2\2\u0098\u0099"+
		"\7q\2\2\u0099\u009a\7q\2\2\u009a\u009b\7r\2\2\u009b\n\3\2\2\2\u009c\u009d"+
		"\7k\2\2\u009d\u009e\7h\2\2\u009e\f\3\2\2\2\u009f\u00a0\7g\2\2\u00a0\u00a1"+
		"\7n\2\2\u00a1\u00a2\7u\2\2\u00a2\u00a3\7g\2\2\u00a3\16\3\2\2\2\u00a4\u00a5"+
		"\7h\2\2\u00a5\u00a6\7w\2\2\u00a6\u00a7\7p\2\2\u00a7\u00a8\7e\2\2\u00a8"+
		"\u00a9\7v\2\2\u00a9\u00aa\7k\2\2\u00aa\u00ab\7q\2\2\u00ab\u00ac\7p\2\2"+
		"\u00ac\20\3\2\2\2\u00ad\u00ae\7t\2\2\u00ae\u00af\7g\2\2\u00af\u00b0\7"+
		"v\2\2\u00b0\u00b1\7w\2\2\u00b1\u00b2\7t\2\2\u00b2\u00b3\7p\2\2\u00b3\22"+
		"\3\2\2\2\u00b4\u00b5\7x\2\2\u00b5\u00b6\7c\2\2\u00b6\u00b7\7n\2\2\u00b7"+
		"\24\3\2\2\2\u00b8\u00b9\7o\2\2\u00b9\u00ba\7w\2\2\u00ba\u00bb\7v\2\2\u00bb"+
		"\26\3\2\2\2\u00bc\u00bd\7d\2\2\u00bd\u00be\7q\2\2\u00be\u00bf\7q\2\2\u00bf"+
		"\u00c0\7n\2\2\u00c0\30\3\2\2\2\u00c1\u00c2\7k\2\2\u00c2\u00c3\7p\2\2\u00c3"+
		"\u00c4\7v\2\2\u00c4\32\3\2\2\2\u00c5\u00c6\7h\2\2\u00c6\u00c7\7n\2\2\u00c7"+
		"\u00c8\7q\2\2\u00c8\u00c9\7c\2\2\u00c9\u00ca\7v\2\2\u00ca\34\3\2\2\2\u00cb"+
		"\u00cc\7e\2\2\u00cc\u00cd\7j\2\2\u00cd\u00ce\7c\2\2\u00ce\u00cf\7t\2\2"+
		"\u00cf\36\3\2\2\2\u00d0\u00d1\7u\2\2\u00d1\u00d2\7v\2\2\u00d2\u00d3\7"+
		"t\2\2\u00d3\u00d4\7k\2\2\u00d4\u00d5\7p\2\2\u00d5\u00d6\7i\2\2\u00d6 "+
		"\3\2\2\2\u00d7\u00d8\7g\2\2\u00d8\u00d9\7z\2\2\u00d9\u00da\7r\2\2\u00da"+
		"\u00db\7q\2\2\u00db\u00dc\7t\2\2\u00dc\u00dd\7v\2\2\u00dd\"\3\2\2\2\u00de"+
		"\u00df\7k\2\2\u00df\u00e0\7o\2\2\u00e0\u00e1\7r\2\2\u00e1\u00e2\7q\2\2"+
		"\u00e2\u00e3\7t\2\2\u00e3\u00e4\7v\2\2\u00e4$\3\2\2\2\u00e5\u00e6\7p\2"+
		"\2\u00e6\u00e7\7g\2\2\u00e7\u00e8\7y\2\2\u00e8&\3\2\2\2\u00e9\u00ea\7"+
		"e\2\2\u00ea\u00eb\7n\2\2\u00eb\u00ec\7c\2\2\u00ec\u00ed\7u\2\2\u00ed\u00ee"+
		"\7u\2\2\u00ee(\3\2\2\2\u00ef\u00f0\7g\2\2\u00f0\u00f1\7p\2\2\u00f1\u00f2"+
		"\7w\2\2\u00f2\u00f3\7o\2\2\u00f3*\3\2\2\2\u00f4\u00f5\7k\2\2\u00f5\u00f6"+
		"\7p\2\2\u00f6,\3\2\2\2\u00f7\u00f8\7*\2\2\u00f8.\3\2\2\2\u00f9\u00fa\7"+
		"+\2\2\u00fa\60\3\2\2\2\u00fb\u00fc\7}\2\2\u00fc\62\3\2\2\2\u00fd\u00fe"+
		"\7\177\2\2\u00fe\64\3\2\2\2\u00ff\u0100\7]\2\2\u0100\66\3\2\2\2\u0101"+
		"\u0102\7_\2\2\u01028\3\2\2\2\u0103\u0104\7=\2\2\u0104:\3\2\2\2\u0105\u0106"+
		"\7.\2\2\u0106<\3\2\2\2\u0107\u0108\7\60\2\2\u0108>\3\2\2\2\u0109\u010a"+
		"\7?\2\2\u010a@\3\2\2\2\u010b\u010c\7@\2\2\u010cB\3\2\2\2\u010d\u010e\7"+
		">\2\2\u010eD\3\2\2\2\u010f\u0110\7>\2\2\u0110\u0111\7?\2\2\u0111F\3\2"+
		"\2\2\u0112\u0113\7@\2\2\u0113\u0114\7?\2\2\u0114H\3\2\2\2\u0115\u0116"+
		"\7?\2\2\u0116\u0117\7?\2\2\u0117J\3\2\2\2\u0118\u0119\7#\2\2\u0119\u011a"+
		"\7?\2\2\u011aL\3\2\2\2\u011b\u011c\7\u0080\2\2\u011cN\3\2\2\2\u011d\u011e"+
		"\7<\2\2\u011eP\3\2\2\2\u011f\u0120\7-\2\2\u0120\u0121\7-\2\2\u0121R\3"+
		"\2\2\2\u0122\u0123\7/\2\2\u0123\u0124\7/\2\2\u0124T\3\2\2\2\u0125\u0126"+
		"\7-\2\2\u0126V\3\2\2\2\u0127\u0128\7/\2\2\u0128X\3\2\2\2\u0129\u012a\7"+
		",\2\2\u012aZ\3\2\2\2\u012b\u012c\7\61\2\2\u012c\\\3\2\2\2\u012d\u012e"+
		"\7(\2\2\u012e^\3\2\2\2\u012f\u0130\7~\2\2\u0130`\3\2\2\2\u0131\u0132\7"+
		"`\2\2\u0132b\3\2\2\2\u0133\u0134\7\'\2\2\u0134d\3\2\2\2\u0135\u0136\7"+
		"?\2\2\u0136\u0137\7@\2\2\u0137f\3\2\2\2\u0138\u0139\7v\2\2\u0139\u013a"+
		"\7t\2\2\u013a\u013b\7w\2\2\u013b\u0142\7g\2\2\u013c\u013d\7h\2\2\u013d"+
		"\u013e\7c\2\2\u013e\u013f\7n\2\2\u013f\u0140\7u\2\2\u0140\u0142\7g\2\2"+
		"\u0141\u0138\3\2\2\2\u0141\u013c\3\2\2\2\u0142h\3\2\2\2\u0143\u0147\t"+
		"\4\2\2\u0144\u0146\t\5\2\2\u0145\u0144\3\2\2\2\u0146\u0149\3\2\2\2\u0147"+
		"\u0145\3\2\2\2\u0147\u0148\3\2\2\2\u0148j\3\2\2\2\u0149\u0147\3\2\2\2"+
		"\u014a\u0153\5m\67\2\u014b\u014d\7\60\2\2\u014c\u014e\5m\67\2\u014d\u014c"+
		"\3\2\2\2\u014d\u014e\3\2\2\2\u014e\u0150\3\2\2\2\u014f\u0151\5s:\2\u0150"+
		"\u014f\3\2\2\2\u0150\u0151\3\2\2\2\u0151\u0154\3\2\2\2\u0152\u0154\5s"+
		":\2\u0153\u014b\3\2\2\2\u0153\u0152\3\2\2\2\u0154\u015b\3\2\2\2\u0155"+
		"\u0156\7\60\2\2\u0156\u0158\5m\67\2\u0157\u0159\5s:\2\u0158\u0157\3\2"+
		"\2\2\u0158\u0159\3\2\2\2\u0159\u015b\3\2\2\2\u015a\u014a\3\2\2\2\u015a"+
		"\u0155\3\2\2\2\u015bl\3\2\2\2\u015c\u015e\t\5\2\2\u015d\u015c\3\2\2\2"+
		"\u015e\u015f\3\2\2\2\u015f\u015d\3\2\2\2\u015f\u0160\3\2\2\2\u0160n\3"+
		"\2\2\2\u0161\u0162\t\6\2\2\u0162p\3\2\2\2\u0163\u0164\t\7\2\2\u0164r\3"+
		"\2\2\2\u0165\u0167\t\b\2\2\u0166\u0168\t\t\2\2\u0167\u0166\3\2\2\2\u0167"+
		"\u0168\3\2\2\2\u0168\u0169\3\2\2\2\u0169\u016a\5m\67\2\u016at\3\2\2\2"+
		"\u016b\u016c\t\n\2\2\u016cv\3\2\2\2\17\2z\u0084\u008f\u0141\u0147\u014d"+
		"\u0150\u0153\u0158\u015a\u015f\u0167\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}