package com.kero.security.lang.nodes.metaline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.property.Property;
import com.kero.security.core.role.Role;

public class PropagationMetaline extends PropertyMetalineBase {

	public static TokenizeStrategy TOTEXT_STYLE = DefaultTokenizeStrategy.FULL_BRANCHES;
	
	private Map<String, String> propagationMap;
	
	public PropagationMetaline(Map<String, String> propagationMap) {
		
		this.propagationMap = propagationMap;
	}
	
	@Override
	public String toText() {
		
		return TOTEXT_STYLE.toText(this.propagationMap);
	}

	@Override
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

	public static interface TokenizeStrategy {
		
		public String toText(Map<String, String> propagationMap);
	}
	
	public static enum DefaultTokenizeStrategy implements TokenizeStrategy {
		
		PAIR {

			@Override
			public String toText(Map<String, String> propagationMap) {
				
				StringBuilder builder = new StringBuilder();
					
					propagationMap.forEach((from, to)-> {
						
						builder.append("- propagation: "+ from + " -> " + to + "\n");
					});
				
				return builder.toString();
			}
		},
		FULL_BRANCHES {
			
			@Override
			public String toText(Map<String, String> propagationMap) {
				
				StringBuilder builder = new StringBuilder();
				
					List<String> roots = new ArrayList<>();
					
						propagationMap.forEach((from, to)-> {
							
							if(!propagationMap.values().contains(from)) {
								
								roots.add(from);
							}
						});
					
					for(String root : roots) {
						
						String head = propagationMap.get(root);
						
						builder.append("- propagation: "+ root + " -> " + head);
						
						while(propagationMap.containsKey(head)) {
							
							head = propagationMap.get(head);
						
							builder.append(" -> " + head);
						}
						
						builder.append("\n");
					}
					
				return builder.toString();
			}
		};
	}
}
