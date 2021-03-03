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
		COLON=39, BANG=40, NOT=41, AND=42, INC=43, DEC=44, ADD=45, SUB=46, MUL=47, 
		DIV=48, BITAND=49, BITOR=50, POW=51, MOD=52, FATARROW=53, BooleanLiteral=54, 
		DECIMAL_LIT=55, FLOAT_LIT=56, IDENTIFIER=57, RAW_STRING_LIT=58, STRING_LITERAL=59;
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
			"GT", "LT", "LE", "GE", "EQUAL", "NOTEQUAL", "TILDE", "COLON", "BANG", 
			"NOT", "AND", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", 
			"POW", "MOD", "FATARROW", "BooleanLiteral", "DECIMAL_LIT", "FLOAT_LIT", 
			"IDENTIFIER", "RAW_STRING_LIT", "STRING_LITERAL", "DoubleStringCharacter", 
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2=\u01a4\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\3\2\6\2\u0087\n\2\r\2\16\2\u0088\3\2\3"+
		"\2\3\3\3\3\3\3\3\3\7\3\u0091\n\3\f\3\16\3\u0094\13\3\3\3\3\3\3\4\3\4\3"+
		"\4\3\4\7\4\u009c\n\4\f\4\16\4\u009f\13\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3"+
		"\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3"+
		"\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23"+
		"\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\26"+
		"\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34"+
		"\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3#\3$\3$\3$\3"+
		"%\3%\3%\3&\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3*\3*\3+\3+\3+\3+\3+\5+\u0139"+
		"\n+\3,\3,\3,\3-\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3"+
		"\63\3\64\3\64\3\65\3\65\3\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\3"+
		"\67\3\67\3\67\5\67\u015d\n\67\38\38\78\u0161\n8\f8\168\u0164\138\39\3"+
		"9\39\59\u0169\n9\39\59\u016c\n9\39\59\u016f\n9\39\39\39\59\u0174\n9\5"+
		"9\u0176\n9\3:\3:\3:\7:\u017b\n:\f:\16:\u017e\13:\3;\3;\7;\u0182\n;\f;"+
		"\16;\u0185\13;\3;\3;\3<\3<\7<\u018b\n<\f<\16<\u018e\13<\3<\3<\3=\3=\3"+
		">\6>\u0195\n>\r>\16>\u0196\3?\3?\3@\3@\3A\3A\5A\u019f\nA\3A\3A\3B\3B\3"+
		"\u009d\2C\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33"+
		"\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67"+
		"\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65"+
		"i\66k\67m8o9q:s;u<w=y\2{\2}\2\177\2\u0081\2\u0083\2\3\2\r\5\2\13\f\16"+
		"\17\"\"\4\2\f\f\17\17\3\2\63;\3\2\62;\3\2bb\6\2\f\f\17\17$$^^\3\2\629"+
		"\5\2\62;CHch\4\2GGgg\4\2--//\5\2C\\aac|\2\u01ae\2\3\3\2\2\2\2\5\3\2\2"+
		"\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2"+
		"\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3"+
		"\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3"+
		"\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3"+
		"\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2"+
		"\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2"+
		"Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3"+
		"\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2"+
		"\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\3\u0086\3\2\2\2\5\u008c\3\2\2\2\7"+
		"\u0097\3\2\2\2\t\u00a5\3\2\2\2\13\u00aa\3\2\2\2\r\u00ad\3\2\2\2\17\u00b2"+
		"\3\2\2\2\21\u00bb\3\2\2\2\23\u00c2\3\2\2\2\25\u00c6\3\2\2\2\27\u00ca\3"+
		"\2\2\2\31\u00cf\3\2\2\2\33\u00d3\3\2\2\2\35\u00d9\3\2\2\2\37\u00de\3\2"+
		"\2\2!\u00e5\3\2\2\2#\u00ec\3\2\2\2%\u00f3\3\2\2\2\'\u00f7\3\2\2\2)\u00fd"+
		"\3\2\2\2+\u0102\3\2\2\2-\u0105\3\2\2\2/\u0107\3\2\2\2\61\u0109\3\2\2\2"+
		"\63\u010b\3\2\2\2\65\u010d\3\2\2\2\67\u010f\3\2\2\29\u0111\3\2\2\2;\u0113"+
		"\3\2\2\2=\u0115\3\2\2\2?\u0117\3\2\2\2A\u0119\3\2\2\2C\u011b\3\2\2\2E"+
		"\u011d\3\2\2\2G\u0120\3\2\2\2I\u0123\3\2\2\2K\u0126\3\2\2\2M\u0129\3\2"+
		"\2\2O\u012b\3\2\2\2Q\u012d\3\2\2\2S\u012f\3\2\2\2U\u0138\3\2\2\2W\u013a"+
		"\3\2\2\2Y\u013d\3\2\2\2[\u0140\3\2\2\2]\u0142\3\2\2\2_\u0144\3\2\2\2a"+
		"\u0146\3\2\2\2c\u0148\3\2\2\2e\u014a\3\2\2\2g\u014c\3\2\2\2i\u014e\3\2"+
		"\2\2k\u0150\3\2\2\2m\u015c\3\2\2\2o\u015e\3\2\2\2q\u0175\3\2\2\2s\u0177"+
		"\3\2\2\2u\u017f\3\2\2\2w\u0188\3\2\2\2y\u0191\3\2\2\2{\u0194\3\2\2\2}"+
		"\u0198\3\2\2\2\177\u019a\3\2\2\2\u0081\u019c\3\2\2\2\u0083\u01a2\3\2\2"+
		"\2\u0085\u0087\t\2\2\2\u0086\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0086"+
		"\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008b\b\2\2\2\u008b"+
		"\4\3\2\2\2\u008c\u008d\7\61\2\2\u008d\u008e\7\61\2\2\u008e\u0092\3\2\2"+
		"\2\u008f\u0091\n\3\2\2\u0090\u008f\3\2\2\2\u0091\u0094\3\2\2\2\u0092\u0090"+
		"\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0095\3\2\2\2\u0094\u0092\3\2\2\2\u0095"+
		"\u0096\b\3\2\2\u0096\6\3\2\2\2\u0097\u0098\7\61\2\2\u0098\u0099\7,\2\2"+
		"\u0099\u009d\3\2\2\2\u009a\u009c\13\2\2\2\u009b\u009a\3\2\2\2\u009c\u009f"+
		"\3\2\2\2\u009d\u009e\3\2\2\2\u009d\u009b\3\2\2\2\u009e\u00a0\3\2\2\2\u009f"+
		"\u009d\3\2\2\2\u00a0\u00a1\7,\2\2\u00a1\u00a2\7\61\2\2\u00a2\u00a3\3\2"+
		"\2\2\u00a3\u00a4\b\4\2\2\u00a4\b\3\2\2\2\u00a5\u00a6\7n\2\2\u00a6\u00a7"+
		"\7q\2\2\u00a7\u00a8\7q\2\2\u00a8\u00a9\7r\2\2\u00a9\n\3\2\2\2\u00aa\u00ab"+
		"\7k\2\2\u00ab\u00ac\7h\2\2\u00ac\f\3\2\2\2\u00ad\u00ae\7g\2\2\u00ae\u00af"+
		"\7n\2\2\u00af\u00b0\7u\2\2\u00b0\u00b1\7g\2\2\u00b1\16\3\2\2\2\u00b2\u00b3"+
		"\7h\2\2\u00b3\u00b4\7w\2\2\u00b4\u00b5\7p\2\2\u00b5\u00b6\7e\2\2\u00b6"+
		"\u00b7\7v\2\2\u00b7\u00b8\7k\2\2\u00b8\u00b9\7q\2\2\u00b9\u00ba\7p\2\2"+
		"\u00ba\20\3\2\2\2\u00bb\u00bc\7t\2\2\u00bc\u00bd\7g\2\2\u00bd\u00be\7"+
		"v\2\2\u00be\u00bf\7w\2\2\u00bf\u00c0\7t\2\2\u00c0\u00c1\7p\2\2\u00c1\22"+
		"\3\2\2\2\u00c2\u00c3\7x\2\2\u00c3\u00c4\7c\2\2\u00c4\u00c5\7n\2\2\u00c5"+
		"\24\3\2\2\2\u00c6\u00c7\7o\2\2\u00c7\u00c8\7w\2\2\u00c8\u00c9\7v\2\2\u00c9"+
		"\26\3\2\2\2\u00ca\u00cb\7d\2\2\u00cb\u00cc\7q\2\2\u00cc\u00cd\7q\2\2\u00cd"+
		"\u00ce\7n\2\2\u00ce\30\3\2\2\2\u00cf\u00d0\7k\2\2\u00d0\u00d1\7p\2\2\u00d1"+
		"\u00d2\7v\2\2\u00d2\32\3\2\2\2\u00d3\u00d4\7h\2\2\u00d4\u00d5\7n\2\2\u00d5"+
		"\u00d6\7q\2\2\u00d6\u00d7\7c\2\2\u00d7\u00d8\7v\2\2\u00d8\34\3\2\2\2\u00d9"+
		"\u00da\7e\2\2\u00da\u00db\7j\2\2\u00db\u00dc\7c\2\2\u00dc\u00dd\7t\2\2"+
		"\u00dd\36\3\2\2\2\u00de\u00df\7u\2\2\u00df\u00e0\7v\2\2\u00e0\u00e1\7"+
		"t\2\2\u00e1\u00e2\7k\2\2\u00e2\u00e3\7p\2\2\u00e3\u00e4\7i\2\2\u00e4 "+
		"\3\2\2\2\u00e5\u00e6\7g\2\2\u00e6\u00e7\7z\2\2\u00e7\u00e8\7r\2\2\u00e8"+
		"\u00e9\7q\2\2\u00e9\u00ea\7t\2\2\u00ea\u00eb\7v\2\2\u00eb\"\3\2\2\2\u00ec"+
		"\u00ed\7k\2\2\u00ed\u00ee\7o\2\2\u00ee\u00ef\7r\2\2\u00ef\u00f0\7q\2\2"+
		"\u00f0\u00f1\7t\2\2\u00f1\u00f2\7v\2\2\u00f2$\3\2\2\2\u00f3\u00f4\7p\2"+
		"\2\u00f4\u00f5\7g\2\2\u00f5\u00f6\7y\2\2\u00f6&\3\2\2\2\u00f7\u00f8\7"+
		"e\2\2\u00f8\u00f9\7n\2\2\u00f9\u00fa\7c\2\2\u00fa\u00fb\7u\2\2\u00fb\u00fc"+
		"\7u\2\2\u00fc(\3\2\2\2\u00fd\u00fe\7g\2\2\u00fe\u00ff\7p\2\2\u00ff\u0100"+
		"\7w\2\2\u0100\u0101\7o\2\2\u0101*\3\2\2\2\u0102\u0103\7k\2\2\u0103\u0104"+
		"\7p\2\2\u0104,\3\2\2\2\u0105\u0106\7*\2\2\u0106.\3\2\2\2\u0107\u0108\7"+
		"+\2\2\u0108\60\3\2\2\2\u0109\u010a\7}\2\2\u010a\62\3\2\2\2\u010b\u010c"+
		"\7\177\2\2\u010c\64\3\2\2\2\u010d\u010e\7]\2\2\u010e\66\3\2\2\2\u010f"+
		"\u0110\7_\2\2\u01108\3\2\2\2\u0111\u0112\7=\2\2\u0112:\3\2\2\2\u0113\u0114"+
		"\7.\2\2\u0114<\3\2\2\2\u0115\u0116\7\60\2\2\u0116>\3\2\2\2\u0117\u0118"+
		"\7?\2\2\u0118@\3\2\2\2\u0119\u011a\7@\2\2\u011aB\3\2\2\2\u011b\u011c\7"+
		">\2\2\u011cD\3\2\2\2\u011d\u011e\7>\2\2\u011e\u011f\7?\2\2\u011fF\3\2"+
		"\2\2\u0120\u0121\7@\2\2\u0121\u0122\7?\2\2\u0122H\3\2\2\2\u0123\u0124"+
		"\7?\2\2\u0124\u0125\7?\2\2\u0125J\3\2\2\2\u0126\u0127\7#\2\2\u0127\u0128"+
		"\7?\2\2\u0128L\3\2\2\2\u0129\u012a\7\u0080\2\2\u012aN\3\2\2\2\u012b\u012c"+
		"\7<\2\2\u012cP\3\2\2\2\u012d\u012e\7#\2\2\u012eR\3\2\2\2\u012f\u0130\7"+
		"p\2\2\u0130\u0131\7q\2\2\u0131\u0132\7v\2\2\u0132T\3\2\2\2\u0133\u0134"+
		"\7(\2\2\u0134\u0139\7(\2\2\u0135\u0136\7c\2\2\u0136\u0137\7p\2\2\u0137"+
		"\u0139\7f\2\2\u0138\u0133\3\2\2\2\u0138\u0135\3\2\2\2\u0139V\3\2\2\2\u013a"+
		"\u013b\7-\2\2\u013b\u013c\7-\2\2\u013cX\3\2\2\2\u013d\u013e\7/\2\2\u013e"+
		"\u013f\7/\2\2\u013fZ\3\2\2\2\u0140\u0141\7-\2\2\u0141\\\3\2\2\2\u0142"+
		"\u0143\7/\2\2\u0143^\3\2\2\2\u0144\u0145\7,\2\2\u0145`\3\2\2\2\u0146\u0147"+
		"\7\61\2\2\u0147b\3\2\2\2\u0148\u0149\7(\2\2\u0149d\3\2\2\2\u014a\u014b"+
		"\7~\2\2\u014bf\3\2\2\2\u014c\u014d\7`\2\2\u014dh\3\2\2\2\u014e\u014f\7"+
		"\'\2\2\u014fj\3\2\2\2\u0150\u0151\7?\2\2\u0151\u0152\7@\2\2\u0152l\3\2"+
		"\2\2\u0153\u0154\7v\2\2\u0154\u0155\7t\2\2\u0155\u0156\7w\2\2\u0156\u015d"+
		"\7g\2\2\u0157\u0158\7h\2\2\u0158\u0159\7c\2\2\u0159\u015a\7n\2\2\u015a"+
		"\u015b\7u\2\2\u015b\u015d\7g\2\2\u015c\u0153\3\2\2\2\u015c\u0157\3\2\2"+
		"\2\u015dn\3\2\2\2\u015e\u0162\t\4\2\2\u015f\u0161\t\5\2\2\u0160\u015f"+
		"\3\2\2\2\u0161\u0164\3\2\2\2\u0162\u0160\3\2\2\2\u0162\u0163\3\2\2\2\u0163"+
		"p\3\2\2\2\u0164\u0162\3\2\2\2\u0165\u016e\5{>\2\u0166\u0168\7\60\2\2\u0167"+
		"\u0169\5{>\2\u0168\u0167\3\2\2\2\u0168\u0169\3\2\2\2\u0169\u016b\3\2\2"+
		"\2\u016a\u016c\5\u0081A\2\u016b\u016a\3\2\2\2\u016b\u016c\3\2\2\2\u016c"+
		"\u016f\3\2\2\2\u016d\u016f\5\u0081A\2\u016e\u0166\3\2\2\2\u016e\u016d"+
		"\3\2\2\2\u016f\u0176\3\2\2\2\u0170\u0171\7\60\2\2\u0171\u0173\5{>\2\u0172"+
		"\u0174\5\u0081A\2\u0173\u0172\3\2\2\2\u0173\u0174\3\2\2\2\u0174\u0176"+
		"\3\2\2\2\u0175\u0165\3\2\2\2\u0175\u0170\3\2\2\2\u0176r\3\2\2\2\u0177"+
		"\u017c\5\u0083B\2\u0178\u017b\5\u0083B\2\u0179\u017b\5{>\2\u017a\u0178"+
		"\3\2\2\2\u017a\u0179\3\2\2\2\u017b\u017e\3\2\2\2\u017c\u017a\3\2\2\2\u017c"+
		"\u017d\3\2\2\2\u017dt\3\2\2\2\u017e\u017c\3\2\2\2\u017f\u0183\7b\2\2\u0180"+
		"\u0182\n\6\2\2\u0181\u0180\3\2\2\2\u0182\u0185\3\2\2\2\u0183\u0181\3\2"+
		"\2\2\u0183\u0184\3\2\2\2\u0184\u0186\3\2\2\2\u0185\u0183\3\2\2\2\u0186"+
		"\u0187\7b\2\2\u0187v\3\2\2\2\u0188\u018c\7$\2\2\u0189\u018b\5y=\2\u018a"+
		"\u0189\3\2\2\2\u018b\u018e\3\2\2\2\u018c\u018a\3\2\2\2\u018c\u018d\3\2"+
		"\2\2\u018d\u018f\3\2\2\2\u018e\u018c\3\2\2\2\u018f\u0190\7$\2\2\u0190"+
		"x\3\2\2\2\u0191\u0192\n\7\2\2\u0192z\3\2\2\2\u0193\u0195\t\5\2\2\u0194"+
		"\u0193\3\2\2\2\u0195\u0196\3\2\2\2\u0196\u0194\3\2\2\2\u0196\u0197\3\2"+
		"\2\2\u0197|\3\2\2\2\u0198\u0199\t\b\2\2\u0199~\3\2\2\2\u019a\u019b\t\t"+
		"\2\2\u019b\u0080\3\2\2\2\u019c\u019e\t\n\2\2\u019d\u019f\t\13\2\2\u019e"+
		"\u019d\3\2\2\2\u019e\u019f\3\2\2\2\u019f\u01a0\3\2\2\2\u01a0\u01a1\5{"+
		">\2\u01a1\u0082\3\2\2\2\u01a2\u01a3\t\f\2\2\u01a3\u0084\3\2\2\2\24\2\u0088"+
		"\u0092\u009d\u0138\u015c\u0162\u0168\u016b\u016e\u0173\u0175\u017a\u017c"+
		"\u0183\u018c\u0196\u019e\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}