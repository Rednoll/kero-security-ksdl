package com.kero.security.ksdl.reader;

import com.kero.security.ksdl.resource.KsdlTextResource;
import com.kero.security.ksdl.resource.repository.KsdlResourceRepository;
import com.kero.security.lang.KsdlLexer;
import com.kero.security.lang.KsdlParser;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.collections.TokenSequence;

public class TextualReader extends KsdlReaderBase {

	private KsdlResourceRepository<KsdlTextResource> resources;
	
	public TextualReader(KsdlResourceRepository<KsdlTextResource> resources) {
	
		this.resources = resources;
	}
	
	@Override
	public RootNodeList readRoots() {
		
		RootNodeList roots = new RootNodeList();
		
		resources.getAll().forEach(resource -> {
			
			String text = resource.getRawText();
			
			TokenSequence tokens = KsdlLexer.getInstance().tokenize(text);
		
			roots.add(KsdlParser.getInstance().parse(tokens));
		});

		return roots;
	}
}
