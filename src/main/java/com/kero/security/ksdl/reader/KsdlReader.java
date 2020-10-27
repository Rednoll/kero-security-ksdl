package com.kero.security.ksdl.reader;

import com.kero.security.lang.collections.RootNodeList;

public interface KsdlReader {

	public RootNodeList readRoots();
	
	public static CompositeReader addCacheWrap(CompositeReader source) {
		
		return new CompositeReaderCacheWrap(source);
	}
	
	public static KsdlReader addCacheWrap(KsdlReader source) {
		
		return new ReaderCacheWrap(source);
	}
}
