package com.kero.security.lang.parsers;

import java.util.ArrayList;
import java.util.List;

import com.kero.security.core.scheme.AccessScheme;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.nodes.DefaultAccessNode;
import com.kero.security.lang.nodes.PropertyNode;
import com.kero.security.lang.nodes.SchemeNode;
import com.kero.security.lang.tokens.DefaultAccessToken;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public class SchemeParser extends KsdlNodeParserBase<AccessScheme, SchemeNode> implements KsdlRootNodeParser<AccessScheme, SchemeNode>, HasBlock<PropertyNode> {

	private PropertyParser propertyParser = new PropertyParser();

	@Override
	public boolean isMatch(Object obj) {
		
		return obj instanceof AccessScheme;
	}
	
	@Override
	public SchemeNode parse(AccessScheme scheme) {
		
		String typeName = scheme.getName();
		DefaultAccessNode defaultRule = DefaultAccessNode.fromAccess(scheme.getDefaultAccess());
		
		List<PropertyNode> props = new ArrayList<>();
		
			scheme.getLocalProperties().forEach(prop -> props.add(propertyParser.parse(prop)));
		
		return new SchemeNode(typeName, defaultRule, props);
	}
	
	@Override
	public boolean isMatch(TokenSequence tokens) {
		
		if(!tokens.isToken(0, KeyWordToken.SCHEME)) return false;
		if(!tokens.isToken(1, NameToken.class)) return false;
		
		return true;
	}
	
	@Override
	public SchemeNode parse(TokenSequence tokens) {
	
		tokens.poll(); //SCHEME
		
		NameToken nameToken = (NameToken) tokens.poll();
		
		DefaultAccessToken defaultRuleToken = tokens.tryGetOrDefault(DefaultAccessToken.EMPTY);
		
		List<PropertyNode> props = this.parseBlock(tokens);
		String typeName = nameToken.getRaw();
		DefaultAccessNode defaultRule = defaultRuleToken.toNode();
		
		return new SchemeNode(typeName, defaultRule, props);
	}
	
	@Override
	public PropertyNode parseBlockUnit(TokenSequence tokens) {
		
		return propertyParser.parse(tokens);
	}
}
