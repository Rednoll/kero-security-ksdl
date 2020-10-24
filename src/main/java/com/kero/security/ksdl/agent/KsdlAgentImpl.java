package com.kero.security.ksdl.agent;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.scheme.configurator.KsdlAccessSchemeConfigurator;
import com.kero.security.ksdl.provider.BaseCompositeProvider;
import com.kero.security.ksdl.provider.CompositeProvider;
import com.kero.security.ksdl.provider.KsdlProvider;
import com.kero.security.ksdl.provider.TextualProvider;
import com.kero.security.ksdl.provider.resource.KsdlTextResource;

public class KsdlAgentImpl implements KsdlAgent {
	
	private KeroAccessAgent accessAgent;
	
	private CompositeProvider mainProvider;
	
	public KsdlAgentImpl(KeroAccessAgent agent) {
		this(agent, new BaseCompositeProvider());
		
	}
	
	public KsdlAgentImpl(KeroAccessAgent accessAgent, CompositeProvider mainProvider) {
		
		this.accessAgent = accessAgent;
		this.mainProvider = mainProvider;
		
		this.mainProvider = KsdlProvider.addCacheWrap(this.mainProvider);
		
		this.accessAgent.addConfigurator(new KsdlAccessSchemeConfigurator(this.mainProvider));
	}
	
	@Override
	public void addProvider(KsdlProvider provider) {
		
		this.mainProvider.addProvider(provider);
	}
	
	@Override
	public void addTextResource(KsdlTextResource resource) {
		
		this.mainProvider.addProvider(new TextualProvider(resource));
	}
	
	@Override
	public void preloadMainProvider() {
		
		this.mainProvider.getRoots(); //Trigger load
	}
}
