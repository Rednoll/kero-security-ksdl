package com.kero.security.ksdl.extractor;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.ksdl.script.ScriptList;

public interface KsdlExtractor {

	public ScriptList extractFrom(KeroAccessAgent agent);
}
