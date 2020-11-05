package com.kero.security.ksdl.script;

import com.kero.security.ksdl.resource.additionals.ResourceAddress;
import com.kero.security.lang.collections.RootNodeList;

public interface KsdlScript {

	public RootNodeList getContent();
	public ResourceAddress getAddress();
}
