package com.kero.security.ksdl.reader;

import com.kero.security.ksdl.resource.KsdlResource;
import com.kero.security.ksdl.resource.repository.KsdlResourceRepository;
import com.kero.security.ksdl.script.BaseKsdlScript;
import com.kero.security.ksdl.script.ScriptList;
import com.kero.security.lang.KsdlLexer;
import com.kero.security.lang.KsdlParser;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.collections.TokenSequence;

public class TextualReader extends KsdlReaderBase {

	private KsdlResourceRepository<KsdlResource<String>> resources;
	
	public TextualReader(KsdlResourceRepository<KsdlResource<String>> resources) {
	
		this.resources = resources;
	}
	
	@Override
	public ScriptList readAll() {
		
		ScriptList scripts = new ScriptList();
		
		resources.getAll().forEach(resource -> {
			
			String text = resource.readData();
			
			TokenSequence tokens = KsdlLexer.getInstance().tokenize(text);
		
			RootNodeList roots = KsdlParser.getInstance().parse(tokens);
			
			scripts.add(new BaseKsdlScript(resource.getAddress(), roots));
		});

		return scripts;
	}
}
