package com.kero.security.ksdl.resource;

import com.kero.security.ksdl.resource.additionals.ResourceAddress;

public interface KsdlResource<T> {
	
	public T readData();
	
	public ResourceAddress getAddress();

	public static boolean hasWrap(KsdlResource resource, Class<? extends KsdlResourceWrap> wrapClass) {
		
		if(resource instanceof KsdlResourceWrap) {
			
			KsdlResourceWrap wrap = (KsdlResourceWrap) resource;
			
			return wrap.hasWrap(wrapClass);
		}
		
		return false;
	}
	
	public static <K> KsdlResource<K> addCacheWrap(KsdlResource<K> resource) {
	
		if(hasWrap(resource, ResourceCacheWrap.class)) return resource;
		
		return new ResourceCacheWrap(resource);
	}
}
