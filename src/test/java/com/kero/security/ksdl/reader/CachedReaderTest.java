package com.kero.security.ksdl.reader;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kero.security.ksdl.reader.KsdlReader;
import com.kero.security.ksdl.reader.ReaderCacheWrap;
import com.kero.security.lang.collections.RootNodeList;

public class CachedReaderTest {

	@Test
	public void getRoots_One() {
		
		KsdlReader provider = Mockito.mock(KsdlReader.class);
		Mockito.when(provider.readRoots()).thenReturn(new RootNodeList());
		
		ReaderCacheWrap cached = new ReaderCacheWrap(provider);
		
		cached.readRoots();
		cached.readRoots();
		
		Mockito.verify(provider, Mockito.times(1)).readRoots();
	}
}
