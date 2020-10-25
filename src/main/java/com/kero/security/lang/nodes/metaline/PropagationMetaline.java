package com.kero.security.lang.nodes.metaline;

import java.util.Map;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.property.Property;
import com.kero.security.core.role.Role;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public class PropagationMetaline extends PropertyMetalineBase {

	private Map<String, String> propagationMap;
	
	public PropagationMetaline(Map<String, String> propagationMap) {
		
		this.propagationMap = propagationMap;
	}
	
	public void interpret(KeroAccessAgent manager, Property property) {
		
		propagationMap.forEach((fromName, toName)-> {
		
			Role from = manager.getOrCreateRole(fromName);
			Role to = manager.getOrCreateRole(toName);
			
			property.addRolePropagation(from, to);
		});
	}
	
	public Map<String, String> getPropagationMap() {
		
		return this.propagationMap;
	}
}
