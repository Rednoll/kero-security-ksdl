package com.kero.security.lang.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kero.security.ksdl.reader.TextualReader;
import com.kero.security.ksdl.resource.KsdlTextResource;
import com.kero.security.ksdl.resource.repository.KsdlResourceRepository;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.nodes.SchemeNode;

public class TextualProviderTest {

	@Test
	public void getRoots() {
		
		String data = "scheme TestScheme(D) {\n"
				+ "text(G): +OWNER -FRIEND\n"
				+ "- propagation: OWNER -> FRIEND\n"
				+ "}";
		
		KsdlTextResource resource = Mockito.mock(KsdlTextResource.class);
		Mockito.when(resource.getRawText()).thenReturn(data);
		
		Set<KsdlTextResource> resources = new HashSet<>();
			resources.add(resource);
		
		KsdlResourceRepository repository = Mockito.mock(KsdlResourceRepository.class);
		Mockito.when(repository.getAll()).thenReturn(resources);
		
		TextualReader provider = new TextualReader(repository);
	
		RootNodeList roots = provider.readRoots();
		
		assertEquals(roots.size(), 1);
		assertTrue(roots.get(0) instanceof SchemeNode);
	}
}
