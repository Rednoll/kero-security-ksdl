package com.kero.security.ksdl.resource;

public interface KsdlResourceWrap extends KsdlResource {

	public default boolean hasWrap(Class<? extends KsdlResourceWrap> wrapClass) {
		
		if(wrapClass.isAssignableFrom(this.getClass())) return true;

		KsdlResource original = this.getOriginal();
		
		if(original instanceof KsdlResourceWrap) {
			
			return ((KsdlResourceWrap) original).hasWrap(wrapClass);
		}
		
		return false;
	}
	
	public KsdlResource getOriginal();
}
