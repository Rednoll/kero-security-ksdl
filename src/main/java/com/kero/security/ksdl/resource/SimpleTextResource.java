package com.kero.security.ksdl.resource;

public class SimpleTextResource implements KsdlTextResource {

	private String name;
	private String rawText;
	
	public SimpleTextResource(String name, String rawText) {
	
		this.name = name;
		this.rawText = rawText;
	}
	
	@Override
	public String getRawText() {
		
		return this.rawText;
	}

	@Override
	public String getName() {
		
		return this.name;
	}
}
