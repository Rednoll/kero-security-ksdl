package com.kero.security.ksdl.writer;

import java.util.Collection;

import com.kero.security.ksdl.script.KsdlScript;

public interface KsdlWriter {

	public void writeAll(Collection<KsdlScript> scripts);
	public void write(KsdlScript script);
}
