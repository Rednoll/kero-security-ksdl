package com.kero.security.core.scheme.configurator;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kero.security.core.scheme.AccessScheme;
import com.kero.security.ksdl.reader.KsdlReader;
import com.kero.security.ksdl.script.KsdlScript;
import com.kero.security.ksdl.script.ScriptList;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.nodes.SchemeNode;

public class KsdlAccessSchemeConfiguratorTest {

	@Test
	public void configure() {
		
		RootNodeList roots = new RootNodeList();
	
			SchemeNode node = Mockito.mock(SchemeNode.class);
				Mockito.when(node.getName()).thenReturn("TestScheme");
				
			roots.add(node);
		
		ScriptList scriptsList = new ScriptList();
		
			KsdlScript script = Mockito.mock(KsdlScript.class);
				Mockito.when(script.getContent()).thenReturn(roots);
		
				scriptsList.add(script);
				
		KsdlReader provider = Mockito.mock(KsdlReader.class);
		Mockito.when(provider.readAll()).thenReturn(scriptsList);
		
		AccessScheme schemeMock = Mockito.mock(AccessScheme.class);
		Mockito.when(schemeMock.getName()).thenReturn("TestScheme");
		
		KsdlAccessSchemeConfigurator configurator = new KsdlAccessSchemeConfigurator(provider);
			configurator.configure(schemeMock);
		
		Mockito.verify(node, Mockito.times(1)).interpret(schemeMock);
	}
}
