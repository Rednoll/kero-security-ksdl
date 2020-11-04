package com.kero.security.lang.parsers;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.nodes.KsdlRootNode;

public interface KsdlRootNodeParser<S, T extends KsdlRootNode> extends KsdlNodeParser<S, T> {

	public boolean isMatch(Object obj);
	public boolean isMatch(TokenSequence tokens);
	
	public T parse(S obj);
}
