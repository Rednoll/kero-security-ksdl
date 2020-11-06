package com.kero.security.lang.speakers;

import com.kero.security.lang.collections.TokenSequence;

public interface Speaker {

	public boolean isMatch(TokenSequence seq);
	public String say(TokenSequence seq);
}
