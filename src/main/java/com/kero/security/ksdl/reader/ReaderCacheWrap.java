package com.kero.security.ksdl.reader;

import com.kero.security.lang.collections.RootNodeList;

public class ReaderCacheWrap<T extends KsdlReader> implements KsdlReader {

	protected T original;
	
	protected RootNodeList roots;
	
	public ReaderCacheWrap(T original) {
		
		this.original = original;
	}
	
	public void invalidate() {
		
		this.roots = null;
	}
	
	@Override
	public RootNodeList readRoots() {
		
		if(roots == null) {
	
			roots = original.readRoots();
		}
		
		return this.roots;
	}
}
