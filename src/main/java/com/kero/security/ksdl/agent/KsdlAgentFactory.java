package com.kero.security.ksdl.agent;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.ksdl.agent.configurator.KsdlAgentConfigurator;
import com.kero.security.ksdl.resource.BaseCompositeResource;
import com.kero.security.ksdl.resource.KsdlCompositeResource;

public interface KsdlAgentFactory {

	public default KsdlAgent create(KeroAccessAgent agent) {
		
		return create(agent, new BaseCompositeResource());
	}
	
	public KsdlAgent create(KeroAccessAgent agent, KsdlCompositeResource mainProvider);
	
	public void addConfigurator(KsdlAgentConfigurator conf);
}
