package com.kero.security.ksdl.resource.additionals;

import java.util.Objects;

public class ResourceAddress {

	public static final String SEPARATOR = ".";
	
	private String raw;
	
	public ResourceAddress(String raw) {
		
		this.raw = raw;
	}

	@Override
	public int hashCode() {
		return Objects.hash(raw);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceAddress other = (ResourceAddress) obj;
		return Objects.equals(raw, other.raw);
	}

	@Override
	public String toString() {
		
		return "ResourceAddress [raw=" + raw + "]";
	}
	
	public String getRaw() {
		
		return this.raw;
	}
}
