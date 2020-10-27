package com.kero.security.ksdl.agent;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.ksdl.agent.configuration.KsdlAgentConfigurator;
import com.kero.security.ksdl.reader.BaseCompositeReader;
import com.kero.security.ksdl.reader.CompositeReader;

public interface KsdlAgentFactory {

	public default KsdlAgent create(KeroAccessAgent agent) {
		
		return create(agent, new BaseCompositeReader());
	}
	
	public KsdlAgent create(KeroAccessAgent agent, CompositeReader mainProvider);
	
	public void addConfigurator(KsdlAgentConfigurator conf);
}
