package com.kero.security.ksdl.writer;

import java.util.Collection;

import com.kero.security.ksdl.resource.KsdlWritableResource;
import com.kero.security.ksdl.resource.additionals.ResourceAddress;
import com.kero.security.ksdl.resource.repository.KsdlWritableResourceRepository;
import com.kero.security.ksdl.script.KsdlScript;
import com.kero.security.lang.collections.RootNodeList;

public class TextualWriter implements KsdlWriter {

	private KsdlWritableResourceRepository<KsdlWritableResource<String>> repository;
	
	public TextualWriter(KsdlWritableResourceRepository<KsdlWritableResource<String>> repository) {
	
		this.repository = repository;
	}
	

	@Override
	public void writeAll(Collection<KsdlScript> scripts) {
		
		scripts.forEach(this::write);
	}
	
	@Override
	public void write(KsdlScript script) {
	
		ResourceAddress target = script.getAddress();
		RootNodeList content = script.getContent();
		
		KsdlWritableResource<String> resource = repository.getOrCreateResource(target);
		
		String data = content.toText();
		
		resource.writeData(data);
	}
}
