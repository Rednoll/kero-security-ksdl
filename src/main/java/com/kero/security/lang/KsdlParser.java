package com.kero.security.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.nodes.KsdlRootNode;
import com.kero.security.lang.parsers.KsdlRootNodeParser;
import com.kero.security.lang.parsers.SchemeParser;

public class KsdlParser {

	private static final KsdlParser INSTANCE = new KsdlParser();
	
	protected List<KsdlRootNodeParser<?, ? extends KsdlRootNode>> parsers;
	
	private KsdlParser() {
		
		this.parsers = new ArrayList<>();
			parsers.add(new SchemeParser());
	}
	
	public RootNodeList parse(Collection<?> objs) {
		
		return objs.stream()
			.map(this::parse)
		.collect(Collectors.toCollection(RootNodeList::new));
	}
	
	public KsdlRootNode parse(Object obj) {
		
		for(KsdlRootNodeParser parser : parsers) {
			
			if(parser.isMatch(obj)) {
				
				return parser.parse(obj);
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
