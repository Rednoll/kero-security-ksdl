package com.kero.security.lang.speakers.metalines;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public class PropagationSpeaker extends MetalineSpeakerBase {

	public PropagationSpeaker() {
		super("propagation");
	
	}

	@Override
	public String say(TokenSequence seq) {
		
		StringBuilder builder = new StringBuilder();
		
			seq.consume(KeyWordToken.METALINE);
		
			builder.append("- ");
			
			NameToken nameToken = seq.tryPoll(NameToken.class);
			
			builder.append(nameToken.getRaw());
			
			if(seq.isToken(0, KeyWordToken.OPEN_BLOCK)) {
				
				seq.consume(KeyWordToken.OPEN_BLOCK);
				
				builder.append(": ");
				
				NameToken roleName = seq.tryPoll(NameToken.class);
				
				builder.append(roleName.getRaw());
				
				while(!seq.isToken(0, KeyWordToken.CLOSE_BLOCK)) {
				
					seq.consume(KeyWordToken.TO);
				
					builder.append(" -> ");
					
					roleName = seq.tryPoll(NameToken.class);
				
					builder.append(roleName.getRaw());
				}
				
				seq.consume(KeyWordToken.CLOSE_BLOCK);
				
				builder.append("\n");
			}
			
			
		return builder.toString();
	}
}
