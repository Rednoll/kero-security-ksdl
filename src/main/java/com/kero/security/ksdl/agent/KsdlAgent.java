package com.kero.security.ksdl.agent;

import com.kero.security.ksdl.provider.KsdlProvider;
import com.kero.security.ksdl.provider.resource.KsdlTextResource;

public interface KsdlAgent {

	public void preloadMainProvider();
	
	public void addProvider(KsdlProvider provider);
	public void addTextResource(KsdlTextResource resource);
}
