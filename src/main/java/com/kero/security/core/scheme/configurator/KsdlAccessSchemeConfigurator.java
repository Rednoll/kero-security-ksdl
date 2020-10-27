package com.kero.security.core.scheme.configurator;

import com.kero.security.core.scheme.AccessScheme;
import com.kero.security.ksdl.reader.KsdlReader;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.collections.SchemeNodeMap;
import com.kero.security.lang.nodes.SchemeNode;

public class KsdlAccessSchemeConfigurator extends AccessSchemeConfiguratorBase {

	protected KsdlReader reader;
	
	public KsdlAccessSchemeConfigurator(KsdlReader reader) {
	
		this.reader = reader;
	}

	@Override
	public void configure(AccessScheme scheme) {
		
		RootNodeList roots = reader.readRoots();
	
		SchemeNodeMap schemeNodes = roots.getSchemeNodes();
		
		SchemeNode schemeNode = schemeNodes.getOrDefault(scheme.getName(), SchemeNode.EMPTY);

		schemeNode.interpret(scheme);
	}
}
