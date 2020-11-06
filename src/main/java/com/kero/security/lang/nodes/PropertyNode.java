package com.kero.security.lang.nodes;

import java.util.Collections;
import java.util.List;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.property.Property;
import com.kero.security.core.role.storage.RoleStorage;
import com.kero.security.core.scheme.AccessScheme;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.nodes.metaline.PropertyMetalineBase;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public class PropertyNode extends KsdlNodeBase {

	public static PropertyNode EMPTY = new Empty();
	
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

	public TokenSequence tokenize() {
		
		TokenSequence seq = new TokenSequence();
			seq.add(new NameToken(this.getName()));
			seq.add(this.getDefaultAccess().tokenize());
			
			if(this.getRoleRules().size() > 0) {
				
				seq.add(KeyWordToken.OPEN_BLOCK);
				
				this.getRoleRules().forEach(ruleNode -> seq.add(ruleNode.tokenize()));
				
				seq.add(KeyWordToken.CLOSE_BLOCK);
			}
			
			this.getMetalines().forEach(line -> seq.add(line.tokenize()));
			
		return seq;
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
	
	private static class Empty extends PropertyNode {

		public Empty() {
			super(null, null, null, null);
			
		}
		
		public TokenSequence tokenize() {

			return new TokenSequence();
		}
		
		public void interpret(AccessScheme scheme) {

		}
		
		public List<PropertyMetalineBase> getMetalines() {
			
			return Collections.emptyList();
		}
		
		public List<RoleNode> getRoleRules() {
			
			return Collections.emptyList();
		}
		
		public DefaultAccessNode getDefaultAccess() {
			
			return null;
		}
		
		public String getName() {
			
			return null;
		}
	}
}
