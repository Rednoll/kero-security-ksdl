package com.kero.security.ksdl.writer;

import org.junit.jupiter.api.Test;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.DefaultAccessToken;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;
import com.kero.security.lang.tokens.RoleToken;

public class TextualWriterTest {

	@Test
	public void test() {
		
		TextualWriter writer = new TextualWriter(null);
	
		TokenSequence seq = new TokenSequence();
			seq.add(KeyWordToken.SCHEME);
			seq.add(new NameToken("TestScheme"));
			seq.add(DefaultAccessToken.DENY);
			seq.add(KeyWordToken.OPEN_BLOCK);
			seq.add(new NameToken("testProp"));
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
			seq.add(KeyWordToken.FORWARD_DIRECTION);
			seq.add(new NameToken("COMMON"));
			seq.add(KeyWordToken.CLOSE_BLOCK);
			seq.add(KeyWordToken.CLOSE_BLOCK);
			
		System.out.println(writer.delex(seq));
	}
}
