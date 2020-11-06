package com.kero.security.ksdl.reader;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kero.security.ksdl.script.ScriptList;

public class CachedReaderTest {

	@Test
	public void getRoots_One() {
		
		KsdlReader provider = Mockito.mock(KsdlReader.class);
		Mockito.when(provider.readAll()).thenReturn(new ScriptList());
		
		ReaderCacheWrap cached = new ReaderCacheWrap(provider);
		
		cached.readAll();
		cached.readAll();
		
		Mockito.verify(provider, Mockito.times(1)).readAll();
	}
}
