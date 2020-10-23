package com.kero.security.lang.provider;

import com.kero.security.lang.collections.RootNodeList;

public class ProviderCacheWrap implements KsdlProvider, PreloadableProvider {

	private KsdlProvider original;
	
	private RootNodeList roots;
	
	public ProviderCacheWrap(KsdlProvider original) {
		
		this.original = original;
	}
	
	@Override
	public RootNodeList getRoots() {
		
		if(roots == null) {
	
			roots = original.getRoots();
		}
		
		return this.roots;
	}

	@Override
	public void preloadResource() {
		
		if(original instanceof PreloadableProvider) {
			
			((PreloadableProvider) original).preloadResource();
		}
	}
}
