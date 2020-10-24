package com.kero.security.ksdl.provider;

public class CompositeProviderCacheWrap extends ProviderCacheWrap<CompositeProvider> implements CompositeProvider {

	public CompositeProviderCacheWrap(CompositeProvider original) {
		super(original);
	
	}

	@Override
	public void addProvider(KsdlProvider provider) {
	
		this.invalidate();
		this.original.addProvider(provider);
	}
}
