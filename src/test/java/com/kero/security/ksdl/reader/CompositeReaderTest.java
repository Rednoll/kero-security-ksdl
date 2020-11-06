package com.kero.security.ksdl.reader;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kero.security.ksdl.reader.BaseCompositeReader;
import com.kero.security.ksdl.reader.CompositeReader;
import com.kero.security.ksdl.reader.KsdlReader;
import com.kero.security.ksdl.script.KsdlScript;
import com.kero.security.ksdl.script.ScriptList;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.nodes.KsdlRootNode;

public class CompositeReaderTest {

	@Test
	public void test() {
		
		KsdlReader source1 = Mockito.mock(KsdlReader.class);
		KsdlRootNode node1 = Mockito.mock(KsdlRootNode.class);
		
		RootNodeList list1 = new RootNodeList();
			list1.add(node1);
		
		ScriptList scriptsList1 = new ScriptList();
		
			KsdlScript script1 = Mockito.mock(KsdlScript.class);
				Mockito.when(script1.getContent()).thenReturn(list1);
			
			scriptsList1.add(script1);
			
		Mockito.when(source1.readAll()).thenReturn(scriptsList1);
		
		////
		
		KsdlReader source2 = Mockito.mock(KsdlReader.class);
		KsdlRootNode node2 = Mockito.mock(KsdlRootNode.class);
		
		RootNodeList list2 = new RootNodeList();
			list2.add(node2);
	
		ScriptList scriptsList2 = new ScriptList();
			
			KsdlScript script2 = Mockito.mock(KsdlScript.class);
				Mockito.when(script2.getContent()).thenReturn(list2);
			
			scriptsList2.add(script2);
			
		Mockito.when(source2.readAll()).thenReturn(scriptsList2);
	
		////
		
		Set<KsdlReader> sources = new HashSet<>();
			sources.add(source1);
			sources.add(source2);
			
		CompositeReader provider = new BaseCompositeReader(sources);
		
		ScriptList scripts = provider.readAll();
		
		assertTrue(scripts.get(0).getContent().contains(node1) || scripts.get(0).getContent().contains(node2));
		assertTrue(scripts.get(1).getContent().contains(node2) || scripts.get(1).getContent().contains(node1));
	}
}
