package com.kero.security.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.exception.UnexpectedTokenException;
import com.kero.security.lang.nodes.PropertyNode;
import com.kero.security.lang.nodes.SchemeNode;
import com.kero.security.lang.nodes.metaline.PropagationMetaline;
import com.kero.security.lang.nodes.metaline.PropertyMetalineBase;
import com.kero.security.lang.tokens.DefaultAccessToken;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;
import com.kero.security.lang.tokens.RoleToken;

public class KsdlParserTest {

	@Test
	public void parse() throws UnexpectedTokenException {
		
		KsdlParser parser = KsdlParser.getInstance();
	
		TokenSequence seq = new TokenSequence();
			seq.put(0, KeyWordToken.SCHEME);
			seq.put(1, new NameToken("TestScheme"));
			seq.put(2, DefaultAccessToken.DENY);
			seq.put(3, KeyWordToken.OPEN_BLOCK);
			seq.put(4, new NameToken("text"));
			seq.put(5, DefaultAccessToken.GRANT);
			seq.put(6, KeyWordToken.OPEN_BLOCK);
			seq.put(7, new RoleToken("OWNER", true));
			seq.put(8, new RoleToken("FRIEND", false));
			seq.put(9, KeyWordToken.CLOSE_BLOCK);
			seq.put(10, KeyWordToken.METALINE);
			seq.put(11, new NameToken("propagation"));
			seq.put(12, KeyWordToken.OPEN_BLOCK);
			seq.put(13, new NameToken("OWNER"));
			seq.put(14, KeyWordToken.TO);
			seq.put(15, new NameToken("FRIEND"));
			seq.put(16, KeyWordToken.CLOSE_BLOCK);
			seq.put(17, KeyWordToken.CLOSE_BLOCK);
			
		RootNodeList roots = parser.parse(seq);
		
		assertEquals(roots.size(), 1);
		assertTrue(roots.get(0) instanceof SchemeNode);
		
		SchemeNode schemeNode = (SchemeNode) roots.get(0);
	
		List<PropertyNode> propertyNodes = schemeNode.getProperties();
	
		assertEquals(propertyNodes.size(), 1);
		
		PropertyNode propertyNode = propertyNodes.get(0);
		
		List<PropertyMetalineBase> metalineNodes = propertyNode.getMetalines();
		
		assertEquals(metalineNodes.size(), 1);
		assertTrue(metalineNodes.get(0) instanceof PropagationMetaline);
	}
}
