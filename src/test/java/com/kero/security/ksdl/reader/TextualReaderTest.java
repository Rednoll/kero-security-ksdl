package com.kero.security.ksdl.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kero.security.ksdl.reader.TextualReader;
import com.kero.security.ksdl.resource.KsdlResource;
import com.kero.security.ksdl.resource.repository.KsdlResourceRepository;
import com.kero.security.ksdl.script.ScriptList;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.nodes.SchemeNode;

public class TextualReaderTest {

	@Test
	public void getRoots() {
		
		String data = "scheme TestScheme(D) {\n"
				+ "text(G): +OWNER -FRIEND\n"
				+ "- propagation: OWNER -> FRIEND\n"
				+ "}";
		
		KsdlResource resource = Mockito.mock(KsdlResource.class);
		Mockito.when(resource.readData()).thenReturn(data);
		
		Set<KsdlResource> resources = new HashSet<>();
			resources.add(resource);
		
		KsdlResourceRepository repository = Mockito.mock(KsdlResourceRepository.class);
		Mockito.when(repository.getAll()).thenReturn(resources);
		
		TextualReader provider = new TextualReader(repository);
	
		ScriptList scripts = provider.readAll();
		
		RootNodeList roots = scripts.get(0).getContent();
		
		assertEquals(roots.size(), 1);
		assertTrue(roots.get(0) instanceof SchemeNode);
	}
}
