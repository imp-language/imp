// Generated from org\imp\jvm\ImpLexer.g4 by ANTLR 4.9.1
package org.imp.jvm;
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
		WhiteSpace=1, Comment=2, CommentMultiLine=3, LOOP=4, IF=5, ELSE=6, FUNCTION=7, 
		RETURN=8, VAL=9, MUT=10, BOOL=11, INT=12, FLOAT=13, CHAR=14, STRING=15, 
		EXPORT=16, IMPORT=17, FROM=18, AS=19, NEW=20, CLASS=21, INTERFACE=22, 
		ENUM=23, PUBLIC=24, IN=25, LPAREN=26, RPAREN=27, LBRACE=28, RBRACE=29, 
		LBRACK=30, RBRACK=31, SEMICOLON=32, COMMA=33, DOT=34, ASSIGN=35, GT=36, 
		LT=37, LE=38, GE=39, EQUAL=40, NOTEQUAL=41, TILDE=42, COLON=43, BANG=44, 
		NOT=45, AND=46, OR=47, INC=48, DEC=49, ADD=50, SUB=51, MUL=52, DIV=53, 
		BITAND=54, BITOR=55, POW=56, MOD=57, FATARROW=58, BooleanLiteral=59, DECIMAL_LIT=60, 
		FLOAT_LIT=61, IDENTIFIER=62, RAW_STRING_LIT=63, STRING_LITERAL=64;
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
			"IMPORT", "FROM", "AS", "NEW", "CLASS", "INTERFACE", "ENUM", "PUBLIC", 
			"IN", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "SEMICOLON", 
			"COMMA", "DOT", "ASSIGN", "GT", "LT", "LE", "GE", "EQUAL", "NOTEQUAL", 
			"TILDE", "COLON", "BANG", "NOT", "AND", "OR", "INC", "DEC", "ADD", "SUB", 
			"MUL", "DIV", "BITAND", "BITOR", "POW", "MOD", "FATARROW", "BooleanLiteral", 
			"DECIMAL_LIT", "FLOAT_LIT", "IDENTIFIER", "RAW_STRING_LIT", "STRING_LITERAL", 
			"DoubleStringCharacter", "DECIMALS", "OCTAL_DIGIT", "HEX_DIGIT", "EXPONENT", 
			"ALPHA"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'loop'", "'if'", "'else'", "'function'", "'return'", 
			"'val'", "'mut'", "'bool'", "'int'", "'float'", "'char'", "'string'", 
			"'export'", "'import'", "'from'", "'as'", "'new'", "'class'", "'interface'", 
			"'enum'", "'public'", "'in'", "'('", "')'", "'{'", "'}'", "'['", "']'", 
			"';'", "','", "'.'", "'='", "'>'", "'<'", "'<='", "'>='", "'=='", "'!='", 
			"'~'", "':'", "'!'", "'not'", null, null, "'++'", "'--'", "'+'", "'-'", 
			"'*'", "'/'", "'&'", "'|'", "'^'", "'%'", "'=>'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WhiteSpace", "Comment", "CommentMultiLine", "LOOP", "IF", "ELSE", 
			"FUNCTION", "RETURN", "VAL", "MUT", "BOOL", "INT", "FLOAT", "CHAR", "STRING", 
			"EXPORT", "IMPORT", "FROM", "AS", "NEW", "CLASS", "INTERFACE", "ENUM", 
			"PUBLIC", "IN", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", 
			"SEMICOLON", "COMMA", "DOT", "ASSIGN", "GT", "LT", "LE", "GE", "EQUAL", 
			"NOTEQUAL", "TILDE", "COLON", "BANG", "NOT", "AND", "OR", "INC", "DEC", 
			"ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", "POW", "MOD", "FATARROW", 
			"BooleanLiteral", "DECIMAL_LIT", "FLOAT_LIT", "IDENTIFIER", "RAW_STRING_LIT", 
			"STRING_LITERAL"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2B\u01d0\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\3\2\6\2\u0091"+
		"\n\2\r\2\16\2\u0092\3\2\3\2\3\3\3\3\3\3\3\3\7\3\u009b\n\3\f\3\16\3\u009e"+
		"\13\3\3\3\3\3\3\4\3\4\3\4\3\4\7\4\u00a6\n\4\f\4\16\4\u00a9\13\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3"+
		"\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3"+
		"\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3"+
		" \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3\'\3(\3(\3(\3)\3)"+
		"\3)\3*\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3.\3.\3/\3/\3/\3/\3/\5/\u015c\n/"+
		"\3\60\3\60\3\60\3\60\5\60\u0162\n\60\3\61\3\61\3\61\3\62\3\62\3\62\3\63"+
		"\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3\67\38\38\39\39\3:\3:\3;\3;"+
		"\3;\3<\3<\3<\3<\3<\3<\3<\3<\3<\5<\u0186\n<\3=\3=\7=\u018a\n=\f=\16=\u018d"+
		"\13=\3=\5=\u0190\n=\3>\3>\3>\5>\u0195\n>\3>\5>\u0198\n>\3>\5>\u019b\n"+
		">\3>\3>\3>\5>\u01a0\n>\5>\u01a2\n>\3?\3?\3?\7?\u01a7\n?\f?\16?\u01aa\13"+
		"?\3@\3@\7@\u01ae\n@\f@\16@\u01b1\13@\3@\3@\3A\3A\7A\u01b7\nA\fA\16A\u01ba"+
		"\13A\3A\3A\3B\3B\3C\6C\u01c1\nC\rC\16C\u01c2\3D\3D\3E\3E\3F\3F\5F\u01cb"+
		"\nF\3F\3F\3G\3G\3\u00a7\2H\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32"+
		"\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a"+
		"\62c\63e\64g\65i\66k\67m8o9q:s;u<w=y>{?}@\177A\u0081B\u0083\2\u0085\2"+
		"\u0087\2\u0089\2\u008b\2\u008d\2\3\2\r\5\2\13\f\16\17\"\"\4\2\f\f\17\17"+
		"\3\2\63;\3\2\62;\3\2bb\6\2\f\f\17\17$$^^\3\2\629\5\2\62;CHch\4\2GGgg\4"+
		"\2--//\5\2C\\aac|\2\u01dc\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2"+
		"\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2"+
		"\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3"+
		"\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2"+
		"\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67"+
		"\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2"+
		"\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2"+
		"\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]"+
		"\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2"+
		"\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2"+
		"\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2"+
		"\2\2\3\u0090\3\2\2\2\5\u0096\3\2\2\2\7\u00a1\3\2\2\2\t\u00af\3\2\2\2\13"+
		"\u00b4\3\2\2\2\r\u00b7\3\2\2\2\17\u00bc\3\2\2\2\21\u00c5\3\2\2\2\23\u00cc"+
		"\3\2\2\2\25\u00d0\3\2\2\2\27\u00d4\3\2\2\2\31\u00d9\3\2\2\2\33\u00dd\3"+
		"\2\2\2\35\u00e3\3\2\2\2\37\u00e8\3\2\2\2!\u00ef\3\2\2\2#\u00f6\3\2\2\2"+
		"%\u00fd\3\2\2\2\'\u0102\3\2\2\2)\u0105\3\2\2\2+\u0109\3\2\2\2-\u010f\3"+
		"\2\2\2/\u0119\3\2\2\2\61\u011e\3\2\2\2\63\u0125\3\2\2\2\65\u0128\3\2\2"+
		"\2\67\u012a\3\2\2\29\u012c\3\2\2\2;\u012e\3\2\2\2=\u0130\3\2\2\2?\u0132"+
		"\3\2\2\2A\u0134\3\2\2\2C\u0136\3\2\2\2E\u0138\3\2\2\2G\u013a\3\2\2\2I"+
		"\u013c\3\2\2\2K\u013e\3\2\2\2M\u0140\3\2\2\2O\u0143\3\2\2\2Q\u0146\3\2"+
		"\2\2S\u0149\3\2\2\2U\u014c\3\2\2\2W\u014e\3\2\2\2Y\u0150\3\2\2\2[\u0152"+
		"\3\2\2\2]\u015b\3\2\2\2_\u0161\3\2\2\2a\u0163\3\2\2\2c\u0166\3\2\2\2e"+
		"\u0169\3\2\2\2g\u016b\3\2\2\2i\u016d\3\2\2\2k\u016f\3\2\2\2m\u0171\3\2"+
		"\2\2o\u0173\3\2\2\2q\u0175\3\2\2\2s\u0177\3\2\2\2u\u0179\3\2\2\2w\u0185"+
		"\3\2\2\2y\u018f\3\2\2\2{\u01a1\3\2\2\2}\u01a3\3\2\2\2\177\u01ab\3\2\2"+
		"\2\u0081\u01b4\3\2\2\2\u0083\u01bd\3\2\2\2\u0085\u01c0\3\2\2\2\u0087\u01c4"+
		"\3\2\2\2\u0089\u01c6\3\2\2\2\u008b\u01c8\3\2\2\2\u008d\u01ce\3\2\2\2\u008f"+
		"\u0091\t\2\2\2\u0090\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u0090\3\2"+
		"\2\2\u0092\u0093\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0095\b\2\2\2\u0095"+
		"\4\3\2\2\2\u0096\u0097\7\61\2\2\u0097\u0098\7\61\2\2\u0098\u009c\3\2\2"+
		"\2\u0099\u009b\n\3\2\2\u009a\u0099\3\2\2\2\u009b\u009e\3\2\2\2\u009c\u009a"+
		"\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009f\3\2\2\2\u009e\u009c\3\2\2\2\u009f"+
		"\u00a0\b\3\2\2\u00a0\6\3\2\2\2\u00a1\u00a2\7\61\2\2\u00a2\u00a3\7,\2\2"+
		"\u00a3\u00a7\3\2\2\2\u00a4\u00a6\13\2\2\2\u00a5\u00a4\3\2\2\2\u00a6\u00a9"+
		"\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a8\u00aa\3\2\2\2\u00a9"+
		"\u00a7\3\2\2\2\u00aa\u00ab\7,\2\2\u00ab\u00ac\7\61\2\2\u00ac\u00ad\3\2"+
		"\2\2\u00ad\u00ae\b\4\2\2\u00ae\b\3\2\2\2\u00af\u00b0\7n\2\2\u00b0\u00b1"+
		"\7q\2\2\u00b1\u00b2\7q\2\2\u00b2\u00b3\7r\2\2\u00b3\n\3\2\2\2\u00b4\u00b5"+
		"\7k\2\2\u00b5\u00b6\7h\2\2\u00b6\f\3\2\2\2\u00b7\u00b8\7g\2\2\u00b8\u00b9"+
		"\7n\2\2\u00b9\u00ba\7u\2\2\u00ba\u00bb\7g\2\2\u00bb\16\3\2\2\2\u00bc\u00bd"+
		"\7h\2\2\u00bd\u00be\7w\2\2\u00be\u00bf\7p\2\2\u00bf\u00c0\7e\2\2\u00c0"+
		"\u00c1\7v\2\2\u00c1\u00c2\7k\2\2\u00c2\u00c3\7q\2\2\u00c3\u00c4\7p\2\2"+
		"\u00c4\20\3\2\2\2\u00c5\u00c6\7t\2\2\u00c6\u00c7\7g\2\2\u00c7\u00c8\7"+
		"v\2\2\u00c8\u00c9\7w\2\2\u00c9\u00ca\7t\2\2\u00ca\u00cb\7p\2\2\u00cb\22"+
		"\3\2\2\2\u00cc\u00cd\7x\2\2\u00cd\u00ce\7c\2\2\u00ce\u00cf\7n\2\2\u00cf"+
		"\24\3\2\2\2\u00d0\u00d1\7o\2\2\u00d1\u00d2\7w\2\2\u00d2\u00d3\7v\2\2\u00d3"+
		"\26\3\2\2\2\u00d4\u00d5\7d\2\2\u00d5\u00d6\7q\2\2\u00d6\u00d7\7q\2\2\u00d7"+
		"\u00d8\7n\2\2\u00d8\30\3\2\2\2\u00d9\u00da\7k\2\2\u00da\u00db\7p\2\2\u00db"+
		"\u00dc\7v\2\2\u00dc\32\3\2\2\2\u00dd\u00de\7h\2\2\u00de\u00df\7n\2\2\u00df"+
		"\u00e0\7q\2\2\u00e0\u00e1\7c\2\2\u00e1\u00e2\7v\2\2\u00e2\34\3\2\2\2\u00e3"+
		"\u00e4\7e\2\2\u00e4\u00e5\7j\2\2\u00e5\u00e6\7c\2\2\u00e6\u00e7\7t\2\2"+
		"\u00e7\36\3\2\2\2\u00e8\u00e9\7u\2\2\u00e9\u00ea\7v\2\2\u00ea\u00eb\7"+
		"t\2\2\u00eb\u00ec\7k\2\2\u00ec\u00ed\7p\2\2\u00ed\u00ee\7i\2\2\u00ee "+
		"\3\2\2\2\u00ef\u00f0\7g\2\2\u00f0\u00f1\7z\2\2\u00f1\u00f2\7r\2\2\u00f2"+
		"\u00f3\7q\2\2\u00f3\u00f4\7t\2\2\u00f4\u00f5\7v\2\2\u00f5\"\3\2\2\2\u00f6"+
		"\u00f7\7k\2\2\u00f7\u00f8\7o\2\2\u00f8\u00f9\7r\2\2\u00f9\u00fa\7q\2\2"+
		"\u00fa\u00fb\7t\2\2\u00fb\u00fc\7v\2\2\u00fc$\3\2\2\2\u00fd\u00fe\7h\2"+
		"\2\u00fe\u00ff\7t\2\2\u00ff\u0100\7q\2\2\u0100\u0101\7o\2\2\u0101&\3\2"+
		"\2\2\u0102\u0103\7c\2\2\u0103\u0104\7u\2\2\u0104(\3\2\2\2\u0105\u0106"+
		"\7p\2\2\u0106\u0107\7g\2\2\u0107\u0108\7y\2\2\u0108*\3\2\2\2\u0109\u010a"+
		"\7e\2\2\u010a\u010b\7n\2\2\u010b\u010c\7c\2\2\u010c\u010d\7u\2\2\u010d"+
		"\u010e\7u\2\2\u010e,\3\2\2\2\u010f\u0110\7k\2\2\u0110\u0111\7p\2\2\u0111"+
		"\u0112\7v\2\2\u0112\u0113\7g\2\2\u0113\u0114\7t\2\2\u0114\u0115\7h\2\2"+
		"\u0115\u0116\7c\2\2\u0116\u0117\7e\2\2\u0117\u0118\7g\2\2\u0118.\3\2\2"+
		"\2\u0119\u011a\7g\2\2\u011a\u011b\7p\2\2\u011b\u011c\7w\2\2\u011c\u011d"+
		"\7o\2\2\u011d\60\3\2\2\2\u011e\u011f\7r\2\2\u011f\u0120\7w\2\2\u0120\u0121"+
		"\7d\2\2\u0121\u0122\7n\2\2\u0122\u0123\7k\2\2\u0123\u0124\7e\2\2\u0124"+
		"\62\3\2\2\2\u0125\u0126\7k\2\2\u0126\u0127\7p\2\2\u0127\64\3\2\2\2\u0128"+
		"\u0129\7*\2\2\u0129\66\3\2\2\2\u012a\u012b\7+\2\2\u012b8\3\2\2\2\u012c"+
		"\u012d\7}\2\2\u012d:\3\2\2\2\u012e\u012f\7\177\2\2\u012f<\3\2\2\2\u0130"+
		"\u0131\7]\2\2\u0131>\3\2\2\2\u0132\u0133\7_\2\2\u0133@\3\2\2\2\u0134\u0135"+
		"\7=\2\2\u0135B\3\2\2\2\u0136\u0137\7.\2\2\u0137D\3\2\2\2\u0138\u0139\7"+
		"\60\2\2\u0139F\3\2\2\2\u013a\u013b\7?\2\2\u013bH\3\2\2\2\u013c\u013d\7"+
		"@\2\2\u013dJ\3\2\2\2\u013e\u013f\7>\2\2\u013fL\3\2\2\2\u0140\u0141\7>"+
		"\2\2\u0141\u0142\7?\2\2\u0142N\3\2\2\2\u0143\u0144\7@\2\2\u0144\u0145"+
		"\7?\2\2\u0145P\3\2\2\2\u0146\u0147\7?\2\2\u0147\u0148\7?\2\2\u0148R\3"+
		"\2\2\2\u0149\u014a\7#\2\2\u014a\u014b\7?\2\2\u014bT\3\2\2\2\u014c\u014d"+
		"\7\u0080\2\2\u014dV\3\2\2\2\u014e\u014f\7<\2\2\u014fX\3\2\2\2\u0150\u0151"+
		"\7#\2\2\u0151Z\3\2\2\2\u0152\u0153\7p\2\2\u0153\u0154\7q\2\2\u0154\u0155"+
		"\7v\2\2\u0155\\\3\2\2\2\u0156\u0157\7(\2\2\u0157\u015c\7(\2\2\u0158\u0159"+
		"\7c\2\2\u0159\u015a\7p\2\2\u015a\u015c\7f\2\2\u015b\u0156\3\2\2\2\u015b"+
		"\u0158\3\2\2\2\u015c^\3\2\2\2\u015d\u015e\7~\2\2\u015e\u0162\7~\2\2\u015f"+
		"\u0160\7q\2\2\u0160\u0162\7t\2\2\u0161\u015d\3\2\2\2\u0161\u015f\3\2\2"+
		"\2\u0162`\3\2\2\2\u0163\u0164\7-\2\2\u0164\u0165\7-\2\2\u0165b\3\2\2\2"+
		"\u0166\u0167\7/\2\2\u0167\u0168\7/\2\2\u0168d\3\2\2\2\u0169\u016a\7-\2"+
		"\2\u016af\3\2\2\2\u016b\u016c\7/\2\2\u016ch\3\2\2\2\u016d\u016e\7,\2\2"+
		"\u016ej\3\2\2\2\u016f\u0170\7\61\2\2\u0170l\3\2\2\2\u0171\u0172\7(\2\2"+
		"\u0172n\3\2\2\2\u0173\u0174\7~\2\2\u0174p\3\2\2\2\u0175\u0176\7`\2\2\u0176"+
		"r\3\2\2\2\u0177\u0178\7\'\2\2\u0178t\3\2\2\2\u0179\u017a\7?\2\2\u017a"+
		"\u017b\7@\2\2\u017bv\3\2\2\2\u017c\u017d\7v\2\2\u017d\u017e\7t\2\2\u017e"+
		"\u017f\7w\2\2\u017f\u0186\7g\2\2\u0180\u0181\7h\2\2\u0181\u0182\7c\2\2"+
		"\u0182\u0183\7n\2\2\u0183\u0184\7u\2\2\u0184\u0186\7g\2\2\u0185\u017c"+
		"\3\2\2\2\u0185\u0180\3\2\2\2\u0186x\3\2\2\2\u0187\u018b\t\4\2\2\u0188"+
		"\u018a\t\5\2\2\u0189\u0188\3\2\2\2\u018a\u018d\3\2\2\2\u018b\u0189\3\2"+
		"\2\2\u018b\u018c\3\2\2\2\u018c\u0190\3\2\2\2\u018d\u018b\3\2\2\2\u018e"+
		"\u0190\7\62\2\2\u018f\u0187\3\2\2\2\u018f\u018e\3\2\2\2\u0190z\3\2\2\2"+
		"\u0191\u019a\5\u0085C\2\u0192\u0194\7\60\2\2\u0193\u0195\5\u0085C\2\u0194"+
		"\u0193\3\2\2\2\u0194\u0195\3\2\2\2\u0195\u0197\3\2\2\2\u0196\u0198\5\u008b"+
		"F\2\u0197\u0196\3\2\2\2\u0197\u0198\3\2\2\2\u0198\u019b\3\2\2\2\u0199"+
		"\u019b\5\u008bF\2\u019a\u0192\3\2\2\2\u019a\u0199\3\2\2\2\u019b\u01a2"+
		"\3\2\2\2\u019c\u019d\7\60\2\2\u019d\u019f\5\u0085C\2\u019e\u01a0\5\u008b"+
		"F\2\u019f\u019e\3\2\2\2\u019f\u01a0\3\2\2\2\u01a0\u01a2\3\2\2\2\u01a1"+
		"\u0191\3\2\2\2\u01a1\u019c\3\2\2\2\u01a2|\3\2\2\2\u01a3\u01a8\5\u008d"+
		"G\2\u01a4\u01a7\5\u008dG\2\u01a5\u01a7\5\u0085C\2\u01a6\u01a4\3\2\2\2"+
		"\u01a6\u01a5\3\2\2\2\u01a7\u01aa\3\2\2\2\u01a8\u01a6\3\2\2\2\u01a8\u01a9"+
		"\3\2\2\2\u01a9~\3\2\2\2\u01aa\u01a8\3\2\2\2\u01ab\u01af\7b\2\2\u01ac\u01ae"+
		"\n\6\2\2\u01ad\u01ac\3\2\2\2\u01ae\u01b1\3\2\2\2\u01af\u01ad\3\2\2\2\u01af"+
		"\u01b0\3\2\2\2\u01b0\u01b2\3\2\2\2\u01b1\u01af\3\2\2\2\u01b2\u01b3\7b"+
		"\2\2\u01b3\u0080\3\2\2\2\u01b4\u01b8\7$\2\2\u01b5\u01b7\5\u0083B\2\u01b6"+
		"\u01b5\3\2\2\2\u01b7\u01ba\3\2\2\2\u01b8\u01b6\3\2\2\2\u01b8\u01b9\3\2"+
		"\2\2\u01b9\u01bb\3\2\2\2\u01ba\u01b8\3\2\2\2\u01bb\u01bc\7$\2\2\u01bc"+
		"\u0082\3\2\2\2\u01bd\u01be\n\7\2\2\u01be\u0084\3\2\2\2\u01bf\u01c1\t\5"+
		"\2\2\u01c0\u01bf\3\2\2\2\u01c1\u01c2\3\2\2\2\u01c2\u01c0\3\2\2\2\u01c2"+
		"\u01c3\3\2\2\2\u01c3\u0086\3\2\2\2\u01c4\u01c5\t\b\2\2\u01c5\u0088\3\2"+
		"\2\2\u01c6\u01c7\t\t\2\2\u01c7\u008a\3\2\2\2\u01c8\u01ca\t\n\2\2\u01c9"+
		"\u01cb\t\13\2\2\u01ca\u01c9\3\2\2\2\u01ca\u01cb\3\2\2\2\u01cb\u01cc\3"+
		"\2\2\2\u01cc\u01cd\5\u0085C\2\u01cd\u008c\3\2\2\2\u01ce\u01cf\t\f\2\2"+
		"\u01cf\u008e\3\2\2\2\26\2\u0092\u009c\u00a7\u015b\u0161\u0185\u018b\u018f"+
		"\u0194\u0197\u019a\u019f\u01a1\u01a6\u01a8\u01af\u01b8\u01c2\u01ca\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}