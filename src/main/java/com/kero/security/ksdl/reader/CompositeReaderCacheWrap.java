package com.kero.security.ksdl.reader;

public class CompositeReaderCacheWrap extends ReaderCacheWrap<CompositeReader> implements CompositeReader {

	public CompositeReaderCacheWrap(CompositeReader original) {
		super(original);
	
	}

	@Override
	public void addReader(KsdlReader reader) {
	
		this.invalidate();
		this.original.addReader(reader);
	}
}
