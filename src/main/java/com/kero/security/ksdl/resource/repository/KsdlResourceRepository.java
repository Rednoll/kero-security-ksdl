package com.kero.security.ksdl.resource.repository;

import java.util.Collection;

import com.kero.security.ksdl.resource.KsdlResource;
import com.kero.security.ksdl.resource.additionals.ResourceAddress;

public interface KsdlResourceRepository<T extends KsdlResource> {

	public T getResource(ResourceAddress address);
	
	public Collection<T> getAll();
	
	public String getName();
	
	public String adaptAddress(ResourceAddress address);
	
	public static boolean hasWrap(KsdlResourceRepository resource, Class<? extends KsdlResourceRepositoryWrap> wrapClass) {
		
		if(resource instanceof KsdlResourceRepositoryWrap) {
			
			KsdlResourceRepositoryWrap wrap = (KsdlResourceRepositoryWrap) resource;
			
			return wrap.hasWrap(wrapClass);
		}
		
		return false;
	}
	
	public static KsdlResourceRepository addCacheWrap(KsdlWritableResourceRepository resource) {
		
		if(hasWrap(resource, WritableResourceRepositoryCacheWrap.class)) return resource;
		
		return new WritableResourceRepositoryCacheWrap(resource);
	}
	
	public static KsdlResourceRepository addCacheWrap(KsdlResourceRepository resource) {
		
		if(hasWrap(resource, ResourceRepositoryCacheWrap.class)) return resource;
		
		return new ResourceRepositoryCacheWrap(resource);
	}
}
