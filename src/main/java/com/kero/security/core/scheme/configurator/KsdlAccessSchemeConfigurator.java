package com.kero.security.core.scheme.configurator;

import com.kero.security.core.scheme.AccessScheme;
import com.kero.security.lang.collections.SchemeNodeMap;
import com.kero.security.lang.nodes.SchemeNode;

public class KsdlAccessSchemeConfigurator extends AccessSchemeConfiguratorBase {

	private SchemeNodeMap schemes;
	
	public KsdlAccessSchemeConfigurator(SchemeNodeMap schemes) {
	
		this.schemes = schemes;
	}

	@Override
	public void configure(AccessScheme scheme) {
		
		SchemeNode schemeNode = this.schemes.getOrDefault(scheme.getName(), SchemeNode.EMPTY);

		schemeNode.interpret(scheme);
	}
}
