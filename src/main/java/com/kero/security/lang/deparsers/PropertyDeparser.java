package com.kero.security.lang.deparsers;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.nodes.PropertyNode;
import com.kero.security.lang.tokens.NameToken;

public class PropertyDeparser implements HasBlockDeparser<PropertyNode> {

	public TokenSequence deparse(PropertyNode node) {
		
		TokenSequence seq = new TokenSequence();
			seq.add(new NameToken(node.getName()));
			seq.add(node.getDefaultAccess().toToken());
			seq.addAll(this.deparseBlock(node));
			
			//Metalines
			
		return seq;
	}
	
	@Override
	public TokenSequence deparseBlockUnit(PropertyNode node, int unitIndex) {
		
		TokenSequence seq = new TokenSequence();
			seq.add(node.getRoleRules().get(unitIndex).toToken());
		
		return seq;
	}

	@Override
	public int unitsCount(PropertyNode node) {
		
		return node.getRoleRules().size();
	}	
}