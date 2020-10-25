package com.kero.security.lang.nodes;

import java.util.List;
import java.util.Set;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.property.Property;
import com.kero.security.core.role.storage.RoleStorage;
import com.kero.security.core.scheme.AccessScheme;
import com.kero.security.lang.nodes.metaline.PropertyMetalineBase;

public class PropertyNode extends KsdlNodeBase {

	private String name;
	private DefaultAccessNode defaultAccess;
	
	private List<RoleNode> roleRules;
	
	private List<PropertyMetalineBase> metalines;
	
	public PropertyNode(String name, DefaultAccessNode defaultAccess, List<RoleNode> roleRules, List<PropertyMetalineBase> metalines) {
		
		this.name = name;
		this.defaultAccess = defaultAccess;
		
		this.roleRules = roleRules;
		this.metalines = metalines;
	}

	public void interpret(AccessScheme scheme) {

		KeroAccessAgent manager = scheme.getAgent();
		RoleStorage roleStorage = manager.getRoleStorage();
		
		Property prop = scheme.getOrCreateLocalProperty(this.name);

			defaultAccess.interpret(manager, prop);
			
			roleRules.forEach(rule -> rule.interpret(prop, roleStorage));
			
			metalines.forEach(metaline -> metaline.interpret(manager, prop));
	}
	
	public List<PropertyMetalineBase> getMetalines() {
		
		return this.metalines;
	}
	
	public List<RoleNode> getRoleRules() {
		
		return this.roleRules;
	}
	
	public DefaultAccessNode getDefaultAccess() {
		
		return this.defaultAccess;
	}
	
	public String getName() {
		
		return this.name;
	}
}
