package com.kero.security.lang.speakers;

import com.kero.security.core.access.Access;
import com.kero.security.core.scheme.AccessScheme;
import com.kero.security.lang.KsdlSpeaker;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.DefaultAccessToken;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public class SchemeSpeaker implements Speaker {

	private PropertySpeaker propertySpeaker = new PropertySpeaker();
	
	@Override
	public boolean isMatch(TokenSequence seq) {
		
		if(!seq.isToken(0, KeyWordToken.SCHEME)) return false;
		if(!seq.isToken(1, NameToken.class)) return false;
		
		return true;
	}
	
	@Override
	public String say(TokenSequence seq) {
		
		seq.consume(KeyWordToken.SCHEME);
		
		StringBuilder builder = new StringBuilder();
		
			builder.append(KeyWordToken.SCHEME.toText()+" ");
		
			NameToken nameToken = seq.tryPoll(NameToken.class);
			
			builder.append(nameToken.getRaw());
			
			DefaultAccessToken access = seq.tryGetOrDefault(DefaultAccessToken.EMPTY);
			
			builder.append(access.toText());
	
			if(seq.isToken(0, KeyWordToken.EXTENDS)) {
				
				seq.poll();
				
				builder.append(" "+KeyWordToken.EXTENDS.toText());
			
				NameToken parentNameToken = seq.tryPoll(NameToken.class);
			
				builder.append(" "+parentNameToken.getRaw());
			}
			
			if(seq.isToken(0, KeyWordToken.OPEN_BLOCK)) {
			
				seq.poll();
				
				builder.append(" {\n\n");
			
				while(seq.isToken(0, NameToken.class)) {
					
					String property = propertySpeaker.say(seq);

					property = KsdlSpeaker.getInstance().addIndentTo(property);

					builder.append(property);
				}
				
				seq.consume(KeyWordToken.CLOSE_BLOCK);
				
				builder.append("}\n");
			}
			
		return builder.toString();
	}
}
