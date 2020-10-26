package com.kero.security.lang.nodes;

import java.util.Collections;
import java.util.List;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.scheme.AccessScheme;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public class SchemeNode extends KsdlNodeBase implements KsdlRootNode {

	public static final SchemeNode EMPTY = new Empty();
	
	private String name;
	
	private DefaultAccessNode defaultAccess;
	
	private List<PropertyNode> properties;
	
	public SchemeNode(String name, DefaultAccessNode defaultAccess, List<PropertyNode> properties) {
		
		this.name = name;
		this.defaultAccess = defaultAccess;
		this.properties = properties;
	}
	
	@Override
	public TokenSequence tokenize() {
		
		TokenSequence seq = new TokenSequence();
			seq.add(KeyWordToken.SCHEME);
			seq.add(new NameToken(this.name));
			seq.add(this.defaultAccess.tokenize());
			
			if(this.properties.size() > 0) {
				
				seq.add(KeyWordToken.OPEN_BLOCK);
				
				this.properties.forEach(prop -> seq.add(prop.tokenize()));
				
				seq.add(KeyWordToken.CLOSE_BLOCK);
			}
			
		return seq;
	}
	
	public void interpret(KeroAccessAgent manager) {
		
		AccessScheme scheme = manager.getSchemeByName(this.name);
	
		this.interpret(scheme);
	}
	
	public void interpret(AccessScheme scheme) {

		defaultAccess.interpret(scheme.getAgent(), scheme);
		
		properties.forEach((prop)-> prop.interpret(scheme));
	}
	
	public List<PropertyNode> getProperties() {
		
		return this.properties;
	}
	
	public DefaultAccessNode getDefaultAccess() {
		
		return this.defaultAccess;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	private static class Empty extends SchemeNode {

		public Empty() {
			super("", DefaultAccessNode.EMPTY, Collections.emptyList());
		
		}
		
		@Override
		public void interpret(KeroAccessAgent manager) {
			
		}
		
		@Override
		public void interpret(AccessScheme scheme) {
			
		}
	}
}