package com.kero.security.lang.parsers.metaline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kero.security.core.property.Property;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.exception.UnexpectedTokenException;
import com.kero.security.lang.nodes.metaline.MetalineNode;
import com.kero.security.lang.tokens.KeyWordToken;

public class HasMetalinesTest {

	@Test
	public void test() throws UnexpectedTokenException {
		
		MetalineNode node1 = Mockito.mock(MetalineNode.class);
		MetalineNode node2 = Mockito.mock(MetalineNode.class);
		
		CloseTestParser parser1 = Mockito.mock(CloseTestParser.class, Mockito.CALLS_REAL_METHODS);
			parser1.setNode(node1);
		
		OpenTestParser parser2 = Mockito.mock(OpenTestParser.class, Mockito.CALLS_REAL_METHODS);
			parser2.setNode(node2);
		
		List<MetalineParser> parsers = new ArrayList<>();
			parsers.add(parser1);
			parsers.add(parser2);
			
		SomeParser hasMetalines = new SomeParser(parsers);
		
		TokenSequence seq = new TokenSequence();
			seq.put(0, KeyWordToken.METALINE);
			seq.put(1, KeyWordToken.OPEN_BLOCK);
			seq.put(2, KeyWordToken.OPEN_BLOCK);
			seq.put(3, KeyWordToken.OPEN_BLOCK);
			seq.put(4, KeyWordToken.METALINE);
			seq.put(5, KeyWordToken.CLOSE_BLOCK);
			seq.put(6, KeyWordToken.CLOSE_BLOCK);
			seq.put(7, KeyWordToken.CLOSE_BLOCK);
		
		List<MetalineNode> nodes = hasMetalines.parseMetalines(seq);
	
		assertEquals(nodes.size(), 2);
		assertTrue(nodes.contains(node1));
		assertTrue(nodes.contains(node2));
		
		Mockito.verify(parser1, Mockito.times(1)).parse(seq);
		Mockito.verify(parser2, Mockito.times(1)).parse(seq);
	}
	
	public static class SomeParser implements HasMetalines {

		private List<MetalineParser> parsers;
		
		public SomeParser(List<MetalineParser> parsers) {
			
			this.parsers = parsers;
		}
		
		@Override
		public List getMetalineParsers() {

			return this.parsers;
		}
	}
	
	public static class CloseTestParser implements MetalineParser {

		private MetalineNode node;
		
		@Override
		public boolean isMatch(TokenSequence tokens) {
			
			return tokens.isToken(1, KeyWordToken.CLOSE_BLOCK);
		}

		@Override
		public MetalineNode parse(Property property) {
			
			return null;
		}
		
		@Override
		public MetalineNode parse(TokenSequence tokens) throws UnexpectedTokenException {
			
			tokens.consume(KeyWordToken.METALINE);
			tokens.consume(KeyWordToken.CLOSE_BLOCK);
			tokens.consume(KeyWordToken.CLOSE_BLOCK);
			tokens.consume(KeyWordToken.CLOSE_BLOCK);
			
			return node;
		}
		
		public void setNode(MetalineNode node) {
			
			this.node = node;
		}

		@Override
		public boolean isMatch(Object obj) {
			
			return false;
		}
	}
	
	public static class OpenTestParser implements MetalineParser {

		private MetalineNode node;
		
		@Override
		public boolean isMatch(TokenSequence tokens) {
		
			return tokens.isToken(1, KeyWordToken.OPEN_BLOCK);
		}
		
		@Override
		public MetalineNode parse(Property property) {
			
			return null;
		}

		@Override
		public MetalineNode parse(TokenSequence tokens) throws UnexpectedTokenException {
		
			tokens.consume(KeyWordToken.METALINE);
			tokens.consume(KeyWordToken.OPEN_BLOCK);
			tokens.consume(KeyWordToken.OPEN_BLOCK);
			tokens.consume(KeyWordToken.OPEN_BLOCK);
			
			return node;
		}
		
		public void setNode(MetalineNode node) {
			
			this.node = node;
		}

		@Override
		public boolean isMatch(Object obj) {
			
			return false;
		}
	}
}
