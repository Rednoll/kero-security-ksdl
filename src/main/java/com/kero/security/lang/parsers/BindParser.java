package com.kero.security.lang.parsers;

import com.kero.security.core.scheme.AccessScheme;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.nodes.BindNode;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public class BindParser extends KsdlNodeParserBase<AccessScheme, BindNode> implements KsdlRootNodeParser<AccessScheme, BindNode> {

	@Override
	public boolean isMatch(TokenSequence tokens) {
		
		if(!tokens.isToken(0, KeyWordToken.BIND)) return false;
		if(!tokens.isToken(1, NameToken.class)) return false;
		if(!tokens.isToken(2, KeyWordToken.TO)) return false;
		if(!tokens.isToken(3, NameToken.class)) return false;
		
		return true;
	}

	@Override
	public BindNode parse(TokenSequence tokens) {
		
		tokens.consume(KeyWordToken.BIND);
		
		NameToken name = tokens.tryPoll(NameToken.class);
		
		tokens.consume(KeyWordToken.TO);
		
		NameToken className = tokens.tryPoll(NameToken.class);
		
		return new BindNode(name.getRaw(), className.getRaw());
	}

	@Override
	public boolean isMatch(Object obj) {
		
		return obj instanceof AccessScheme;
	}
	
	@Override
	public BindNode parse(AccessScheme obj) {
		
		String name = obj.getName();
		Class<?> clazz = obj.getTypeClass();
		
		return new BindNode(name, clazz.getCanonicalName());
	}

	@Override
	public Class<BindNode> getNodeClass() {
		
		return BindNode.class;
	}
}
