package com.kero.security.ksdl.reader;

public interface CompositeReader extends KsdlReader {

	public void addReader(KsdlReader provider);
}
