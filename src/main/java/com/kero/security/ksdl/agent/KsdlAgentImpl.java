package com.kero.security.ksdl.agent;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.scheme.configurator.KsdlAccessSchemeConfigurator;
import com.kero.security.ksdl.reader.BaseCompositeReader;
import com.kero.security.ksdl.reader.CompositeReader;
import com.kero.security.ksdl.reader.KsdlReader;

public class KsdlAgentImpl implements KsdlAgent {
	
	private KeroAccessAgent accessAgent;
	
	private CompositeReader mainReader;
	
	KsdlAgentImpl(KeroAccessAgent agent) {
		this(agent, new BaseCompositeReader());
		
	}
	
	KsdlAgentImpl(KeroAccessAgent accessAgent, CompositeReader mainReader) {
		
		this.accessAgent = accessAgent;
		this.mainReader = mainReader;
		
		this.mainReader = KsdlReader.addCacheWrap(this.mainReader);
		
		this.accessAgent.addConfigurator(new KsdlAccessSchemeConfigurator(this.mainReader));
	}
	
	@Override
	public void addReader(KsdlReader provider) {
		
		this.mainReader.addReader(provider);
	}

	@Override
	public void preloadMainReader() {
		
		this.mainReader.readRoots(); //Trigger load
	}
}
