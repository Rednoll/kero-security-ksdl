package com.kero.security.ksdl.resource;

import java.util.HashSet;
import java.util.Set;

public class BaseCompositeResource implements KsdlCompositeResource {

	private Set<KsdlResource> resources = new HashSet<>();
	
	@Override
	public String read() {
		
		StringBuilder builder = new StringBuilder();
		
		resources.forEach(resource -> builder.append(resource.read()+"\n"));
		
		return builder.toString();
	}

	@Override
	public void addResource(KsdlResource resource) {
		
		this.resources.add(resource);
	}
}