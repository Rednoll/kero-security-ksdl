package com.kero.security.ksdl.script;

import com.kero.security.ksdl.resource.additionals.ResourceAddress;
import com.kero.security.lang.collections.RootNodeList;

public class BaseKsdlScript implements KsdlScript {

	private ResourceAddress address;
	
	private RootNodeList content;
	
	public BaseKsdlScript() {}
	
	public BaseKsdlScript(ResourceAddress address, RootNodeList content) {
		
		this.address = address;
		this.content = content;
	}
	
	@Override
	public RootNodeList getContent() {
		
		return this.content;
	}

	@Override
	public ResourceAddress getAddress() {
	
		return this.address;
	}
}
