package com.kero.security.ksdl.agent;

import com.kero.security.ksdl.reader.KsdlReader;

public interface KsdlAgent {

	public void preloadMainReader();
	
	public void addReader(KsdlReader provider);
}
