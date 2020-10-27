package com.kero.security.lang.provider;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kero.security.ksdl.reader.BaseCompositeReader;
import com.kero.security.ksdl.reader.CompositeReader;
import com.kero.security.ksdl.reader.KsdlReader;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.nodes.KsdlRootNode;

public class CompositeProviderTest {

	@Test
	public void test() {
		
		KsdlReader source1 = Mockito.mock(KsdlReader.class);
		KsdlRootNode node1 = Mockito.mock(KsdlRootNode.class);
		
		RootNodeList list1 = new RootNodeList();
			list1.add(node1);
		
		Mockito.when(source1.readRoots()).thenReturn(list1);
		
		KsdlReader source2 = Mockito.mock(KsdlReader.class);
		KsdlRootNode node2 = Mockito.mock(KsdlRootNode.class);
		
		RootNodeList list2 = new RootNodeList();
			list2.add(node2);
	
		Mockito.when(source2.readRoots()).thenReturn(list2);
	
		Set<KsdlReader> sources = new HashSet<>();
			sources.add(source1);
			sources.add(source2);
			
		CompositeReader provider = new BaseCompositeReader(sources);
			
		assertTrue(provider.readRoots().contains(node1));
		assertTrue(provider.readRoots().contains(node2));
	}
}
