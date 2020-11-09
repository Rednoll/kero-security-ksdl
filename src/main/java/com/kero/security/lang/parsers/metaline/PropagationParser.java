package com.kero.security.lang.parsers.metaline;

import java.util.HashMap;
import java.util.Map;

import com.kero.security.core.property.Property;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.exception.UnexpectedTokenException;
import com.kero.security.lang.nodes.metaline.PropagationMetaline;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public class PropagationParser extends MetalineParserBase<Property, PropagationMetaline> {
	
	public PropagationParser() {
		super("propagation");
		
	}

	@Override
	public boolean isMatch(Property property) {
		
		return !property.getLocalRolesPropagation().isEmpty();
	}
	
	@Override
	public boolean isMatch(TokenSequence tokens) {
		
		boolean superMatch = super.isMatch(tokens);
	
		if(!superMatch) return false;
		if(!tokens.isToken(2, KeyWordToken.OPEN_BLOCK)) return false;
		
		return true;
	}
	
	@Override
	public PropagationMetaline parse(Property property) {
	
		Map<String, String> propagationMap = new HashMap<>();
		
		property.getLocalRolesPropagation().forEach((from, to)-> {
			
			propagationMap.put(from.getName(), to.getName());
		});
		
		return new PropagationMetaline(propagationMap);
	}
	
	@Override
	public PropagationMetaline parse(TokenSequence tokens) throws UnexpectedTokenException {
		
		tokens.consume(KeyWordToken.METALINE);
		tokens.consume(NameToken.class);
		tokens.consume(KeyWordToken.OPEN_BLOCK);
		
		NameToken fromRoleName = tokens.tryPoll(NameToken.class);
		
		Map<String, String> propagationMap = new HashMap<>();
		
		while(tokens.peek() != KeyWordToken.CLOSE_BLOCK) {
			
			tokens.consume(KeyWordToken.TO);

			NameToken toRoleName = tokens.tryPoll(NameToken.class);
		
			propagationMap.put(fromRoleName.getRaw(), toRoleName.getRaw());
			
			fromRoleName = toRoleName;
		}
		
		tokens.consume(KeyWordToken.CLOSE_BLOCK);
		
		return new PropagationMetaline(propagationMap);
	}
}
