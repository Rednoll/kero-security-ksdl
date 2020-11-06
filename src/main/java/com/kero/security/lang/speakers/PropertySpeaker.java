package com.kero.security.lang.speakers;

import java.util.HashSet;
import java.util.Set;

import com.kero.security.lang.KsdlSpeaker;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.speakers.metalines.MetalineSpeaker;
import com.kero.security.lang.speakers.metalines.PropagationSpeaker;
import com.kero.security.lang.tokens.DefaultAccessToken;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;
import com.kero.security.lang.tokens.RoleToken;

public class PropertySpeaker implements Speaker {
	
	private Set<MetalineSpeaker> metalineSpeakers = new HashSet<>();
	
	public PropertySpeaker() {
		
		this.metalineSpeakers.add(new PropagationSpeaker());
	}
	
	public boolean isMatch(TokenSequence seq) {
		
		if(!seq.isToken(0, NameToken.class)) return false;
		
		return true;
	}
	
	public String say(TokenSequence seq) {
		
		StringBuilder builder = new StringBuilder();
		
			NameToken nameToken = seq.tryPoll(NameToken.class);
		
			builder.append(nameToken.getRaw());
			
			DefaultAccessToken accessToken = seq.tryGetOrDefault(DefaultAccessToken.EMPTY);
			
			builder.append(accessToken.toText());
			
			if(seq.isToken(0, KeyWordToken.OPEN_BLOCK)) {
				
				seq.poll();
				
				builder.append(":");
				
				while(seq.isToken(0, RoleToken.class)) {
					
					RoleToken roleToken = seq.tryPoll(RoleToken.class);
					
					builder.append(" "+roleToken.toText());
				}
				
				seq.consume(KeyWordToken.CLOSE_BLOCK);
				
				builder.append("\n");
			}
			
			while(seq.isToken(0, KeyWordToken.METALINE)) {
				
				this.metalineSpeakers.forEach(speaker -> {
					
					if(speaker.isMatch(seq)) {
						
						String metaline = speaker.say(seq);
					
						metaline = KsdlSpeaker.getInstance().addIndentTo(metaline);

						builder.append(metaline);
					}
				});
			}
			
		return builder.toString();
	}
}
