package com.kero.security.lang.tokens;

import com.kero.security.core.access.Access;
import com.kero.security.lang.nodes.RoleNode;

public class RoleToken extends KsdlTokenBase {

	private boolean accessible;
	private String roleName;
	
	public RoleToken(String roleName, boolean accessible) {
		
		this.roleName = roleName;
		this.accessible = accessible;
	}
	
	public RoleNode toNode() {
		
		return new RoleNode(this.roleName, accessible ? Access.GRANT : Access.DENY);
	}
	
	@Override
	public String toString() {
		
		return "RoleToken [accessible=" + accessible + ", roleName=" + roleName + "]";
	}

	public String getRoleName() {
		
		return this.roleName;
	}
	
	public boolean getAccessible() {
		
		return this.accessible;
	}
}
