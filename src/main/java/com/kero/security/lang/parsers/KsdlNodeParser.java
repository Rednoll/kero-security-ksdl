package com.kero.security.lang.parsers;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.exception.UnexpectedTokenException;
import com.kero.security.lang.nodes.KsdlNode;

public interface KsdlNodeParser<S, T extends KsdlNode> {

	public T parse(S obj);
	public T parse(TokenSequence tokens) throws UnexpectedTokenException;
}
