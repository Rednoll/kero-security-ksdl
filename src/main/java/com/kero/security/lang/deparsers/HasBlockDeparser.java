package com.kero.security.lang.deparsers;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.KeyWordToken;

public interface HasBlockDeparser<T> {

	public default TokenSequence deparseBlock(T node) {

		TokenSequence seq = new TokenSequence();

		int size = unitsCount(node);

		if(size == 0) return seq;
		
		seq.add(KeyWordToken.OPEN_BLOCK);
		
		for(int i = 0; i < size; i++) {
			
			seq.addAll(deparseBlockUnit(node, i));
		}
		
		seq.add(KeyWordToken.CLOSE_BLOCK);

		return seq;
	}

	public TokenSequence deparseBlockUnit(T node, int unitIndex);

	public int unitsCount(T node);
}
