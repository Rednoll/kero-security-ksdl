package com.kero.security.lang.speakers;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public class BindSpeaker implements Speaker {

	@Override
	public boolean isMatch(TokenSequence seq) {
		
		if(!seq.isToken(0, KeyWordToken.BIND)) return false;
		if(!seq.isToken(1, NameToken.class)) return false;
		if(!seq.isToken(2, KeyWordToken.TO)) return false;
		if(!seq.isToken(3, NameToken.class)) return false;
		
		return true;
	}

	@Override
	public String say(TokenSequence seq) {
		
		StringBuilder builder = new StringBuilder();
		
			seq.consume(KeyWordToken.BIND);
			builder.append(KeyWordToken.BIND.toText());
		
			NameToken name = seq.tryPoll(NameToken.class);
			
			builder.append(" "+name.getRaw());
			
			seq.consume(KeyWordToken.TO);
			builder.append(" "+KeyWordToken.TO.toText());
		
			NameToken className = seq.tryPoll(NameToken.class);
			
			builder.append(" "+className.getRaw());
			
			if(!seq.isToken(0, KeyWordToken.BIND)) {
				
				builder.append("\n");
			}
			
		return builder.toString();
	}
}
