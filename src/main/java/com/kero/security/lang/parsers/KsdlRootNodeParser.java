package com.kero.security.lang.parsers;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.nodes.KsdlNode;

public interface KsdlRootNodeParser<S, T extends KsdlNode> extends KsdlNodeParser<S, T> {

	public boolean isMatch(TokenSequence tokens);
	
	public RootNodeList parse(KeroAccessAgent agent);
}
