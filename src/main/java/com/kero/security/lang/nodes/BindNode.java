package com.kero.security.lang.nodes;

import com.kero.security.core.agent.KeroAccessAgent;

public class BindNode extends KsdlNodeBase implements KsdlRootNode {

	protected String name;
	protected String className;
	
	public BindNode(String name, String className) {
		
		this.name = name;
		this.className = className;
	}
	
	public void interpret(KeroAccessAgent manager) {
		
		try {
			
			Class<?> type = Class.forName(this.className);
		
			manager.setTypeName(this.name, type);
		}
		catch(ClassNotFoundException e) {
			
			e.printStackTrace();
		}
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
	
	public String getName() {
		
		return this.name;
	}
	
	public String getClassName() {
		
		return this.className;
	}
}
