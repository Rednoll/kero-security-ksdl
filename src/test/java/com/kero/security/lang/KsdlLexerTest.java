package com.kero.security.lang;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.DefaultAccessToken;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.KsdlToken;
import com.kero.security.lang.tokens.NameToken;
import com.kero.security.lang.tokens.RoleToken;

public class KsdlLexerTest {

	@Test
	public void test() {
		
		String data =
			"scheme TestScheme(D) {\n"
			+ "text(G): -OWNER \n"
			+ "- propagation: FRIEND -> ANY \n"
			+ "}";
		
		KsdlLexer lexer = KsdlLexer.getInstance();
		
		TokenSequence seq = lexer.tokenize(data);
		
		List<KsdlToken> values = new ArrayList(seq.values());
		
		assertTrue(values.get(0) == KeyWordToken.SCHEME);
		assertTrue(values.get(1) instanceof NameToken);
		assertTrue(values.get(2) == DefaultAccessToken.DENY);
		assertTrue(values.get(3) == KeyWordToken.OPEN_BLOCK);
		assertTrue(values.get(4) instanceof NameToken);
		assertTrue(values.get(5) == DefaultAccessToken.GRANT);
		assertTrue(values.get(6) == KeyWordToken.OPEN_BLOCK);
		assertTrue(values.get(7) instanceof RoleToken);
		assertTrue(values.get(8) == KeyWordToken.CLOSE_BLOCK);
		assertTrue(values.get(9) == KeyWordToken.METALINE);
		assertTrue(values.get(10) instanceof NameToken);
		assertTrue(values.get(11) == KeyWordToken.OPEN_BLOCK);
		assertTrue(values.get(12) instanceof NameToken);
		assertTrue(values.get(13) == KeyWordToken.TO);
		assertTrue(values.get(14) instanceof NameToken);
		assertTrue(values.get(15) == KeyWordToken.CLOSE_BLOCK);
		assertTrue(values.get(16) == KeyWordToken.CLOSE_BLOCK);
	}
}
