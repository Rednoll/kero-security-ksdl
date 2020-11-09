package com.kero.security.lang.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.kero.security.core.access.Access;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.exception.UnexpectedTokenException;
import com.kero.security.lang.nodes.DefaultAccessNode;
import com.kero.security.lang.nodes.PropertyNode;
import com.kero.security.lang.nodes.RoleNode;
import com.kero.security.lang.nodes.metaline.PropagationMetaline;
import com.kero.security.lang.tokens.DefaultAccessToken;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;
import com.kero.security.lang.tokens.RoleToken;

public class PropertyParserTest {
	
	@Test
	public void parse() throws UnexpectedTokenException {
		
		PropertyParser parser = new PropertyParser();
		
		TokenSequence seq = new TokenSequence();
			seq.put(0, new NameToken("text"));
			seq.put(1, DefaultAccessToken.GRANT);
			seq.put(2, KeyWordToken.OPEN_BLOCK);
			seq.put(3, new RoleToken("OWNER", true));
			seq.put(4, new RoleToken("FRIEND", false));
			seq.put(5, KeyWordToken.CLOSE_BLOCK);
			seq.put(6, KeyWordToken.METALINE);
			seq.put(7, new NameToken("propagation"));
			seq.put(8, KeyWordToken.OPEN_BLOCK);
			seq.put(9, new NameToken("OWNER"));
			seq.put(10, KeyWordToken.TO);
			seq.put(11, new NameToken("FRIEND"));
			seq.put(12, KeyWordToken.CLOSE_BLOCK);
			
		PropertyNode propNode = parser.parse(seq);
		
		assertEquals(propNode.getName(), "text");
		assertEquals(propNode.getDefaultAccess(), DefaultAccessNode.GRANT);
		assertTrue(propNode.getRoleRules().contains(new RoleNode("OWNER", Access.GRANT)));
		assertTrue(propNode.getRoleRules().contains(new RoleNode("FRIEND", Access.DENY)));
		
		PropagationMetaline metaline = (PropagationMetaline) propNode.getMetalines().get(0);
		
		assertEquals(metaline.getPropagationMap().get("OWNER"), "FRIEND");
	}
}
