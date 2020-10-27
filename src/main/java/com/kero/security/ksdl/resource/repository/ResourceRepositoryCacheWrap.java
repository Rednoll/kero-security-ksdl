package com.kero.security.ksdl.resource.repository;

import java.util.Collection;

import com.kero.security.ksdl.resource.KsdlResource;

public class ResourceRepositoryCacheWrap<T extends KsdlResource> implements KsdlResourceRepositoryWrap<T> {

	private KsdlResourceRepository<T> original;
	
	private Collection<T> all;
	
	public ResourceRepositoryCacheWrap(KsdlResourceRepository<T> original) {
		
		this.original = original;
	}
	
	@Override
	public Collection<T> getAll() {
		
		if(all == null) {
			
			all = this.original.getAll();
		}
		
		return all;
	}

	@Override
	public String getName() {
		
		return this.original.getName();
	}
	
	@Override
	public KsdlResourceRepository<T> getOriginal() {
		
		return this.original;
	}
}
