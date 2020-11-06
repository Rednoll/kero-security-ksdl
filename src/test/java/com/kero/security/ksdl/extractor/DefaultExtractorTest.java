package com.kero.security.ksdl.extractor;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

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
import com.kero.security.ksdl.resource.repository.TextResourceFolder;
import com.kero.security.ksdl.script.ScriptList;
import com.kero.security.ksdl.writer.TextualWriter;

public class DefaultExtractorTest {

	@Test
	public void test() {
		
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
		
		TextResourceFolder folder = new TextResourceFolder(Paths.get(""));
		
		TextualWriter writer = new TextualWriter(folder);
		
		DefaultExtractor extractor = new DefaultExtractor();
	
		ScriptList scripts = extractor.extractFrom(agent);
		
		writer.writeAll(scripts);
	}
}
