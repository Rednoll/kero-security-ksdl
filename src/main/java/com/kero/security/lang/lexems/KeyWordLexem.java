package com.kero.security.lang.lexems;

import java.util.regex.Pattern;

import com.kero.security.lang.tokens.KeyWordToken;

public enum KeyWordLexem implements KsdlLexem<KeyWordToken> {
	
	SCHEME("scheme", true, KeyWordToken.SCHEME),
	METALINE("\\-", true, KeyWordToken.METALINE),
	FORWARD_DIRECTION("\\->", false, KeyWordToken.TO),
	TO("to", true, KeyWordToken.TO),
	OPEN_BLOCK("\\{", false, KeyWordToken.OPEN_BLOCK),
	CLOSE_BLOCK("\\}", false, KeyWordToken.CLOSE_BLOCK),
	OPEN_SHORT_BLOCK(":", false, KeyWordToken.OPEN_BLOCK),
	CLOSE_SHORT_BLOCK("\\n", false, KeyWordToken.CLOSE_BLOCK), //Anonymous, not common work (detecting by lexer)
	EXTENDS("extends", true, KeyWordToken.EXTENDS),
	BIND("bind", true, KeyWordToken.BIND);
	
	private String pattern;
	private boolean requireSpace;
	private KeyWordToken token;
	
	private KeyWordLexem(String pattern, boolean requireSpace, KeyWordToken token) {
		
		this.pattern = pattern;
		this.requireSpace = requireSpace;
		this.token = token;
	}
	
	@Override
	public boolean isMatch(CharSequence data) {
		
		return Pattern.matches(pattern, data);
	}
	
	public boolean isRequireSpace() {
		
		return this.requireSpace;
	}
	
	@Override
	public String getPattern() {
		
		return this.pattern;
	}

	@Override
	public KeyWordToken tokenize(String data) {
		
		return this.token;
	}
}
