package com.kero.security.lang.tokens;

import java.util.Objects;

public class NameToken extends KsdlTokenBase {

	private String raw;
	
	public NameToken(String raw) {
		
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
		NameToken other = (NameToken) obj;
		return Objects.equals(raw, other.raw);
	}

	@Override
	public String toString() {
		return "NameToken [raw=" + raw + "]";
	}

	public String getRaw() {
		
		return this.raw;
	}
}
