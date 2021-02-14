package com.kero.security.core.scheme.strategy;

import java.util.HashMap;
import java.util.Map;

import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.nodes.BindNode;

public class KsdlBindingSchemeNamingStrategy extends DefaultAccessSchemeNamingStrategy implements AccessSchemeNamingStrategy {

	private Map<Class, String> names = new HashMap<>();
	
	public KsdlBindingSchemeNamingStrategy(RootNodeList roots) {
		
		roots.getAllOf(BindNode.class).forEach(node -> {
			
			try {
				
				names.put(Class.forName(node.getClassName()), node.getName());
			}
			catch(ClassNotFoundException e) {
				
				throw new RuntimeException(e);
			}
		});
	}
	
	@Override
	public String getName(Class<?> rawType) {
		
		return this.names.getOrDefault(rawType, super.getName(rawType));
	}
}
