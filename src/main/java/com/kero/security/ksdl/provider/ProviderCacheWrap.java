package com.kero.security.ksdl.provider;

import com.kero.security.lang.collections.RootNodeList;

public class ProviderCacheWrap<T extends KsdlProvider> implements KsdlProvider {

	protected T original;
	
	protected RootNodeList roots;
	
	public ProviderCacheWrap(T original) {
		
		this.original = original;
	}
	
	public void invalidate() {
		
		this.roots = null;
	}
	
	@Override
	public RootNodeList getRoots() {
		
		if(roots == null) {
	
			roots = original.getRoots();
		}
		
		return this.roots;
	}
}
