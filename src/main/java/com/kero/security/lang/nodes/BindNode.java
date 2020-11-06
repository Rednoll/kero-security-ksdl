package com.kero.security.lang.nodes;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.NameToken;

public class BindNode extends KsdlNodeBase implements KsdlRootNode {

	private String name;
	private String className;
	
	public BindNode(String name, String className) {
		
		this.name = name;
		this.className = className;
	}
	
	@Override
	public TokenSequence tokenize() {
		
		TokenSequence seq = new TokenSequence();
		
			seq.add(KeyWordToken.BIND);
			seq.add(new NameToken(this.name));
			seq.add(KeyWordToken.TO);
			seq.add(new NameToken(this.className));
		
		return seq;
	}
}
