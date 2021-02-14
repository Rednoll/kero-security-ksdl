package com.kero.security.ksdl.agent;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.scheme.configurator.KsdlAccessSchemeConfigurator;
import com.kero.security.ksdl.resource.BaseCompositeResource;
import com.kero.security.ksdl.resource.KsdlCompositeResource;
import com.kero.security.ksdl.resource.KsdlResource;
import com.kero.security.lang.KsdlLexer;
import com.kero.security.lang.KsdlParser;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.collections.SchemeNodeMap;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.exception.UnexpectedTokenException;

public class KsdlAgentImpl implements KsdlAgent {
	
	private KeroAccessAgent accessAgent;
	
	private KsdlCompositeResource mainResource;
	
	private boolean initialized = false;
	
	KsdlAgentImpl(KeroAccessAgent agent) {
		this(agent, new BaseCompositeResource());
		
	}
	
	KsdlAgentImpl(KeroAccessAgent accessAgent, KsdlCompositeResource mainResource) {
		
		this.accessAgent = accessAgent;
		this.mainResource = mainResource;
	}
	
	public void init() throws UnexpectedTokenException {
		
		if(this.initialized) throw new RuntimeException("KsdlAgent already initialized!");

		String rawData = this.mainResource.read();
		
		TokenSequence tokens = KsdlLexer.getInstance().tokenize(rawData);
		RootNodeList nodes = KsdlParser.getInstance().parse(tokens);
		SchemeNodeMap schemes = nodes.getSchemeNodes();
		
		this.accessAgent.addConfigurator(new KsdlAccessSchemeConfigurator(schemes));
		
		this.initialized = true;
	}
	
	@Override
	public void addResource(KsdlResource resource) {
	
		if(this.initialized) throw new RuntimeException("Can't add resource after KSDL agent initialized!");
		
		this.mainResource.addResource(resource);
	}
}
