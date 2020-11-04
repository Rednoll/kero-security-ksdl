package com.kero.security.ksdl.resource.additionals;

public class ResourceAddress {

	public static final String SEPARATOR = ".";
	
	private String raw;
	
	public ResourceAddress(String raw) {
		
		this.raw = raw;
	}

	@Override
	public String toString() {
		
		return "ResourceAddress [raw=" + raw + "]";
	}
	
	public String getRaw() {
		
		return this.raw;
	}
}
