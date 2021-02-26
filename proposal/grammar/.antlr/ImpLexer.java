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
		RETURN=8, BOOL=9, INT=10, FLOAT=11, CHAR=12, EXPORT=13, IMPORT=14, NEW=15, 
		CLASS=16, ENUM=17, LPAREN=18, RPAREN=19, LBRACE=20, RBRACE=21, LBRACK=22, 
		RBRACK=23, SEMICOLON=24, COMMA=25, DOT=26, ASSIGN=27, GT=28, LT=29, LE=30, 
		GE=31, EQUAL=32, NOTEQUAL=33, TILDE=34, COLON=35, INC=36, DEC=37, ADD=38, 
		SUB=39, MUL=40, DIV=41, BITAND=42, BITOR=43, CARET=44, MOD=45, FATARROW=46, 
		BooleanLiteral=47, DECIMAL_LIT=48, FLOAT_LIT=49;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WhiteSpace", "Comment", "CommentMultiLine", "LOOP", "IF", "ELSE", "FUNCTION", 
			"RETURN", "BOOL", "INT", "FLOAT", "CHAR", "EXPORT", "IMPORT", "NEW", 
			"CLASS", "ENUM", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", 
			"SEMICOLON", "COMMA", "DOT", "ASSIGN", "GT", "LT", "LE", "GE", "EQUAL", 
			"NOTEQUAL", "TILDE", "COLON", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", 
			"BITAND", "BITOR", "CARET", "MOD", "FATARROW", "BooleanLiteral", "DECIMAL_LIT", 
			"FLOAT_LIT", "DECIMALS", "OCTAL_DIGIT", "HEX_DIGIT", "EXPONENT"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\63\u014f\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\3\2\6\2o\n\2\r\2\16\2p\3\2\3\2\3\3\3\3\3\3\3"+
		"\3\7\3y\n\3\f\3\16\3|\13\3\3\3\3\3\3\4\3\4\3\4\3\4\7\4\u0084\n\4\f\4\16"+
		"\4\u0087\13\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7"+
		"\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26"+
		"\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35"+
		"\3\36\3\36\3\37\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3\"\3\"\3#\3#\3$\3$\3"+
		"%\3%\3%\3&\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/"+
		"\3/\3/\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\5\60\u0126\n\60\3"+
		"\61\3\61\7\61\u012a\n\61\f\61\16\61\u012d\13\61\3\62\3\62\3\62\5\62\u0132"+
		"\n\62\3\62\5\62\u0135\n\62\3\62\5\62\u0138\n\62\3\62\3\62\3\62\5\62\u013d"+
		"\n\62\5\62\u013f\n\62\3\63\6\63\u0142\n\63\r\63\16\63\u0143\3\64\3\64"+
		"\3\65\3\65\3\66\3\66\5\66\u014c\n\66\3\66\3\66\3\u0085\2\67\3\3\5\4\7"+
		"\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C"+
		"#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\2g\2i\2k\2\3\2\n\5\2\13\f"+
		"\16\17\"\"\4\2\f\f\17\17\3\2\63;\3\2\62;\3\2\629\5\2\62;CHch\4\2GGgg\4"+
		"\2--//\2\u0156\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3"+
		"\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2"+
		"\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3"+
		"\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2"+
		"\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\2"+
		"9\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3"+
		"\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2"+
		"\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2"+
		"_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\3n\3\2\2\2\5t\3\2\2\2\7\177\3\2\2\2\t"+
		"\u008d\3\2\2\2\13\u0092\3\2\2\2\r\u0095\3\2\2\2\17\u009a\3\2\2\2\21\u00a3"+
		"\3\2\2\2\23\u00aa\3\2\2\2\25\u00af\3\2\2\2\27\u00b3\3\2\2\2\31\u00b9\3"+
		"\2\2\2\33\u00be\3\2\2\2\35\u00c5\3\2\2\2\37\u00cc\3\2\2\2!\u00d0\3\2\2"+
		"\2#\u00d6\3\2\2\2%\u00db\3\2\2\2\'\u00dd\3\2\2\2)\u00df\3\2\2\2+\u00e1"+
		"\3\2\2\2-\u00e3\3\2\2\2/\u00e5\3\2\2\2\61\u00e7\3\2\2\2\63\u00e9\3\2\2"+
		"\2\65\u00eb\3\2\2\2\67\u00ed\3\2\2\29\u00ef\3\2\2\2;\u00f1\3\2\2\2=\u00f3"+
		"\3\2\2\2?\u00f6\3\2\2\2A\u00f9\3\2\2\2C\u00fc\3\2\2\2E\u00ff\3\2\2\2G"+
		"\u0101\3\2\2\2I\u0103\3\2\2\2K\u0106\3\2\2\2M\u0109\3\2\2\2O\u010b\3\2"+
		"\2\2Q\u010d\3\2\2\2S\u010f\3\2\2\2U\u0111\3\2\2\2W\u0113\3\2\2\2Y\u0115"+
		"\3\2\2\2[\u0117\3\2\2\2]\u0119\3\2\2\2_\u0125\3\2\2\2a\u0127\3\2\2\2c"+
		"\u013e\3\2\2\2e\u0141\3\2\2\2g\u0145\3\2\2\2i\u0147\3\2\2\2k\u0149\3\2"+
		"\2\2mo\t\2\2\2nm\3\2\2\2op\3\2\2\2pn\3\2\2\2pq\3\2\2\2qr\3\2\2\2rs\b\2"+
		"\2\2s\4\3\2\2\2tu\7\61\2\2uv\7\61\2\2vz\3\2\2\2wy\n\3\2\2xw\3\2\2\2y|"+
		"\3\2\2\2zx\3\2\2\2z{\3\2\2\2{}\3\2\2\2|z\3\2\2\2}~\b\3\2\2~\6\3\2\2\2"+
		"\177\u0080\7\61\2\2\u0080\u0081\7,\2\2\u0081\u0085\3\2\2\2\u0082\u0084"+
		"\13\2\2\2\u0083\u0082\3\2\2\2\u0084\u0087\3\2\2\2\u0085\u0086\3\2\2\2"+
		"\u0085\u0083\3\2\2\2\u0086\u0088\3\2\2\2\u0087\u0085\3\2\2\2\u0088\u0089"+
		"\7,\2\2\u0089\u008a\7\61\2\2\u008a\u008b\3\2\2\2\u008b\u008c\b\4\2\2\u008c"+
		"\b\3\2\2\2\u008d\u008e\7n\2\2\u008e\u008f\7q\2\2\u008f\u0090\7q\2\2\u0090"+
		"\u0091\7r\2\2\u0091\n\3\2\2\2\u0092\u0093\7k\2\2\u0093\u0094\7h\2\2\u0094"+
		"\f\3\2\2\2\u0095\u0096\7g\2\2\u0096\u0097\7n\2\2\u0097\u0098\7u\2\2\u0098"+
		"\u0099\7g\2\2\u0099\16\3\2\2\2\u009a\u009b\7h\2\2\u009b\u009c\7w\2\2\u009c"+
		"\u009d\7p\2\2\u009d\u009e\7e\2\2\u009e\u009f\7v\2\2\u009f\u00a0\7k\2\2"+
		"\u00a0\u00a1\7q\2\2\u00a1\u00a2\7p\2\2\u00a2\20\3\2\2\2\u00a3\u00a4\7"+
		"t\2\2\u00a4\u00a5\7g\2\2\u00a5\u00a6\7v\2\2\u00a6\u00a7\7w\2\2\u00a7\u00a8"+
		"\7t\2\2\u00a8\u00a9\7p\2\2\u00a9\22\3\2\2\2\u00aa\u00ab\7d\2\2\u00ab\u00ac"+
		"\7q\2\2\u00ac\u00ad\7q\2\2\u00ad\u00ae\7n\2\2\u00ae\24\3\2\2\2\u00af\u00b0"+
		"\7k\2\2\u00b0\u00b1\7p\2\2\u00b1\u00b2\7v\2\2\u00b2\26\3\2\2\2\u00b3\u00b4"+
		"\7h\2\2\u00b4\u00b5\7n\2\2\u00b5\u00b6\7q\2\2\u00b6\u00b7\7c\2\2\u00b7"+
		"\u00b8\7v\2\2\u00b8\30\3\2\2\2\u00b9\u00ba\7e\2\2\u00ba\u00bb\7j\2\2\u00bb"+
		"\u00bc\7c\2\2\u00bc\u00bd\7t\2\2\u00bd\32\3\2\2\2\u00be\u00bf\7g\2\2\u00bf"+
		"\u00c0\7z\2\2\u00c0\u00c1\7r\2\2\u00c1\u00c2\7q\2\2\u00c2\u00c3\7t\2\2"+
		"\u00c3\u00c4\7v\2\2\u00c4\34\3\2\2\2\u00c5\u00c6\7k\2\2\u00c6\u00c7\7"+
		"o\2\2\u00c7\u00c8\7r\2\2\u00c8\u00c9\7q\2\2\u00c9\u00ca\7t\2\2\u00ca\u00cb"+
		"\7v\2\2\u00cb\36\3\2\2\2\u00cc\u00cd\7p\2\2\u00cd\u00ce\7g\2\2\u00ce\u00cf"+
		"\7y\2\2\u00cf \3\2\2\2\u00d0\u00d1\7e\2\2\u00d1\u00d2\7n\2\2\u00d2\u00d3"+
		"\7c\2\2\u00d3\u00d4\7u\2\2\u00d4\u00d5\7u\2\2\u00d5\"\3\2\2\2\u00d6\u00d7"+
		"\7g\2\2\u00d7\u00d8\7p\2\2\u00d8\u00d9\7w\2\2\u00d9\u00da\7o\2\2\u00da"+
		"$\3\2\2\2\u00db\u00dc\7*\2\2\u00dc&\3\2\2\2\u00dd\u00de\7+\2\2\u00de("+
		"\3\2\2\2\u00df\u00e0\7}\2\2\u00e0*\3\2\2\2\u00e1\u00e2\7\177\2\2\u00e2"+
		",\3\2\2\2\u00e3\u00e4\7]\2\2\u00e4.\3\2\2\2\u00e5\u00e6\7_\2\2\u00e6\60"+
		"\3\2\2\2\u00e7\u00e8\7=\2\2\u00e8\62\3\2\2\2\u00e9\u00ea\7.\2\2\u00ea"+
		"\64\3\2\2\2\u00eb\u00ec\7\60\2\2\u00ec\66\3\2\2\2\u00ed\u00ee\7?\2\2\u00ee"+
		"8\3\2\2\2\u00ef\u00f0\7@\2\2\u00f0:\3\2\2\2\u00f1\u00f2\7>\2\2\u00f2<"+
		"\3\2\2\2\u00f3\u00f4\7>\2\2\u00f4\u00f5\7?\2\2\u00f5>\3\2\2\2\u00f6\u00f7"+
		"\7@\2\2\u00f7\u00f8\7?\2\2\u00f8@\3\2\2\2\u00f9\u00fa\7?\2\2\u00fa\u00fb"+
		"\7?\2\2\u00fbB\3\2\2\2\u00fc\u00fd\7#\2\2\u00fd\u00fe\7?\2\2\u00feD\3"+
		"\2\2\2\u00ff\u0100\7\u0080\2\2\u0100F\3\2\2\2\u0101\u0102\7<\2\2\u0102"+
		"H\3\2\2\2\u0103\u0104\7-\2\2\u0104\u0105\7-\2\2\u0105J\3\2\2\2\u0106\u0107"+
		"\7/\2\2\u0107\u0108\7/\2\2\u0108L\3\2\2\2\u0109\u010a\7-\2\2\u010aN\3"+
		"\2\2\2\u010b\u010c\7/\2\2\u010cP\3\2\2\2\u010d\u010e\7,\2\2\u010eR\3\2"+
		"\2\2\u010f\u0110\7\61\2\2\u0110T\3\2\2\2\u0111\u0112\7(\2\2\u0112V\3\2"+
		"\2\2\u0113\u0114\7~\2\2\u0114X\3\2\2\2\u0115\u0116\7`\2\2\u0116Z\3\2\2"+
		"\2\u0117\u0118\7\'\2\2\u0118\\\3\2\2\2\u0119\u011a\7?\2\2\u011a\u011b"+
		"\7@\2\2\u011b^\3\2\2\2\u011c\u011d\7v\2\2\u011d\u011e\7t\2\2\u011e\u011f"+
		"\7w\2\2\u011f\u0126\7g\2\2\u0120\u0121\7h\2\2\u0121\u0122\7c\2\2\u0122"+
		"\u0123\7n\2\2\u0123\u0124\7u\2\2\u0124\u0126\7g\2\2\u0125\u011c\3\2\2"+
		"\2\u0125\u0120\3\2\2\2\u0126`\3\2\2\2\u0127\u012b\t\4\2\2\u0128\u012a"+
		"\t\5\2\2\u0129\u0128\3\2\2\2\u012a\u012d\3\2\2\2\u012b\u0129\3\2\2\2\u012b"+
		"\u012c\3\2\2\2\u012cb\3\2\2\2\u012d\u012b\3\2\2\2\u012e\u0137\5e\63\2"+
		"\u012f\u0131\7\60\2\2\u0130\u0132\5e\63\2\u0131\u0130\3\2\2\2\u0131\u0132"+
		"\3\2\2\2\u0132\u0134\3\2\2\2\u0133\u0135\5k\66\2\u0134\u0133\3\2\2\2\u0134"+
		"\u0135\3\2\2\2\u0135\u0138\3\2\2\2\u0136\u0138\5k\66\2\u0137\u012f\3\2"+
		"\2\2\u0137\u0136\3\2\2\2\u0138\u013f\3\2\2\2\u0139\u013a\7\60\2\2\u013a"+
		"\u013c\5e\63\2\u013b\u013d\5k\66\2\u013c\u013b\3\2\2\2\u013c\u013d\3\2"+
		"\2\2\u013d\u013f\3\2\2\2\u013e\u012e\3\2\2\2\u013e\u0139\3\2\2\2\u013f"+
		"d\3\2\2\2\u0140\u0142\t\5\2\2\u0141\u0140\3\2\2\2\u0142\u0143\3\2\2\2"+
		"\u0143\u0141\3\2\2\2\u0143\u0144\3\2\2\2\u0144f\3\2\2\2\u0145\u0146\t"+
		"\6\2\2\u0146h\3\2\2\2\u0147\u0148\t\7\2\2\u0148j\3\2\2\2\u0149\u014b\t"+
		"\b\2\2\u014a\u014c\t\t\2\2\u014b\u014a\3\2\2\2\u014b\u014c\3\2\2\2\u014c"+
		"\u014d\3\2\2\2\u014d\u014e\5e\63\2\u014el\3\2\2\2\17\2pz\u0085\u0125\u012b"+
		"\u0131\u0134\u0137\u013c\u013e\u0143\u014b\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}