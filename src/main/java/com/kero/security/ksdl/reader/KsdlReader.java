package com.kero.security.ksdl.reader;

import com.kero.security.ksdl.script.ScriptList;

public interface KsdlReader {

	public ScriptList readAll();
	
	public static CompositeReader addCacheWrap(CompositeReader source) {
		
		return new CompositeReaderCacheWrap(source);
	}
	
	public static KsdlReader addCacheWrap(KsdlReader source) {
		
		return new ReaderCacheWrap(source);
	}
}
