package com.kero.security.ksdl.agent;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.ksdl.agent.configuration.KsdlAgentConfigurator;
import com.kero.security.ksdl.reader.CompositeReader;

public class KsdlAgentFactoryImpl implements KsdlAgentFactory {

	private static Logger LOGGER = LoggerFactory.getLogger("Kero-Security-KSDL");
	
	private Set<KsdlAgentConfigurator> configurators = new HashSet<>();
	
	public KsdlAgentFactoryImpl() {
	
	}
	
	@Override
	public KsdlAgent create(KeroAccessAgent accessAgent, CompositeReader mainProvider) {
		
		KsdlAgent agent = new KsdlAgentImpl(accessAgent, mainProvider);
		
		for(KsdlAgentConfigurator conf : configurators) {
			
			LOGGER.debug("Apply configurator: "+conf+" to new agent.");
			conf.configure(agent);
		}
		
		return agent;
	}
	
	@Override
	public void addConfigurator(KsdlAgentConfigurator conf) {
		
		this.configurators.add(conf);
		
		LOGGER.debug("Add configurator: "+conf+" to ksdl agent factory.");
	}
}
