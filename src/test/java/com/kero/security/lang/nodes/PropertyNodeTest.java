package com.kero.security.lang.nodes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.kero.security.core.access.Access;
import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.agent.KeroAccessAgentFactoryImpl;
import com.kero.security.core.property.Property;
import com.kero.security.core.scheme.AccessScheme;

public class PropertyNodeTest {

	@Test
	public void interpret() {
		
		List<RoleNode> roles = new ArrayList<>();
			roles.add(new RoleNode("OWNER", Access.GRANT));
			roles.add(new RoleNode("ANY", Access.DENY));
		
		PropertyNode propertyNode = new PropertyNode("text", DefaultAccessNode.GRANT, roles, Collections.emptyList());
		
		KeroAccessAgent agent = new KeroAccessAgentFactoryImpl().create();
		
		AccessScheme scheme = agent.getOrCreateScheme(TestClass.class);
	
		propertyNode.interpret(scheme);
		
		Property property = scheme.getLocalProperty("text");
	
		assertEquals(property.getDefaultAccess(), Access.GRANT);
		assertTrue(property.getLocalGrantRoles().contains(agent.getRole("OWNER")));
		assertTrue(property.getLocalDenyRoles().contains(agent.getRole("ANY")));
	}
	
	public static class TestClass {}
}
