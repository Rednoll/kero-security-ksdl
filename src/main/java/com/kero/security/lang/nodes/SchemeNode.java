package com.kero.security.lang.nodes;

import java.util.Collections;
import java.util.List;

import com.kero.security.core.access.Access;
import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.scheme.AccessScheme;
import com.kero.security.lang.KsdlSpeaker;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public class SchemeNode extends KsdlNodeBase implements KsdlRootNode {

	public static final SchemeNode EMPTY = new Empty();
	
	protected String name;
	
	protected String parentName;
	
	protected DefaultAccessNode defaultAccess;
	
	protected List<PropertyNode> properties;
	
	public SchemeNode(String name, String parentName, DefaultAccessNode defaultAccess, List<PropertyNode> properties) {
		
		this.name = name;
		this.parentName = parentName;
		this.defaultAccess = defaultAccess;
		this.properties = properties;
	}
	
	public SchemeNode(String name, DefaultAccessNode defaultAccess, List<PropertyNode> properties) {
		this(name, null, defaultAccess, properties);
	
	}
	
	public String toText() {
		
		StringBuilder builder = new StringBuilder();
		
			builder.append("\n");
		
			builder.append("scheme "+this.name);
		
			builder.append(this.defaultAccess.toText());
			
			builder.append(" {\n\n");
			
				StringBuilder propertiesBuilder = new StringBuilder();
				
					this.properties.forEach(property -> propertiesBuilder.append(property.toText()));
				
				builder.append(KsdlSpeaker.getInstance().addIndentTo(propertiesBuilder.toString()));
			
			builder.append("}\n");
			
		return builder.toString();
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