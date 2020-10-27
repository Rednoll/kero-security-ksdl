package com.kero.security.ksdl.resource;

public class TextResourceCacheWrap implements KsdlTextResourceWrap {
	
	private KsdlTextResource original;
	
	private String rawText;
	
	public TextResourceCacheWrap(KsdlTextResource original) {
	
		this.original = original;
	}
	
	@Override
	public String getRawText() {
		
		if(rawText == null) {
			
			rawText = original.getRawText();
		}
		
		return this.rawText;
	}

	@Override
	public String getName() {
		
		return this.original.getName();
	}
	
	@Override
	public KsdlTextResource getOriginal() {
		
		return this.original;
	}
}
