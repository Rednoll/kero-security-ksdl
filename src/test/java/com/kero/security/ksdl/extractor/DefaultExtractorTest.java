package com.kero.security.ksdl.extractor;

import java.nio.file.FileSystem;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.agent.KeroAccessAgentFactoryImpl;
import com.kero.security.core.scheme.configurator.AnnotationAccessSchemeConfigurator;
import com.kero.security.ksdl.extractor.p1.Another;
import com.kero.security.ksdl.extractor.p1.Root;
import com.kero.security.ksdl.extractor.p1.impl.AnotherASub;
import com.kero.security.ksdl.extractor.p1.impl.RootASub;
import com.kero.security.ksdl.extractor.p1.impl.RootBSub;
import com.kero.security.ksdl.extractor.p1.impl.RootBSubSub;
import com.kero.security.ksdl.extractor.p1.impl.p2.RootCSub;
import com.kero.security.ksdl.extractor.p1.impl.p2.RootCSubSub;

public class DefaultExtractorTest {

	@Test
	public void test() throws Exception {
		
		FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
		
		Path folder = fileSystem.getPath("");
		
		KeroAccessAgent agent = new KeroAccessAgentFactoryImpl().create();
			agent.addConfigurator(new AnnotationAccessSchemeConfigurator(agent));
		
		agent.getOrCreateScheme(Root.class);
		agent.getOrCreateScheme(RootASub.class);
		agent.getOrCreateScheme(RootBSub.class);
		agent.getOrCreateScheme(RootBSubSub.class);
		agent.getOrCreateScheme(RootCSub.class);
		agent.getOrCreateScheme(RootCSubSub.class);
		
		agent.getOrCreateScheme(Another.class);
		agent.getOrCreateScheme(AnotherASub.class);
		
		DefaultExtractor extractor = new DefaultExtractor(folder);
	
		extractor.extractFrom(agent);
	}
}
