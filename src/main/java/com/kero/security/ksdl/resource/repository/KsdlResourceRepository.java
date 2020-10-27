package com.kero.security.ksdl.resource.repository;

import java.util.Collection;

import com.kero.security.ksdl.resource.KsdlResource;

public interface KsdlResourceRepository<T extends KsdlResource> {

	public Collection<T> getAll();
	
	public String getName();
	
	public static boolean hasWrap(KsdlResourceRepository resource, Class<? extends KsdlResourceRepositoryWrap> wrapClass) {
		
		if(resource instanceof KsdlResourceRepositoryWrap) {
			
			KsdlResourceRepositoryWrap wrap = (KsdlResourceRepositoryWrap) resource;
			
			return wrap.hasWrap(wrapClass);
		}
		
		return false;
	}
	
	public static KsdlResourceRepository addCacheWrap(KsdlResourceRepository resource) {
		
		if(hasWrap(resource, ResourceRepositoryCacheWrap.class)) return resource;
		
		return new ResourceRepositoryCacheWrap(resource);
	}
}
