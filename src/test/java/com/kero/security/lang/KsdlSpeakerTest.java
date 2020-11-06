package com.kero.security.lang;

import org.junit.jupiter.api.Test;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.DefaultAccessToken;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;
import com.kero.security.lang.tokens.RoleToken;

public class KsdlSpeakerTest {

	@Test
	public void say() {
		
		KsdlSpeaker speaker = KsdlSpeaker.getInstance();
	
		TokenSequence seq = new TokenSequence();
			seq.add(KeyWordToken.SCHEME);
			seq.add(new NameToken("TestScheme"));
			seq.add(DefaultAccessToken.DENY);
			seq.add(KeyWordToken.OPEN_BLOCK);
			seq.add(new NameToken("text"));
			seq.add(DefaultAccessToken.GRANT);
			seq.add(KeyWordToken.OPEN_BLOCK);
			seq.add(new RoleToken("OWNER", true));
			seq.add(new RoleToken("FRIEND", false));
			seq.add(KeyWordToken.CLOSE_BLOCK);
			seq.add(KeyWordToken.METALINE);
			seq.add(new NameToken("propagation"));
			seq.add(KeyWordToken.OPEN_BLOCK);
			seq.add(new NameToken("OWNER"));
			seq.add(KeyWordToken.FORWARD_DIRECTION);
			seq.add(new NameToken("FRIEND"));
			seq.add(KeyWordToken.CLOSE_BLOCK);
			seq.add(KeyWordToken.CLOSE_BLOCK);
	
		System.out.print(speaker.say(seq));
	}
}
