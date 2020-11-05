package com.kero.security.ksdl.reader;

import com.kero.security.ksdl.script.ScriptList;

public class ReaderCacheWrap<T extends KsdlReader> implements KsdlReader {

	protected T original;
	
	protected ScriptList all;
	
	public ReaderCacheWrap(T original) {
		
		this.original = original;
	}
	
	public void invalidate() {
		
		this.all = null;
	}
	
	@Override
	public ScriptList readAll() {
		
		if(all == null) {
	
			all = original.readAll();
		}
		
		return this.all;
	}
}
