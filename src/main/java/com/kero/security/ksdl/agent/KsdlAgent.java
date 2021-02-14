package com.kero.security.ksdl.agent;

import com.kero.security.ksdl.resource.KsdlResource;

public interface KsdlAgent {
	
	public void init();
	public void addResource(KsdlResource resource);
}
