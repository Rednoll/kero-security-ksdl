package com.kero.security.lang.nodes;

import java.util.Objects;

import com.kero.security.core.access.Access;
import com.kero.security.core.property.Property;
import com.kero.security.core.role.storage.RoleStorage;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.RoleToken;

public class RoleNode extends KsdlNodeBase {

	protected String name;
	protected Access access;
	
	public RoleNode(String name, Access access) {
		
		this.name = name;
		this.access = access;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(access, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleNode other = (RoleNode) obj;
		return access == other.access && Objects.equals(name, other.name);
	}
	
	@Override
	public String toText() {
		
		return( access == Access.GRANT ? "+" : "-") + this.name;
	}
	
	public void interpret(Property prop, RoleStorage roleStorage) {
		
		if(this.access == Access.GRANT) {
		
			prop.grantRole(roleStorage.getOrCreate(name));
		}
		else if(access == Access.DENY) {
			
			prop.denyRole(roleStorage.getOrCreate(name));
		}
		else {
		
			throw new RuntimeException("Can't interpret RoleRule with Access: "+access);
		}
	}
}
