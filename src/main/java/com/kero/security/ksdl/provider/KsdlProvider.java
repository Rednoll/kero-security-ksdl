package com.kero.security.ksdl.provider;

import com.kero.security.lang.collections.RootNodeList;

public interface KsdlProvider {

	public RootNodeList getRoots();
	
	public static CompositeProvider addCacheWrap(CompositeProvider source) {
		
		return new CompositeProviderCacheWrap(source);
	}
	
	public static KsdlProvider addCacheWrap(KsdlProvider source) {
		
		return new ProviderCacheWrap(source);
	}
}
