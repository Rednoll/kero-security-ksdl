package com.kero.security.lang.parsers;

import java.util.ArrayList;
import java.util.List;

import com.kero.security.core.access.Access;
import com.kero.security.core.property.Property;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.exception.UnexpectedTokenException;
import com.kero.security.lang.nodes.DefaultAccessNode;
import com.kero.security.lang.nodes.PropertyNode;
import com.kero.security.lang.nodes.RoleNode;
import com.kero.security.lang.nodes.metaline.PropertyMetalineBase;
import com.kero.security.lang.parsers.metaline.HasMetalines;
import com.kero.security.lang.parsers.metaline.MetalineParser;
import com.kero.security.lang.parsers.metaline.PropagationParser;
import com.kero.security.lang.tokens.DefaultAccessToken;
import com.kero.security.lang.tokens.NameToken;
import com.kero.security.lang.tokens.RoleToken;

public class PropertyParser extends KsdlNodeParserBase<Property, PropertyNode> implements HasBlock<RoleToken>, HasMetalines<PropertyMetalineBase> {

	private List<MetalineParser<Property, ? extends PropertyMetalineBase>> metalineParsers = new ArrayList<>();
	
	public PropertyParser() {
	
		metalineParsers.add(new PropagationParser());
	}
	
	public PropertyNode parse(Property property) {
		
		String name = property.getName();
		DefaultAccessNode defaultAccess = DefaultAccessNode.fromAccess(property.getLocalDefaultAccess());
		
		List<RoleNode> roleRules = new ArrayList<>();
		
			property.getLocalGrantRoles().forEach(role -> 
				roleRules.add(new RoleNode(role.getName(), Access.GRANT)
			));
			
			property.getLocalDenyRoles().forEach(role -> 
				roleRules.add(new RoleNode(role.getName(), Access.DENY)
			));
		
		List<PropertyMetalineBase> metalines = new ArrayList<>();
		
			metalineParsers.forEach(parser -> {
				
				if(parser.isMatch(property)) {
					
					metalines.add(parser.parse(property));
				}
			});
			
		if(defaultAccess == DefaultAccessNode.EMPTY && roleRules.isEmpty() && metalines.isEmpty()) {
			
			return PropertyNode.EMPTY;
		}
		
		return new PropertyNode(name, defaultAccess, roleRules, metalines);
	}
	
	public PropertyNode parse(TokenSequence tokens) throws UnexpectedTokenException {
		
		NameToken nameToken = tokens.tryPoll(NameToken.class);
		
		DefaultAccessToken defaultRuleToken = tokens.tryGetOrDefault(DefaultAccessToken.EMPTY);
		
		List<RoleToken> roles = this.parseBlock(tokens);
		
		List<RoleNode> roleRules = new ArrayList<>();
		
			for(RoleToken role : roles) {
				
				roleRules.add(role.toNode());
			}
		
		List<PropertyMetalineBase> metalines = this.parseMetalines(tokens);
		
		String name = nameToken.getRaw();
		
		DefaultAccessNode defaultAccess = defaultRuleToken.toNode();

		return new PropertyNode(name, defaultAccess, roleRules, metalines);
	}

	@Override
	public List<MetalineParser<Property, ? extends PropertyMetalineBase>> getMetalineParsers() {
		
		return metalineParsers;
	}
	
	@Override
	public RoleToken parseBlockUnit(TokenSequence tokens) throws UnexpectedTokenException {
		
		return tokens.tryPoll(RoleToken.class);
	}
}
