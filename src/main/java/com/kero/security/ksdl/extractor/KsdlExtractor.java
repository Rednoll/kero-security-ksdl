package com.kero.security.ksdl.extractor;

import com.kero.security.core.agent.KeroAccessAgent;

public interface KsdlExtractor {

	public void extractFrom(KeroAccessAgent agent) throws Exception;
}
