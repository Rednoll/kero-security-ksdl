package com.kero.security.ksdl.resource.repository;

import com.kero.security.ksdl.resource.KsdlResource;

public interface KsdlResourceRepositoryWrap<T extends KsdlResource> extends KsdlResourceRepository<T> {

	public default boolean hasWrap(Class<? extends KsdlResourceRepositoryWrap> wrapClass) {
		
		if(wrapClass.isAssignableFrom(this.getClass())) return true;

		KsdlResourceRepository original = this.getOriginal();
		
		if(original instanceof KsdlResourceRepositoryWrap) {
			
			return ((KsdlResourceRepositoryWrap) original).hasWrap(wrapClass);
		}
		
		return false;
	}
	
	public KsdlResourceRepository getOriginal();
}
