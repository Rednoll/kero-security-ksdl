package com.kero.security.ksdl.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kero.security.ksdl.resource.KsdlResource;
import com.kero.security.ksdl.resource.KsdlResourceWrap;
import com.kero.security.ksdl.resource.ResourceCacheWrap;

public class CachedResourceTest {

	@Test
	public void getRawText() {
		
		KsdlResource resource = Mockito.mock(KsdlResource.class);
		Mockito.when(resource.readData()).thenReturn("");
		
		ResourceCacheWrap cached = new ResourceCacheWrap(resource);
	
		cached.readData();
		cached.readData();
		
		Mockito.verify(resource, Mockito.times(1)).readData();
	}
	
	@Test
	public void doubleWrapCheck() {
		
		KsdlResource resource = Mockito.mock(KsdlResource.class);
		Mockito.when(resource.readData()).thenReturn("");
		
		KsdlResourceWrap wrap = (KsdlResourceWrap) KsdlResource.addCacheWrap(resource);
	
		assertEquals(KsdlResource.addCacheWrap(wrap), wrap);
	}
}
