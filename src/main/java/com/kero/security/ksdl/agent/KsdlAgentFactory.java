package com.kero.security.ksdl.agent;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.ksdl.agent.configuration.KsdlAgentConfigurator;
import com.kero.security.ksdl.provider.BaseCompositeProvider;
import com.kero.security.ksdl.provider.CompositeProvider;

public interface KsdlAgentFactory {

	public default KsdlAgent create(KeroAccessAgent agent) {
		
		return create(agent, new BaseCompositeProvider());
	}
	
	public KsdlAgent create(KeroAccessAgent agent, CompositeProvider mainProvider);
	
	public void addConfigurator(KsdlAgentConfigurator conf);
}
