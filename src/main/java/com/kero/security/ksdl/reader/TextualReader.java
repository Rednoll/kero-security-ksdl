package com.kero.security.ksdl.reader;

import com.kero.security.ksdl.reader.exceptions.ReadException;
import com.kero.security.ksdl.resource.KsdlResource;
import com.kero.security.ksdl.resource.repository.KsdlResourceRepository;
import com.kero.security.ksdl.script.BaseKsdlScript;
import com.kero.security.ksdl.script.ScriptList;
import com.kero.security.lang.KsdlLexer;
import com.kero.security.lang.KsdlParser;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.exception.UnexpectedTokenException;

public class TextualReader extends KsdlReaderBase {

	private KsdlResourceRepository<? extends KsdlResource<String>> repository;
	
	public TextualReader(KsdlResourceRepository<? extends KsdlResource<String>> repository) {
	
		this.repository = repository;
	}
	
	@Override
	public ScriptList readAll() {
		
		ScriptList scripts = new ScriptList();
		
		repository.getAll().forEach(resource -> {
			
			String text = resource.readData();
			
			TokenSequence tokens = KsdlLexer.getInstance().tokenize(text);
		
			try {
				
				RootNodeList roots = KsdlParser.getInstance().parse(tokens);
				scripts.add(new BaseKsdlScript(resource.getAddress(), roots));
			}
			catch(UnexpectedTokenException e) {
			
				throw new ReadException(repository, resource, e);
			}
			
		});

		return scripts;
	}
}
