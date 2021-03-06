package com.kero.security.lang.parsers.metaline;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.nodes.metaline.MetalineNode;
import com.kero.security.lang.parsers.KsdlNodeParserBase;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public abstract class MetalineParserBase<S, T extends MetalineNode> extends KsdlNodeParserBase<S, T> implements MetalineParser<S, T> {

	protected String name;
	
	public MetalineParserBase(String name) {
		
		this.name = name;
	}
	
	public boolean isMatch(TokenSequence tokens) {
	
		if(!tokens.isToken(0, KeyWordToken.METALINE)) return false;
		if(!tokens.isToken(1, new NameToken(this.name))) return false;
		
		return true;
	}
}
