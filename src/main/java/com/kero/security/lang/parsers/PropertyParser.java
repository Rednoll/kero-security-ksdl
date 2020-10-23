package com.kero.security.lang.parsers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.nodes.DefaultAccessNode;
import com.kero.security.lang.nodes.PropertyNode;
import com.kero.security.lang.nodes.metaline.PropertyMetalineBase;
import com.kero.security.lang.parsers.metaline.HasMetalines;
import com.kero.security.lang.parsers.metaline.MetalineParser;
import com.kero.security.lang.parsers.metaline.PropagationParser;
import com.kero.security.lang.tokens.DefaultAccessToken;
import com.kero.security.lang.tokens.NameToken;
import com.kero.security.lang.tokens.RoleToken;

public class PropertyParser extends KsdlNodeParserBase<PropertyNode> implements HasBlock<RoleToken>, HasMetalines<PropertyMetalineBase> {

	private List<MetalineParser<? extends PropertyMetalineBase>> metalineParsers = new ArrayList<>();
	
	public PropertyParser() {
	
		metalineParsers.add(new PropagationParser());
	}
	
	public PropertyNode parse(TokenSequence tokens) {
		
		NameToken nameToken = (NameToken) tokens.poll();
		
		DefaultAccessToken defaultRuleToken = tokens.tryGetOrDefault(DefaultAccessToken.EMPTY);
		
		Set<String> grantRoles = new HashSet<>();
		Set<String> denyRoles = new HashSet<>();
		
		List<RoleToken> roles = this.parseBlock(tokens);
		
		for(RoleToken role : roles) {
			
			if(role.getAccessible()) {
				
				grantRoles.add(role.getRoleName());
			}
			else {
				
				denyRoles.add(role.getRoleName());
			}
		}
		
		List<PropertyMetalineBase> metalines = this.parseMetalines(tokens);
		
		String name = nameToken.getRaw();
		
		DefaultAccessNode defaultRule = defaultRuleToken.toNode();

		return new PropertyNode(name, defaultRule, grantRoles, denyRoles, metalines);
	}

	@Override
	public List<MetalineParser<? extends PropertyMetalineBase>> getMetalineParsers() {
		
		return metalineParsers;
	}
	
	@Override
	public RoleToken parseBlockUnit(TokenSequence tokens) {
		
		return (RoleToken) tokens.poll();
	}
}
