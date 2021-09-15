// Generated from C:/Users/Matt/Documents/github/mh15/imp/src/main/antlr4/org/imp/jvm\ImpLexer.g4 by ANTLR 4.9.1
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
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		EOL=1, WhiteSpace=2, Comment=3, CommentMultiLine=4, LOOP=5, IF=6, ELSE=7, 
		FUNCTION=8, RETURN=9, VAL=10, MUT=11, BOOL=12, INT=13, FLOAT=14, CHAR=15, 
		STRING=16, VOID=17, EXPORT=18, IMPORT=19, FROM=20, AS=21, NEW=22, CLASS=23, 
		INTERFACE=24, ENUM=25, PUBLIC=26, STRUCT=27, IN=28, LPAREN=29, RPAREN=30, 
		LBRACE=31, RBRACE=32, LBRACK=33, RBRACK=34, SEMICOLON=35, COMMA=36, DOT=37, 
		ASSIGN=38, GT=39, LT=40, LE=41, GE=42, EQUAL=43, NOTEQUAL=44, TILDE=45, 
		COLON=46, BANG=47, NOT=48, AND=49, OR=50, INC=51, DEC=52, ADD=53, SUB=54, 
		MUL=55, DIV=56, BITAND=57, BITOR=58, POW=59, MOD=60, FATARROW=61, BooleanLiteral=62, 
		IDENTIFIER=63, DECIMAL_LIT=64, FLOAT_LIT=65, DOUBLE_LIT=66, DOUBLE_SUFFIX=67, 
		RAW_STRING_LIT=68, STRING_LITERAL=69;
	public static final int
		WHITESPACE_CHANNEL=2;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN", "WHITESPACE_CHANNEL"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"EOL", "WhiteSpace", "Comment", "CommentMultiLine", "LOOP", "IF", "ELSE", 
			"FUNCTION", "RETURN", "VAL", "MUT", "BOOL", "INT", "FLOAT", "CHAR", "STRING", 
			"VOID", "EXPORT", "IMPORT", "FROM", "AS", "NEW", "CLASS", "INTERFACE", 
			"ENUM", "PUBLIC", "STRUCT", "IN", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
			"LBRACK", "RBRACK", "SEMICOLON", "COMMA", "DOT", "ASSIGN", "GT", "LT", 
			"LE", "GE", "EQUAL", "NOTEQUAL", "TILDE", "COLON", "BANG", "NOT", "AND", 
			"OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", "POW", 
			"MOD", "FATARROW", "BooleanLiteral", "IDENTIFIER", "DECIMAL_LIT", "FLOAT_LIT", 
			"DOUBLE_LIT", "DOUBLE_SUFFIX", "RAW_STRING_LIT", "STRING_LITERAL", "DECIMALS", 
			"OCTAL_DIGIT", "HEX_DIGIT", "EXPONENT", "ALPHA"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "'loop'", "'if'", "'else'", null, "'return'", 
			"'val'", "'mut'", "'bool'", "'int'", "'float'", "'char'", "'string'", 
			"'void'", "'export'", "'import'", "'from'", "'as'", "'new'", "'class'", 
			"'interface'", "'enum'", "'public'", "'struct'", "'in'", "'('", "')'", 
			"'{'", "'}'", "'['", "']'", "';'", "','", "'.'", "'='", "'>'", "'<'", 
			"'<='", "'>='", "'=='", "'!='", "'~'", "':'", "'!'", "'not'", null, null, 
			"'++'", "'--'", "'+'", "'-'", "'*'", "'/'", "'&'", "'|'", "'^'", "'%'", 
			"'=>'", null, null, null, null, null, "'d'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "EOL", "WhiteSpace", "Comment", "CommentMultiLine", "LOOP", "IF", 
			"ELSE", "FUNCTION", "RETURN", "VAL", "MUT", "BOOL", "INT", "FLOAT", "CHAR", 
			"STRING", "VOID", "EXPORT", "IMPORT", "FROM", "AS", "NEW", "CLASS", "INTERFACE", 
			"ENUM", "PUBLIC", "STRUCT", "IN", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
			"LBRACK", "RBRACK", "SEMICOLON", "COMMA", "DOT", "ASSIGN", "GT", "LT", 
			"LE", "GE", "EQUAL", "NOTEQUAL", "TILDE", "COLON", "BANG", "NOT", "AND", 
			"OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", "POW", 
			"MOD", "FATARROW", "BooleanLiteral", "IDENTIFIER", "DECIMAL_LIT", "FLOAT_LIT", 
			"DOUBLE_LIT", "DOUBLE_SUFFIX", "RAW_STRING_LIT", "STRING_LITERAL"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2G\u0209\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\3\2\5\2\u0099\n\2\3\2\3\2\3\3\6\3\u009e\n\3\r\3\16\3\u009f"+
		"\3\3\3\3\3\4\3\4\3\4\3\4\7\4\u00a8\n\4\f\4\16\4\u00ab\13\4\3\4\3\4\3\5"+
		"\3\5\3\5\3\5\7\5\u00b3\n\5\f\5\16\5\u00b6\13\5\3\5\3\5\3\5\3\5\3\5\3\6"+
		"\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00d6\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25"+
		"\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32"+
		"\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\35\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$"+
		"\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3*\3+\3+\3+\3,\3,\3,\3-\3-\3"+
		"-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\62\3\62\5"+
		"\62\u017a\n\62\3\63\3\63\3\63\3\63\5\63\u0180\n\63\3\64\3\64\3\64\3\65"+
		"\3\65\3\65\3\66\3\66\3\67\3\67\38\38\39\39\3:\3:\3;\3;\3<\3<\3=\3=\3>"+
		"\3>\3>\3?\3?\3?\3?\3?\3?\3?\3?\3?\5?\u01a4\n?\3@\3@\3@\7@\u01a9\n@\f@"+
		"\16@\u01ac\13@\3A\3A\7A\u01b0\nA\fA\16A\u01b3\13A\3A\5A\u01b6\nA\3B\3"+
		"B\3B\5B\u01bb\nB\3B\5B\u01be\nB\3B\5B\u01c1\nB\3B\3B\3B\5B\u01c6\nB\5"+
		"B\u01c8\nB\3C\3C\3C\5C\u01cd\nC\3C\5C\u01d0\nC\3C\5C\u01d3\nC\3C\3C\3"+
		"C\3C\3C\5C\u01da\nC\3C\3C\3C\3C\3C\5C\u01e1\nC\3D\3D\3E\3E\7E\u01e7\n"+
		"E\fE\16E\u01ea\13E\3E\3E\3F\3F\3F\3F\7F\u01f2\nF\fF\16F\u01f5\13F\3F\3"+
		"F\3G\6G\u01fa\nG\rG\16G\u01fb\3H\3H\3I\3I\3J\3J\5J\u0204\nJ\3J\3J\3K\3"+
		"K\3\u00b4\2L\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16"+
		"\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34"+
		"\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g"+
		"\65i\66k\67m8o9q:s;u<w=y>{?}@\177A\u0081B\u0083C\u0085D\u0087E\u0089F"+
		"\u008bG\u008d\2\u008f\2\u0091\2\u0093\2\u0095\2\3\2\16\5\2\13\13\16\16"+
		"\"\"\4\2\f\f\17\17\3\2\63;\3\2\62;\3\2bb\n\2$$))^^ddhhppttvv\6\2\f\f\17"+
		"\17$$^^\3\2\629\5\2\62;CHch\4\2GGgg\4\2--//\5\2C\\aac|\2\u021f\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2"+
		"\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3"+
		"\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3"+
		"\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2"+
		"=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3"+
		"\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2"+
		"\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2"+
		"c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3"+
		"\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2"+
		"\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3"+
		"\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2\2\3\u0098\3\2\2\2"+
		"\5\u009d\3\2\2\2\7\u00a3\3\2\2\2\t\u00ae\3\2\2\2\13\u00bc\3\2\2\2\r\u00c1"+
		"\3\2\2\2\17\u00c4\3\2\2\2\21\u00d5\3\2\2\2\23\u00d7\3\2\2\2\25\u00de\3"+
		"\2\2\2\27\u00e2\3\2\2\2\31\u00e6\3\2\2\2\33\u00eb\3\2\2\2\35\u00ef\3\2"+
		"\2\2\37\u00f5\3\2\2\2!\u00fa\3\2\2\2#\u0101\3\2\2\2%\u0106\3\2\2\2\'\u010d"+
		"\3\2\2\2)\u0114\3\2\2\2+\u0119\3\2\2\2-\u011c\3\2\2\2/\u0120\3\2\2\2\61"+
		"\u0126\3\2\2\2\63\u0130\3\2\2\2\65\u0135\3\2\2\2\67\u013c\3\2\2\29\u0143"+
		"\3\2\2\2;\u0146\3\2\2\2=\u0148\3\2\2\2?\u014a\3\2\2\2A\u014c\3\2\2\2C"+
		"\u014e\3\2\2\2E\u0150\3\2\2\2G\u0152\3\2\2\2I\u0154\3\2\2\2K\u0156\3\2"+
		"\2\2M\u0158\3\2\2\2O\u015a\3\2\2\2Q\u015c\3\2\2\2S\u015e\3\2\2\2U\u0161"+
		"\3\2\2\2W\u0164\3\2\2\2Y\u0167\3\2\2\2[\u016a\3\2\2\2]\u016c\3\2\2\2_"+
		"\u016e\3\2\2\2a\u0170\3\2\2\2c\u0179\3\2\2\2e\u017f\3\2\2\2g\u0181\3\2"+
		"\2\2i\u0184\3\2\2\2k\u0187\3\2\2\2m\u0189\3\2\2\2o\u018b\3\2\2\2q\u018d"+
		"\3\2\2\2s\u018f\3\2\2\2u\u0191\3\2\2\2w\u0193\3\2\2\2y\u0195\3\2\2\2{"+
		"\u0197\3\2\2\2}\u01a3\3\2\2\2\177\u01a5\3\2\2\2\u0081\u01b5\3\2\2\2\u0083"+
		"\u01c7\3\2\2\2\u0085\u01e0\3\2\2\2\u0087\u01e2\3\2\2\2\u0089\u01e4\3\2"+
		"\2\2\u008b\u01ed\3\2\2\2\u008d\u01f9\3\2\2\2\u008f\u01fd\3\2\2\2\u0091"+
		"\u01ff\3\2\2\2\u0093\u0201\3\2\2\2\u0095\u0207\3\2\2\2\u0097\u0099\7\17"+
		"\2\2\u0098\u0097\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u009a\3\2\2\2\u009a"+
		"\u009b\7\f\2\2\u009b\4\3\2\2\2\u009c\u009e\t\2\2\2\u009d\u009c\3\2\2\2"+
		"\u009e\u009f\3\2\2\2\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a1"+
		"\3\2\2\2\u00a1\u00a2\b\3\2\2\u00a2\6\3\2\2\2\u00a3\u00a4\7\61\2\2\u00a4"+
		"\u00a5\7\61\2\2\u00a5\u00a9\3\2\2\2\u00a6\u00a8\n\3\2\2\u00a7\u00a6\3"+
		"\2\2\2\u00a8\u00ab\3\2\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa"+
		"\u00ac\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ac\u00ad\b\4\2\2\u00ad\b\3\2\2\2"+
		"\u00ae\u00af\7\61\2\2\u00af\u00b0\7,\2\2\u00b0\u00b4\3\2\2\2\u00b1\u00b3"+
		"\13\2\2\2\u00b2\u00b1\3\2\2\2\u00b3\u00b6\3\2\2\2\u00b4\u00b5\3\2\2\2"+
		"\u00b4\u00b2\3\2\2\2\u00b5\u00b7\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b7\u00b8"+
		"\7,\2\2\u00b8\u00b9\7\61\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bb\b\5\2\2\u00bb"+
		"\n\3\2\2\2\u00bc\u00bd\7n\2\2\u00bd\u00be\7q\2\2\u00be\u00bf\7q\2\2\u00bf"+
		"\u00c0\7r\2\2\u00c0\f\3\2\2\2\u00c1\u00c2\7k\2\2\u00c2\u00c3\7h\2\2\u00c3"+
		"\16\3\2\2\2\u00c4\u00c5\7g\2\2\u00c5\u00c6\7n\2\2\u00c6\u00c7\7u\2\2\u00c7"+
		"\u00c8\7g\2\2\u00c8\20\3\2\2\2\u00c9\u00ca\7h\2\2\u00ca\u00cb\7w\2\2\u00cb"+
		"\u00cc\7p\2\2\u00cc\u00cd\7e\2\2\u00cd\u00ce\7v\2\2\u00ce\u00cf\7k\2\2"+
		"\u00cf\u00d0\7q\2\2\u00d0\u00d6\7p\2\2\u00d1\u00d2\7h\2\2\u00d2\u00d3"+
		"\7w\2\2\u00d3\u00d4\7p\2\2\u00d4\u00d6\7e\2\2\u00d5\u00c9\3\2\2\2\u00d5"+
		"\u00d1\3\2\2\2\u00d6\22\3\2\2\2\u00d7\u00d8\7t\2\2\u00d8\u00d9\7g\2\2"+
		"\u00d9\u00da\7v\2\2\u00da\u00db\7w\2\2\u00db\u00dc\7t\2\2\u00dc\u00dd"+
		"\7p\2\2\u00dd\24\3\2\2\2\u00de\u00df\7x\2\2\u00df\u00e0\7c\2\2\u00e0\u00e1"+
		"\7n\2\2\u00e1\26\3\2\2\2\u00e2\u00e3\7o\2\2\u00e3\u00e4\7w\2\2\u00e4\u00e5"+
		"\7v\2\2\u00e5\30\3\2\2\2\u00e6\u00e7\7d\2\2\u00e7\u00e8\7q\2\2\u00e8\u00e9"+
		"\7q\2\2\u00e9\u00ea\7n\2\2\u00ea\32\3\2\2\2\u00eb\u00ec\7k\2\2\u00ec\u00ed"+
		"\7p\2\2\u00ed\u00ee\7v\2\2\u00ee\34\3\2\2\2\u00ef\u00f0\7h\2\2\u00f0\u00f1"+
		"\7n\2\2\u00f1\u00f2\7q\2\2\u00f2\u00f3\7c\2\2\u00f3\u00f4\7v\2\2\u00f4"+
		"\36\3\2\2\2\u00f5\u00f6\7e\2\2\u00f6\u00f7\7j\2\2\u00f7\u00f8\7c\2\2\u00f8"+
		"\u00f9\7t\2\2\u00f9 \3\2\2\2\u00fa\u00fb\7u\2\2\u00fb\u00fc\7v\2\2\u00fc"+
		"\u00fd\7t\2\2\u00fd\u00fe\7k\2\2\u00fe\u00ff\7p\2\2\u00ff\u0100\7i\2\2"+
		"\u0100\"\3\2\2\2\u0101\u0102\7x\2\2\u0102\u0103\7q\2\2\u0103\u0104\7k"+
		"\2\2\u0104\u0105\7f\2\2\u0105$\3\2\2\2\u0106\u0107\7g\2\2\u0107\u0108"+
		"\7z\2\2\u0108\u0109\7r\2\2\u0109\u010a\7q\2\2\u010a\u010b\7t\2\2\u010b"+
		"\u010c\7v\2\2\u010c&\3\2\2\2\u010d\u010e\7k\2\2\u010e\u010f\7o\2\2\u010f"+
		"\u0110\7r\2\2\u0110\u0111\7q\2\2\u0111\u0112\7t\2\2\u0112\u0113\7v\2\2"+
		"\u0113(\3\2\2\2\u0114\u0115\7h\2\2\u0115\u0116\7t\2\2\u0116\u0117\7q\2"+
		"\2\u0117\u0118\7o\2\2\u0118*\3\2\2\2\u0119\u011a\7c\2\2\u011a\u011b\7"+
		"u\2\2\u011b,\3\2\2\2\u011c\u011d\7p\2\2\u011d\u011e\7g\2\2\u011e\u011f"+
		"\7y\2\2\u011f.\3\2\2\2\u0120\u0121\7e\2\2\u0121\u0122\7n\2\2\u0122\u0123"+
		"\7c\2\2\u0123\u0124\7u\2\2\u0124\u0125\7u\2\2\u0125\60\3\2\2\2\u0126\u0127"+
		"\7k\2\2\u0127\u0128\7p\2\2\u0128\u0129\7v\2\2\u0129\u012a\7g\2\2\u012a"+
		"\u012b\7t\2\2\u012b\u012c\7h\2\2\u012c\u012d\7c\2\2\u012d\u012e\7e\2\2"+
		"\u012e\u012f\7g\2\2\u012f\62\3\2\2\2\u0130\u0131\7g\2\2\u0131\u0132\7"+
		"p\2\2\u0132\u0133\7w\2\2\u0133\u0134\7o\2\2\u0134\64\3\2\2\2\u0135\u0136"+
		"\7r\2\2\u0136\u0137\7w\2\2\u0137\u0138\7d\2\2\u0138\u0139\7n\2\2\u0139"+
		"\u013a\7k\2\2\u013a\u013b\7e\2\2\u013b\66\3\2\2\2\u013c\u013d\7u\2\2\u013d"+
		"\u013e\7v\2\2\u013e\u013f\7t\2\2\u013f\u0140\7w\2\2\u0140\u0141\7e\2\2"+
		"\u0141\u0142\7v\2\2\u01428\3\2\2\2\u0143\u0144\7k\2\2\u0144\u0145\7p\2"+
		"\2\u0145:\3\2\2\2\u0146\u0147\7*\2\2\u0147<\3\2\2\2\u0148\u0149\7+\2\2"+
		"\u0149>\3\2\2\2\u014a\u014b\7}\2\2\u014b@\3\2\2\2\u014c\u014d\7\177\2"+
		"\2\u014dB\3\2\2\2\u014e\u014f\7]\2\2\u014fD\3\2\2\2\u0150\u0151\7_\2\2"+
		"\u0151F\3\2\2\2\u0152\u0153\7=\2\2\u0153H\3\2\2\2\u0154\u0155\7.\2\2\u0155"+
		"J\3\2\2\2\u0156\u0157\7\60\2\2\u0157L\3\2\2\2\u0158\u0159\7?\2\2\u0159"+
		"N\3\2\2\2\u015a\u015b\7@\2\2\u015bP\3\2\2\2\u015c\u015d\7>\2\2\u015dR"+
		"\3\2\2\2\u015e\u015f\7>\2\2\u015f\u0160\7?\2\2\u0160T\3\2\2\2\u0161\u0162"+
		"\7@\2\2\u0162\u0163\7?\2\2\u0163V\3\2\2\2\u0164\u0165\7?\2\2\u0165\u0166"+
		"\7?\2\2\u0166X\3\2\2\2\u0167\u0168\7#\2\2\u0168\u0169\7?\2\2\u0169Z\3"+
		"\2\2\2\u016a\u016b\7\u0080\2\2\u016b\\\3\2\2\2\u016c\u016d\7<\2\2\u016d"+
		"^\3\2\2\2\u016e\u016f\7#\2\2\u016f`\3\2\2\2\u0170\u0171\7p\2\2\u0171\u0172"+
		"\7q\2\2\u0172\u0173\7v\2\2\u0173b\3\2\2\2\u0174\u0175\7(\2\2\u0175\u017a"+
		"\7(\2\2\u0176\u0177\7c\2\2\u0177\u0178\7p\2\2\u0178\u017a\7f\2\2\u0179"+
		"\u0174\3\2\2\2\u0179\u0176\3\2\2\2\u017ad\3\2\2\2\u017b\u017c\7~\2\2\u017c"+
		"\u0180\7~\2\2\u017d\u017e\7q\2\2\u017e\u0180\7t\2\2\u017f\u017b\3\2\2"+
		"\2\u017f\u017d\3\2\2\2\u0180f\3\2\2\2\u0181\u0182\7-\2\2\u0182\u0183\7"+
		"-\2\2\u0183h\3\2\2\2\u0184\u0185\7/\2\2\u0185\u0186\7/\2\2\u0186j\3\2"+
		"\2\2\u0187\u0188\7-\2\2\u0188l\3\2\2\2\u0189\u018a\7/\2\2\u018an\3\2\2"+
		"\2\u018b\u018c\7,\2\2\u018cp\3\2\2\2\u018d\u018e\7\61\2\2\u018er\3\2\2"+
		"\2\u018f\u0190\7(\2\2\u0190t\3\2\2\2\u0191\u0192\7~\2\2\u0192v\3\2\2\2"+
		"\u0193\u0194\7`\2\2\u0194x\3\2\2\2\u0195\u0196\7\'\2\2\u0196z\3\2\2\2"+
		"\u0197\u0198\7?\2\2\u0198\u0199\7@\2\2\u0199|\3\2\2\2\u019a\u019b\7v\2"+
		"\2\u019b\u019c\7t\2\2\u019c\u019d\7w\2\2\u019d\u01a4\7g\2\2\u019e\u019f"+
		"\7h\2\2\u019f\u01a0\7c\2\2\u01a0\u01a1\7n\2\2\u01a1\u01a2\7u\2\2\u01a2"+
		"\u01a4\7g\2\2\u01a3\u019a\3\2\2\2\u01a3\u019e\3\2\2\2\u01a4~\3\2\2\2\u01a5"+
		"\u01aa\5\u0095K\2\u01a6\u01a9\5\u0095K\2\u01a7\u01a9\5\u008dG\2\u01a8"+
		"\u01a6\3\2\2\2\u01a8\u01a7\3\2\2\2\u01a9\u01ac\3\2\2\2\u01aa\u01a8\3\2"+
		"\2\2\u01aa\u01ab\3\2\2\2\u01ab\u0080\3\2\2\2\u01ac\u01aa\3\2\2\2\u01ad"+
		"\u01b1\t\4\2\2\u01ae\u01b0\t\5\2\2\u01af\u01ae\3\2\2\2\u01b0\u01b3\3\2"+
		"\2\2\u01b1\u01af\3\2\2\2\u01b1\u01b2\3\2\2\2\u01b2\u01b6\3\2\2\2\u01b3"+
		"\u01b1\3\2\2\2\u01b4\u01b6\7\62\2\2\u01b5\u01ad\3\2\2\2\u01b5\u01b4\3"+
		"\2\2\2\u01b6\u0082\3\2\2\2\u01b7\u01c0\5\u008dG\2\u01b8\u01ba\7\60\2\2"+
		"\u01b9\u01bb\5\u008dG\2\u01ba\u01b9\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb"+
		"\u01bd\3\2\2\2\u01bc\u01be\5\u0093J\2\u01bd\u01bc\3\2\2\2\u01bd\u01be"+
		"\3\2\2\2\u01be\u01c1\3\2\2\2\u01bf\u01c1\5\u0093J\2\u01c0\u01b8\3\2\2"+
		"\2\u01c0\u01bf\3\2\2\2\u01c1\u01c8\3\2\2\2\u01c2\u01c3\7\60\2\2\u01c3"+
		"\u01c5\5\u008dG\2\u01c4\u01c6\5\u0093J\2\u01c5\u01c4\3\2\2\2\u01c5\u01c6"+
		"\3\2\2\2\u01c6\u01c8\3\2\2\2\u01c7\u01b7\3\2\2\2\u01c7\u01c2\3\2\2\2\u01c8"+
		"\u0084\3\2\2\2\u01c9\u01d2\5\u008dG\2\u01ca\u01cc\7\60\2\2\u01cb\u01cd"+
		"\5\u008dG\2\u01cc\u01cb\3\2\2\2\u01cc\u01cd\3\2\2\2\u01cd\u01cf\3\2\2"+
		"\2\u01ce\u01d0\5\u0093J\2\u01cf\u01ce\3\2\2\2\u01cf\u01d0\3\2\2\2\u01d0"+
		"\u01d3\3\2\2\2\u01d1\u01d3\5\u0093J\2\u01d2\u01ca\3\2\2\2\u01d2\u01d1"+
		"\3\2\2\2\u01d3\u01d4\3\2\2\2\u01d4\u01d5\5\u0087D\2\u01d5\u01e1\3\2\2"+
		"\2\u01d6\u01d7\7\60\2\2\u01d7\u01d9\5\u008dG\2\u01d8\u01da\5\u0093J\2"+
		"\u01d9\u01d8\3\2\2\2\u01d9\u01da\3\2\2\2\u01da\u01db\3\2\2\2\u01db\u01dc"+
		"\5\u0087D\2\u01dc\u01e1\3\2\2\2\u01dd\u01de\5\u008dG\2\u01de\u01df\5\u0087"+
		"D\2\u01df\u01e1\3\2\2\2\u01e0\u01c9\3\2\2\2\u01e0\u01d6\3\2\2\2\u01e0"+
		"\u01dd\3\2\2\2\u01e1\u0086\3\2\2\2\u01e2\u01e3\7f\2\2\u01e3\u0088\3\2"+
		"\2\2\u01e4\u01e8\7b\2\2\u01e5\u01e7\n\6\2\2\u01e6\u01e5\3\2\2\2\u01e7"+
		"\u01ea\3\2\2\2\u01e8\u01e6\3\2\2\2\u01e8\u01e9\3\2\2\2\u01e9\u01eb\3\2"+
		"\2\2\u01ea\u01e8\3\2\2\2\u01eb\u01ec\7b\2\2\u01ec\u008a\3\2\2\2\u01ed"+
		"\u01f3\7$\2\2\u01ee\u01ef\7^\2\2\u01ef\u01f2\t\7\2\2\u01f0\u01f2\n\b\2"+
		"\2\u01f1\u01ee\3\2\2\2\u01f1\u01f0\3\2\2\2\u01f2\u01f5\3\2\2\2\u01f3\u01f1"+
		"\3\2\2\2\u01f3\u01f4\3\2\2\2\u01f4\u01f6\3\2\2\2\u01f5\u01f3\3\2\2\2\u01f6"+
		"\u01f7\7$\2\2\u01f7\u008c\3\2\2\2\u01f8\u01fa\t\5\2\2\u01f9\u01f8\3\2"+
		"\2\2\u01fa\u01fb\3\2\2\2\u01fb\u01f9\3\2\2\2\u01fb\u01fc\3\2\2\2\u01fc"+
		"\u008e\3\2\2\2\u01fd\u01fe\t\t\2\2\u01fe\u0090\3\2\2\2\u01ff\u0200\t\n"+
		"\2\2\u0200\u0092\3\2\2\2\u0201\u0203\t\13\2\2\u0202\u0204\t\f\2\2\u0203"+
		"\u0202\3\2\2\2\u0203\u0204\3\2\2\2\u0204\u0205\3\2\2\2\u0205\u0206\5\u008d"+
		"G\2\u0206\u0094\3\2\2\2\u0207\u0208\t\r\2\2\u0208\u0096\3\2\2\2\36\2\u0098"+
		"\u009f\u00a9\u00b4\u00d5\u0179\u017f\u01a3\u01a8\u01aa\u01b1\u01b5\u01ba"+
		"\u01bd\u01c0\u01c5\u01c7\u01cc\u01cf\u01d2\u01d9\u01e0\u01e8\u01f1\u01f3"+
		"\u01fb\u0203\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}