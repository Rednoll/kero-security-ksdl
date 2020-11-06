package com.kero.security.lang.nodes;

public class BindNode extends KsdlNodeBase implements KsdlRootNode {

	private String name;
	private String className;
	
	public BindNode(String name, String className) {
		
		this.name = name;
		this.className = className;
	}
	
	@Override
	public String toText() {
		
		StringBuilder builder = new StringBuilder();
			builder.append("bind ");
			builder.append(this.name);
			builder.append(" to ");
			builder.append(this.className);	
			
		return builder.toString();
	}
}
