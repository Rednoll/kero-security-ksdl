package com.kero.security.ksdl.resource.repository;

import com.kero.security.ksdl.resource.KsdlWritableResource;
import com.kero.security.ksdl.resource.additionals.ResourceAddress;

public interface KsdlWritableResourceRepository<T extends KsdlWritableResource> extends KsdlResourceRepository<T> {

	public T createResource(ResourceAddress address);
	public boolean hasResource(ResourceAddress address);
	
	public default T getOrCreateResource(ResourceAddress address) {
		
		return this.hasResource(address) ? this.getResource(address) : createResource(address);
	}
}
