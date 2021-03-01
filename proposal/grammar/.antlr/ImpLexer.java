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
		IDENTIFIER=54;
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
			"DECIMALS", "OCTAL_DIGIT", "HEX_DIGIT", "EXPONENT", "ALPHA"
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
			"MOD", "FATARROW", "BooleanLiteral", "DECIMAL_LIT", "FLOAT_LIT", "IDENTIFIER"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\28\u0177\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\3\2\6"+
		"\2{\n\2\r\2\16\2|\3\2\3\2\3\3\3\3\3\3\3\3\7\3\u0085\n\3\f\3\16\3\u0088"+
		"\13\3\3\3\3\3\3\4\3\4\3\4\3\4\7\4\u0090\n\4\f\4\16\4\u0093\13\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3"+
		"\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3"+
		"\25\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3"+
		"\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\""+
		"\3\"\3#\3#\3#\3$\3$\3$\3%\3%\3%\3&\3&\3&\3\'\3\'\3(\3(\3)\3)\3)\3*\3*"+
		"\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3"+
		"\63\3\63\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\5\64\u0144\n\64"+
		"\3\65\3\65\7\65\u0148\n\65\f\65\16\65\u014b\13\65\3\66\3\66\3\66\5\66"+
		"\u0150\n\66\3\66\5\66\u0153\n\66\3\66\5\66\u0156\n\66\3\66\3\66\3\66\5"+
		"\66\u015b\n\66\5\66\u015d\n\66\3\67\3\67\3\67\7\67\u0162\n\67\f\67\16"+
		"\67\u0165\13\67\38\68\u0168\n8\r8\168\u0169\39\39\3:\3:\3;\3;\5;\u0172"+
		"\n;\3;\3;\3<\3<\3\u0091\2=\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32"+
		"\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a"+
		"\62c\63e\64g\65i\66k\67m8o\2q\2s\2u\2w\2\3\2\13\5\2\13\f\16\17\"\"\4\2"+
		"\f\f\17\17\3\2\63;\3\2\62;\3\2\629\5\2\62;CHch\4\2GGgg\4\2--//\5\2C\\"+
		"aac|\2\u017f\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2"+
		"\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2"+
		"\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3"+
		"\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2"+
		"\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2"+
		"S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3"+
		"\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2"+
		"\2\2m\3\2\2\2\3z\3\2\2\2\5\u0080\3\2\2\2\7\u008b\3\2\2\2\t\u0099\3\2\2"+
		"\2\13\u009e\3\2\2\2\r\u00a1\3\2\2\2\17\u00a6\3\2\2\2\21\u00af\3\2\2\2"+
		"\23\u00b6\3\2\2\2\25\u00ba\3\2\2\2\27\u00be\3\2\2\2\31\u00c3\3\2\2\2\33"+
		"\u00c7\3\2\2\2\35\u00cd\3\2\2\2\37\u00d2\3\2\2\2!\u00d9\3\2\2\2#\u00e0"+
		"\3\2\2\2%\u00e7\3\2\2\2\'\u00eb\3\2\2\2)\u00f1\3\2\2\2+\u00f6\3\2\2\2"+
		"-\u00f9\3\2\2\2/\u00fb\3\2\2\2\61\u00fd\3\2\2\2\63\u00ff\3\2\2\2\65\u0101"+
		"\3\2\2\2\67\u0103\3\2\2\29\u0105\3\2\2\2;\u0107\3\2\2\2=\u0109\3\2\2\2"+
		"?\u010b\3\2\2\2A\u010d\3\2\2\2C\u010f\3\2\2\2E\u0111\3\2\2\2G\u0114\3"+
		"\2\2\2I\u0117\3\2\2\2K\u011a\3\2\2\2M\u011d\3\2\2\2O\u011f\3\2\2\2Q\u0121"+
		"\3\2\2\2S\u0124\3\2\2\2U\u0127\3\2\2\2W\u0129\3\2\2\2Y\u012b\3\2\2\2["+
		"\u012d\3\2\2\2]\u012f\3\2\2\2_\u0131\3\2\2\2a\u0133\3\2\2\2c\u0135\3\2"+
		"\2\2e\u0137\3\2\2\2g\u0143\3\2\2\2i\u0145\3\2\2\2k\u015c\3\2\2\2m\u015e"+
		"\3\2\2\2o\u0167\3\2\2\2q\u016b\3\2\2\2s\u016d\3\2\2\2u\u016f\3\2\2\2w"+
		"\u0175\3\2\2\2y{\t\2\2\2zy\3\2\2\2{|\3\2\2\2|z\3\2\2\2|}\3\2\2\2}~\3\2"+
		"\2\2~\177\b\2\2\2\177\4\3\2\2\2\u0080\u0081\7\61\2\2\u0081\u0082\7\61"+
		"\2\2\u0082\u0086\3\2\2\2\u0083\u0085\n\3\2\2\u0084\u0083\3\2\2\2\u0085"+
		"\u0088\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0089\3\2"+
		"\2\2\u0088\u0086\3\2\2\2\u0089\u008a\b\3\2\2\u008a\6\3\2\2\2\u008b\u008c"+
		"\7\61\2\2\u008c\u008d\7,\2\2\u008d\u0091\3\2\2\2\u008e\u0090\13\2\2\2"+
		"\u008f\u008e\3\2\2\2\u0090\u0093\3\2\2\2\u0091\u0092\3\2\2\2\u0091\u008f"+
		"\3\2\2\2\u0092\u0094\3\2\2\2\u0093\u0091\3\2\2\2\u0094\u0095\7,\2\2\u0095"+
		"\u0096\7\61\2\2\u0096\u0097\3\2\2\2\u0097\u0098\b\4\2\2\u0098\b\3\2\2"+
		"\2\u0099\u009a\7n\2\2\u009a\u009b\7q\2\2\u009b\u009c\7q\2\2\u009c\u009d"+
		"\7r\2\2\u009d\n\3\2\2\2\u009e\u009f\7k\2\2\u009f\u00a0\7h\2\2\u00a0\f"+
		"\3\2\2\2\u00a1\u00a2\7g\2\2\u00a2\u00a3\7n\2\2\u00a3\u00a4\7u\2\2\u00a4"+
		"\u00a5\7g\2\2\u00a5\16\3\2\2\2\u00a6\u00a7\7h\2\2\u00a7\u00a8\7w\2\2\u00a8"+
		"\u00a9\7p\2\2\u00a9\u00aa\7e\2\2\u00aa\u00ab\7v\2\2\u00ab\u00ac\7k\2\2"+
		"\u00ac\u00ad\7q\2\2\u00ad\u00ae\7p\2\2\u00ae\20\3\2\2\2\u00af\u00b0\7"+
		"t\2\2\u00b0\u00b1\7g\2\2\u00b1\u00b2\7v\2\2\u00b2\u00b3\7w\2\2\u00b3\u00b4"+
		"\7t\2\2\u00b4\u00b5\7p\2\2\u00b5\22\3\2\2\2\u00b6\u00b7\7x\2\2\u00b7\u00b8"+
		"\7c\2\2\u00b8\u00b9\7n\2\2\u00b9\24\3\2\2\2\u00ba\u00bb\7o\2\2\u00bb\u00bc"+
		"\7w\2\2\u00bc\u00bd\7v\2\2\u00bd\26\3\2\2\2\u00be\u00bf\7d\2\2\u00bf\u00c0"+
		"\7q\2\2\u00c0\u00c1\7q\2\2\u00c1\u00c2\7n\2\2\u00c2\30\3\2\2\2\u00c3\u00c4"+
		"\7k\2\2\u00c4\u00c5\7p\2\2\u00c5\u00c6\7v\2\2\u00c6\32\3\2\2\2\u00c7\u00c8"+
		"\7h\2\2\u00c8\u00c9\7n\2\2\u00c9\u00ca\7q\2\2\u00ca\u00cb\7c\2\2\u00cb"+
		"\u00cc\7v\2\2\u00cc\34\3\2\2\2\u00cd\u00ce\7e\2\2\u00ce\u00cf\7j\2\2\u00cf"+
		"\u00d0\7c\2\2\u00d0\u00d1\7t\2\2\u00d1\36\3\2\2\2\u00d2\u00d3\7u\2\2\u00d3"+
		"\u00d4\7v\2\2\u00d4\u00d5\7t\2\2\u00d5\u00d6\7k\2\2\u00d6\u00d7\7p\2\2"+
		"\u00d7\u00d8\7i\2\2\u00d8 \3\2\2\2\u00d9\u00da\7g\2\2\u00da\u00db\7z\2"+
		"\2\u00db\u00dc\7r\2\2\u00dc\u00dd\7q\2\2\u00dd\u00de\7t\2\2\u00de\u00df"+
		"\7v\2\2\u00df\"\3\2\2\2\u00e0\u00e1\7k\2\2\u00e1\u00e2\7o\2\2\u00e2\u00e3"+
		"\7r\2\2\u00e3\u00e4\7q\2\2\u00e4\u00e5\7t\2\2\u00e5\u00e6\7v\2\2\u00e6"+
		"$\3\2\2\2\u00e7\u00e8\7p\2\2\u00e8\u00e9\7g\2\2\u00e9\u00ea\7y\2\2\u00ea"+
		"&\3\2\2\2\u00eb\u00ec\7e\2\2\u00ec\u00ed\7n\2\2\u00ed\u00ee\7c\2\2\u00ee"+
		"\u00ef\7u\2\2\u00ef\u00f0\7u\2\2\u00f0(\3\2\2\2\u00f1\u00f2\7g\2\2\u00f2"+
		"\u00f3\7p\2\2\u00f3\u00f4\7w\2\2\u00f4\u00f5\7o\2\2\u00f5*\3\2\2\2\u00f6"+
		"\u00f7\7k\2\2\u00f7\u00f8\7p\2\2\u00f8,\3\2\2\2\u00f9\u00fa\7*\2\2\u00fa"+
		".\3\2\2\2\u00fb\u00fc\7+\2\2\u00fc\60\3\2\2\2\u00fd\u00fe\7}\2\2\u00fe"+
		"\62\3\2\2\2\u00ff\u0100\7\177\2\2\u0100\64\3\2\2\2\u0101\u0102\7]\2\2"+
		"\u0102\66\3\2\2\2\u0103\u0104\7_\2\2\u01048\3\2\2\2\u0105\u0106\7=\2\2"+
		"\u0106:\3\2\2\2\u0107\u0108\7.\2\2\u0108<\3\2\2\2\u0109\u010a\7\60\2\2"+
		"\u010a>\3\2\2\2\u010b\u010c\7?\2\2\u010c@\3\2\2\2\u010d\u010e\7@\2\2\u010e"+
		"B\3\2\2\2\u010f\u0110\7>\2\2\u0110D\3\2\2\2\u0111\u0112\7>\2\2\u0112\u0113"+
		"\7?\2\2\u0113F\3\2\2\2\u0114\u0115\7@\2\2\u0115\u0116\7?\2\2\u0116H\3"+
		"\2\2\2\u0117\u0118\7?\2\2\u0118\u0119\7?\2\2\u0119J\3\2\2\2\u011a\u011b"+
		"\7#\2\2\u011b\u011c\7?\2\2\u011cL\3\2\2\2\u011d\u011e\7\u0080\2\2\u011e"+
		"N\3\2\2\2\u011f\u0120\7<\2\2\u0120P\3\2\2\2\u0121\u0122\7-\2\2\u0122\u0123"+
		"\7-\2\2\u0123R\3\2\2\2\u0124\u0125\7/\2\2\u0125\u0126\7/\2\2\u0126T\3"+
		"\2\2\2\u0127\u0128\7-\2\2\u0128V\3\2\2\2\u0129\u012a\7/\2\2\u012aX\3\2"+
		"\2\2\u012b\u012c\7,\2\2\u012cZ\3\2\2\2\u012d\u012e\7\61\2\2\u012e\\\3"+
		"\2\2\2\u012f\u0130\7(\2\2\u0130^\3\2\2\2\u0131\u0132\7~\2\2\u0132`\3\2"+
		"\2\2\u0133\u0134\7`\2\2\u0134b\3\2\2\2\u0135\u0136\7\'\2\2\u0136d\3\2"+
		"\2\2\u0137\u0138\7?\2\2\u0138\u0139\7@\2\2\u0139f\3\2\2\2\u013a\u013b"+
		"\7v\2\2\u013b\u013c\7t\2\2\u013c\u013d\7w\2\2\u013d\u0144\7g\2\2\u013e"+
		"\u013f\7h\2\2\u013f\u0140\7c\2\2\u0140\u0141\7n\2\2\u0141\u0142\7u\2\2"+
		"\u0142\u0144\7g\2\2\u0143\u013a\3\2\2\2\u0143\u013e\3\2\2\2\u0144h\3\2"+
		"\2\2\u0145\u0149\t\4\2\2\u0146\u0148\t\5\2\2\u0147\u0146\3\2\2\2\u0148"+
		"\u014b\3\2\2\2\u0149\u0147\3\2\2\2\u0149\u014a\3\2\2\2\u014aj\3\2\2\2"+
		"\u014b\u0149\3\2\2\2\u014c\u0155\5o8\2\u014d\u014f\7\60\2\2\u014e\u0150"+
		"\5o8\2\u014f\u014e\3\2\2\2\u014f\u0150\3\2\2\2\u0150\u0152\3\2\2\2\u0151"+
		"\u0153\5u;\2\u0152\u0151\3\2\2\2\u0152\u0153\3\2\2\2\u0153\u0156\3\2\2"+
		"\2\u0154\u0156\5u;\2\u0155\u014d\3\2\2\2\u0155\u0154\3\2\2\2\u0156\u015d"+
		"\3\2\2\2\u0157\u0158\7\60\2\2\u0158\u015a\5o8\2\u0159\u015b\5u;\2\u015a"+
		"\u0159\3\2\2\2\u015a\u015b\3\2\2\2\u015b\u015d\3\2\2\2\u015c\u014c\3\2"+
		"\2\2\u015c\u0157\3\2\2\2\u015dl\3\2\2\2\u015e\u0163\5w<\2\u015f\u0162"+
		"\5w<\2\u0160\u0162\5o8\2\u0161\u015f\3\2\2\2\u0161\u0160\3\2\2\2\u0162"+
		"\u0165\3\2\2\2\u0163\u0161\3\2\2\2\u0163\u0164\3\2\2\2\u0164n\3\2\2\2"+
		"\u0165\u0163\3\2\2\2\u0166\u0168\t\5\2\2\u0167\u0166\3\2\2\2\u0168\u0169"+
		"\3\2\2\2\u0169\u0167\3\2\2\2\u0169\u016a\3\2\2\2\u016ap\3\2\2\2\u016b"+
		"\u016c\t\6\2\2\u016cr\3\2\2\2\u016d\u016e\t\7\2\2\u016et\3\2\2\2\u016f"+
		"\u0171\t\b\2\2\u0170\u0172\t\t\2\2\u0171\u0170\3\2\2\2\u0171\u0172\3\2"+
		"\2\2\u0172\u0173\3\2\2\2\u0173\u0174\5o8\2\u0174v\3\2\2\2\u0175\u0176"+
		"\t\n\2\2\u0176x\3\2\2\2\21\2|\u0086\u0091\u0143\u0149\u014f\u0152\u0155"+
		"\u015a\u015c\u0161\u0163\u0169\u0171\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}