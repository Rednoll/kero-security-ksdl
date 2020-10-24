package com.kero.security.ksdl.provider;

public interface CompositeProvider extends KsdlProvider {

	public void addProvider(KsdlProvider provider);
}
