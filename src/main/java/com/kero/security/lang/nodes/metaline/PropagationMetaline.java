package com.kero.security.lang.nodes.metaline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.property.Property;
import com.kero.security.core.role.Role;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public class PropagationMetaline extends PropertyMetalineBase {

	public static TokenizeStrategy TOKENIZE_STRATEGY = DefaultTokenizeStrategy.FULL_BRANCHES;
	
	private Map<String, String> propagationMap;
	
	public PropagationMetaline(Map<String, String> propagationMap) {
		
		this.propagationMap = propagationMap;
	}
	
	public TokenSequence tokenize() {
		
		TokenSequence seq = new TokenSequence();
		
		if(propagationMap.isEmpty()) return seq;
		
		seq.addAll(TOKENIZE_STRATEGY.tokenize(propagationMap));

		return seq;
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

	public static interface TokenizeStrategy {
		
		public TokenSequence tokenize(Map<String, String> propagationMap);
	}
	
	public static enum DefaultTokenizeStrategy implements TokenizeStrategy {
		
		PAIR {

			@Override
			public TokenSequence tokenize(Map<String, String> propagationMap) {
				
				TokenSequence seq = new TokenSequence();
				
				propagationMap.forEach((from, to)-> {
					
					seq.add(KeyWordToken.METALINE);
					seq.add(new NameToken("propagation"));
					seq.add(KeyWordToken.OPEN_BLOCK);
				
					seq.add(new NameToken(from));
					seq.add(KeyWordToken.FORWARD_DIRECTION);
					seq.add(new NameToken(to));
				
					seq.add(KeyWordToken.CLOSE_BLOCK);
				});
				
				return seq;
			}
		},
		FULL_BRANCHES {
			
			@Override
			public TokenSequence tokenize(Map<String, String> propagationMap) {
				
				TokenSequence seq = new TokenSequence();
				
				List<String> roots = new ArrayList<>();
				
					propagationMap.forEach((from, to)-> {
						
						if(!propagationMap.values().contains(from)) {
							
							roots.add(from);
						}
					});
				
				for(String root : roots) {
					
					String head = propagationMap.get(root);
					
					seq.add(KeyWordToken.METALINE);
					seq.add(new NameToken("propagation"));
					seq.add(KeyWordToken.OPEN_BLOCK);
					
					seq.add(new NameToken(root));
					seq.add(KeyWordToken.FORWARD_DIRECTION);
					seq.add(new NameToken(head));
					
					while(propagationMap.containsKey(head)) {
						
						head = propagationMap.get(head);
						
						seq.add(KeyWordToken.FORWARD_DIRECTION);
						seq.add(new NameToken(head));
					}
					
					seq.add(KeyWordToken.CLOSE_BLOCK);
				}
					
				return seq;
			}
		};
	}
}
