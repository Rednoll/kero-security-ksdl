package com.kero.security.ksdl.reader;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.kero.security.ksdl.script.ScriptList;
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
	public ScriptList readAll() {
		
		ScriptList result = new ScriptList();
		
		readers.forEach(reader -> result.addAll(reader.readAll()));
		
		return result;
	}
}
