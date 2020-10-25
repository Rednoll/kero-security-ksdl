package com.kero.security.lang.nodes;

import com.kero.security.core.DefaultAccessOwner;
import com.kero.security.core.access.Access;
import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.lang.tokens.DefaultAccessToken;

public enum DefaultAccessNode implements KsdlNode {
	
	EMPTY(Access.UNKNOWN), GRANT(Access.GRANT), DENY(Access.DENY);

	private Access access;
		
	private DefaultAccessNode(Access access) {
		
		this.access = access;
	}

	public DefaultAccessToken toToken() {
		
		if(this == GRANT) return DefaultAccessToken.GRANT;
		if(this == DENY) return DefaultAccessToken.DENY;
		
		return DefaultAccessToken.EMPTY;
	}
	
	public void interpret(KeroAccessAgent manager, DefaultAccessOwner target) {
		
		target.setDefaultAccess(this.access);
	}
}
