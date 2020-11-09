package com.kero.security.lang;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.exception.KsdlLexerException;
import com.kero.security.lang.lexems.DefaultRuleLexem;
import com.kero.security.lang.lexems.KeyWordLexem;
import com.kero.security.lang.lexems.KsdlLexem;
import com.kero.security.lang.lexems.NameLexem;
import com.kero.security.lang.lexems.RoleLexem;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.KsdlToken;

public class KsdlLexer {

	private static final KsdlLexer INSTANCE = new KsdlLexer();
	
	private List<KeyWordLexem> keyWords = new LinkedList<>();
	private List<KsdlLexem<?>> lexems = new LinkedList<>();
	
	private KsdlLexer() {
	
		for(KeyWordLexem word : KeyWordLexem.values()) {
			
			keyWords.add(word);
		}
		
		lexems.add(new DefaultRuleLexem());
		lexems.add(new RoleLexem());
		lexems.add(new NameLexem());
	}
	
	public TokenSequence tokenize(String data) throws KsdlLexerException {
		
		if(data == null || data.isEmpty()) return new TokenSequence(data);
		
		int findShortEnd = 0;
		
		TokenSequence tokens = new TokenSequence(data);

		StringBuilder currentRawToken = new StringBuilder();

		char[] chars = data.toCharArray();
		
		for(int i = 0; i < chars.length; i++) {

			char cursor = chars[i];
			
			if(cursor == '#') {
		
				while(cursor != '\n') {
					
					i++;
					cursor = chars[i];
				}
				
				//skip \n
				i++;
				cursor = chars[i];
			}
			
			checkLexem(tokens, currentRawToken, cursor, i);

			if(!isSpace(cursor)) {
			
				currentRawToken.append(cursor);
			}
			
			if(cursor == ':') {
				
				findShortEnd++;
			}
			
			if(cursor == '\n' && findShortEnd > 0) {
				
				tokens.put(i, KeyWordToken.CLOSE_BLOCK);
				findShortEnd--;
			}
		}
		
		checkLexem(tokens, currentRawToken, '\0', chars.length);
		
		return tokens;
	}
	
	private boolean checkLexem(TokenSequence tokens, StringBuilder currentRawToken, char cursor, int cursorIndex) {
		
		if(checkWord(tokens, currentRawToken, cursor, cursorIndex)) return true;
		
		for(KsdlLexem<?> lexem : lexems) {
			
			if(lexem.isMatch(currentRawToken) && !lexem.isMatch(currentRawToken.toString()+cursor)) {
				
				KsdlToken token = lexem.tokenize(currentRawToken.toString());

				tokens.put(cursorIndex - currentRawToken.length(), token);
				currentRawToken.setLength(0);
				
				return true;
			}
		}
		
		return false;
	}
	
	private boolean checkWord(TokenSequence tokens, StringBuilder currentRawToken, char cursor, int cursorIndex) {
		
		for(KeyWordLexem word : keyWords) {
			
			if(word.isMatch(currentRawToken) && (!word.isRequireSpace() || isSpace(cursor))) {
				
				KsdlToken token = word.tokenize(currentRawToken.toString());

				tokens.put(cursorIndex - currentRawToken.length(), token);
				currentRawToken.setLength(0);

				return true;
			}			
		}
		
		return false;
	}
	
	private boolean isSpace(char tar) {
		
		return tar == '\f' || tar == '\n' || tar == '\r' || tar == '\t' || tar == ' ';
	}
	
	public static KsdlLexer getInstance() {
		
		return INSTANCE;
	}
}
