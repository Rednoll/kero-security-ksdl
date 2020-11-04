package com.kero.security.ksdl.resource;

import com.kero.security.ksdl.resource.additionals.ResourceAddress;

public class ResourceCacheWrap implements KsdlResourceWrap {
	
	private KsdlResource original;
	
	private Object readDataCache;
	
	public ResourceCacheWrap(KsdlResource original) {
	
		this.original = original;
	}
	
	@Override
	public Object readData() {
		
		if(readDataCache == null) {
			
			readDataCache = original.readData();
		}
		
		return this.readDataCache;
	}

	@Override
	public ResourceAddress getAddress() {
		
		return this.original.getAddress();
	}
	
	@Override
	public KsdlResource getOriginal() {
		
		return this.original;
	}
}
