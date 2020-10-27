package com.kero.security.ksdl.reader;

import java.util.HashSet;
import java.util.Set;

import com.kero.security.lang.collections.RootNodeList;

public class BaseCompositeReader implements CompositeReader {

	private Set<KsdlReader> readers;

	public BaseCompositeReader(Set<KsdlReader> readers) {

		this.readers = readers;
	}
	
	public BaseCompositeReader() {
		this(new HashSet<>());
		
	}
	
	public void addReader(KsdlReader reader) {
		
		this.readers.add(reader);
	}
	
	@Override
	public RootNodeList readRoots() {
		
		RootNodeList result = new RootNodeList();
		
		for(KsdlReader source : readers) {
		
			result.addAll(source.readRoots());
		}
		
		return result;
	}
}
