package com.kero.security.lang.parsers.metaline;

import com.kero.security.core.property.Property;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.exception.UnexpectedTokenException;
import com.kero.security.lang.nodes.metaline.MetalineNode;

public interface MetalineParser<S, T extends MetalineNode> {

	public boolean isMatch(S obj);
	public boolean isMatch(TokenSequence tokens);

	public T parse(Property property);
	public T parse(TokenSequence tokens) throws UnexpectedTokenException;
}
