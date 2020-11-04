package com.kero.security.ksdl.writer;

import com.kero.security.ksdl.resource.additionals.ResourceAddress;
import com.kero.security.lang.collections.RootNodeList;

public interface KsdlWriter {

	public void write(ResourceAddress target, RootNodeList roots);
}
