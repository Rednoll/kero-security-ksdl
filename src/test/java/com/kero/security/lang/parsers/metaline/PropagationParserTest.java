package com.kero.security.lang.parsers.metaline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.exception.UnexpectedTokenException;
import com.kero.security.lang.nodes.metaline.PropagationMetaline;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public class PropagationParserTest {

	@Test
	public void isMatch() {
		
		PropagationParser parser = new PropagationParser();
		
		TokenSequence seq = new TokenSequence();
			seq.put(0, KeyWordToken.METALINE);
			seq.put(1, new NameToken("propagation"));
			seq.put(2, KeyWordToken.OPEN_BLOCK);
		
		assertTrue(parser.isMatch(seq));
	
		seq = new TokenSequence();
			seq.put(0, new NameToken("propagation"));
			seq.put(1, KeyWordToken.OPEN_BLOCK);
	
		assertFalse(parser.isMatch(seq));
		
		seq = new TokenSequence();
			seq.put(0, KeyWordToken.METALINE);
			seq.put(1, new NameToken("propagation1"));
			seq.put(2, KeyWordToken.OPEN_BLOCK);

		assertFalse(parser.isMatch(seq));
		
		seq = new TokenSequence();
			seq.put(0, KeyWordToken.METALINE);
			seq.put(1, new NameToken("propagation"));
	
		assertFalse(parser.isMatch(seq));
	}
	
	@Test
	public void parse() throws UnexpectedTokenException {
		
		PropagationParser parser = new PropagationParser();
	
		TokenSequence seq = new TokenSequence();
			seq.put(0, KeyWordToken.METALINE);
			seq.put(1, new NameToken("propagation"));
			seq.put(2, KeyWordToken.OPEN_BLOCK);
			seq.put(3, new NameToken("OWNER"));
			seq.put(4, KeyWordToken.TO);
			seq.put(5, new NameToken("FRIEND"));
			seq.put(6, KeyWordToken.CLOSE_BLOCK);
		
		PropagationMetaline metaline = parser.parse(seq);
	
		assertEquals(metaline.getPropagationMap().get("OWNER"), "FRIEND");
	}
}
