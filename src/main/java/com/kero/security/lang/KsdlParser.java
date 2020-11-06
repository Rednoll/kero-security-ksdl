package com.kero.security.lang;

import java.util.ArrayList;
import java.util.List;

import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.nodes.KsdlRootNode;
import com.kero.security.lang.parsers.BindParser;
import com.kero.security.lang.parsers.KsdlRootNodeParser;
import com.kero.security.lang.parsers.SchemeParser;

public class KsdlParser {

	private static final KsdlParser INSTANCE = new KsdlParser();
	
	protected List<KsdlRootNodeParser<?, ? extends KsdlRootNode>> parsers;
	
	private KsdlParser() {
		
		this.parsers = new ArrayList<>();
			parsers.add(new SchemeParser());
			parsers.add(new BindParser());
	}
	
	public <T extends KsdlRootNode> T parse(Object obj, Class<T> targetNode) {
		
		for(KsdlRootNodeParser parser : parsers) {
			
			if(parser.isMatch(obj) && parser.getNodeClass() == targetNode) {
				
				return (T) parser.parse(obj);
			}
		}
		
		throw new RuntimeException("Can't parse "+obj);
	}
	
	public RootNodeList parse(TokenSequence tokensArg) {
		
		TokenSequence tokens = new TokenSequence(tokensArg);
		
		RootNodeList roots = new RootNodeList();
		
		c2: while(!tokens.isEmpty()) {
			
			for(KsdlRootNodeParser<?, ? extends KsdlRootNode> parser : this.parsers) {
				
				if(parser.isMatch(tokens)) {
					
					KsdlRootNode node = parser.parse(tokens);
					
					roots.add(node);
					
					continue c2;
				}
			}
		}
		
		return roots;
	}
	
	public static KsdlParser getInstance() {
		
		return INSTANCE;
	}
}
