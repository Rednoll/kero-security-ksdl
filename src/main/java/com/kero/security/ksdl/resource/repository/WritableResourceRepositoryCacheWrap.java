package com.kero.security.ksdl.resource.repository;

import com.kero.security.ksdl.resource.KsdlWritableResource;
import com.kero.security.ksdl.resource.additionals.ResourceAddress;

public class WritableResourceRepositoryCacheWrap<T extends KsdlWritableResource> extends ResourceRepositoryCacheWrap<T> implements KsdlWritableResourceRepository<T> {

	private KsdlWritableResourceRepository<T> original;
	
	public WritableResourceRepositoryCacheWrap(KsdlWritableResourceRepository<T> original) {
		super(original);
		
	}
	
	@Override
	public T createResource(ResourceAddress address) {
		
		this.invalidate();
		
		return this.original.createResource(address);
	}

	@Override
	public boolean hasResource(ResourceAddress address) {
		
		return this.original.hasResource(address);
	}
}
