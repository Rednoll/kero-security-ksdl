package com.kero.security.ksdl.writer;

import com.kero.security.ksdl.resource.KsdlWritableResource;
import com.kero.security.ksdl.resource.additionals.ResourceAddress;
import com.kero.security.ksdl.resource.repository.KsdlWritableResourceRepository;
import com.kero.security.lang.KsdlLexer;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.collections.TokenSequence;

public class TextualWriter implements KsdlWriter {

	private KsdlWritableResourceRepository<KsdlWritableResource<String>> repository;
	
	public TextualWriter(KsdlWritableResourceRepository<KsdlWritableResource<String>> repository) {
	
		this.repository = repository;
	}
	
	@Override
	public void write(ResourceAddress target, RootNodeList roots) {
	
		KsdlWritableResource<String> resource = repository.getOrCreateResource(target);
		
		TokenSequence sequence = KsdlLexer.getInstance().tokenize(roots);
		
		String rawData = null; //TODO
		
		resource.writeData(rawData);
	}
}
