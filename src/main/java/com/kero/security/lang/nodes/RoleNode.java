package com.kero.security.lang.nodes;

import com.kero.security.core.access.Access;
import com.kero.security.core.property.Property;
import com.kero.security.core.role.storage.RoleStorage;
import com.kero.security.lang.tokens.RoleToken;

public class RoleNode extends KsdlNodeBase {

	private String name;
	private Access access;
	
	public RoleNode(String name, Access access) {
		
		this.name = name;
		this.access = access;
	}
	
	public RoleToken toToken() {
		
		return new RoleToken(this.name, access == Access.GRANT);
	}
	
	public void interpret(Property prop, RoleStorage roleStorage) {
		
		if(access == Access.GRANT) {
		
			prop.grantRole(roleStorage.getOrCreate(name));
		}
		else if(access == Access.DENY) {
			
			prop.denyRole(roleStorage.getOrCreate(name));
		}
		
		throw new RuntimeException("Can't interpret RoleRule with Access: "+access);
	}
}
