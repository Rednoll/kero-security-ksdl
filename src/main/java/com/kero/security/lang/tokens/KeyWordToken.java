package com.kero.security.lang.tokens;

public enum KeyWordToken implements KsdlToken {
	
	SCHEME,
	METALINE,
	OPEN_BLOCK,
	CLOSE_BLOCK,
	TO,
	EXTENDS,
	BIND;

	public String toText() {
		
		return this.name().toLowerCase();
	}
}
