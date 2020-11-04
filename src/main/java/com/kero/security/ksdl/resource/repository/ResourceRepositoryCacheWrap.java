package com.kero.security.ksdl.resource.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.kero.security.ksdl.resource.KsdlResource;
import com.kero.security.ksdl.resource.additionals.ResourceAddress;

public class ResourceRepositoryCacheWrap<T extends KsdlResource> implements KsdlResourceRepositoryWrap<T> {

	private KsdlResourceRepository<T> original;
	
	private Collection<T> all;
	
	private Map<ResourceAddress, T> getResourceCache;
	
	public ResourceRepositoryCacheWrap(KsdlResourceRepository<T> original) {
		
		this.original = original;
		this.getResourceCache = new HashMap<>();
	}
	
	protected synchronized void invalidate() {
		
		this.all = null;
		this.getResourceCache = null;
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

	@Override
	public T getResource(ResourceAddress address) {
		
		return getResourceCache.computeIfAbsent(address, original::getResource);
	}
}
