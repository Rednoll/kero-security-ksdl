package com.kero.security.ksdl.resource;

public interface KsdlTextResource extends KsdlResource {

	public String getRawText();
	
	public static boolean hasWrap(KsdlTextResource resource, Class<? extends KsdlTextResourceWrap> wrapClass) {
		
		if(resource instanceof KsdlTextResourceWrap) {
			
			KsdlTextResourceWrap wrap = (KsdlTextResourceWrap) resource;
			
			return wrap.hasWrap(wrapClass);
		}
		
		return false;
	}
	
	public static KsdlTextResource addCacheWrap(KsdlTextResource resource) {
		
		if(hasWrap(resource, TextResourceCacheWrap.class)) return resource;
		
		return new TextResourceCacheWrap(resource);
	}
}