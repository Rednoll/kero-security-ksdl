package com.kero.security.lang.parsers.metaline;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.kero.security.core.property.Property;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.nodes.metaline.MetalineNode;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public class MetalineParserBaseTest {
	
	@Test
	public void test() {
		
		TestMetalineParser parser = new TestMetalineParser();
	
		TokenSequence seq = new TokenSequence();
			seq.put(0, KeyWordToken.METALINE);
			seq.put(1, new NameToken("test"));
		
		assertTrue(parser.isMatch(seq));
	}
	
	public static class TestMetalineParser extends MetalineParserBase<Property, TestNode> {
		
		public TestMetalineParser() {
			super("test");
		}

		@Override
		public TestNode parse(TokenSequence tokens) {
			
			return null;
		}

		@Override
		public TestNode parse(Property property) {
			
			return null;
		}

		@Override
		public boolean isMatch(Property obj) {
			
			return false;
		}
	}
	
	public static class TestNode implements MetalineNode {

		@Override
		public String toText() {
			
			return null;
		}

	}
}
