package com.kero.security.lang.speakers.metalines;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public abstract class MetalineSpeakerBase implements MetalineSpeaker {

	protected String name;
	
	public MetalineSpeakerBase(String name) {
		
		this.name = name;
	}
	
	@Override
	public boolean isMatch(TokenSequence tokens) {
	
		if(!tokens.isToken(0, KeyWordToken.METALINE)) return false;
		if(!tokens.isToken(1, NameToken.class)) return false;
		if(!((NameToken) tokens.get(1)).getRaw().equals(name)) return false;
		
		return true;
	}
}
