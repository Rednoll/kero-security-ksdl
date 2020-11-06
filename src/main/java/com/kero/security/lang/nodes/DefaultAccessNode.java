package com.kero.security.lang.nodes;

import com.kero.security.core.DefaultAccessOwner;
import com.kero.security.core.access.Access;
import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.DefaultAccessToken;

public enum DefaultAccessNode implements KsdlNode {
	
	EMPTY {
	
		public String toText() {
			
			return "";
		}
		
		public void interpret(KeroAccessAgent manager, DefaultAccessOwner target) {
			
			target.setDefaultAccess(Access.UNKNOWN);
		}
	},
	GRANT {
	
		public String toText() {
			
			return "(G)";
		}
		
		public void interpret(KeroAccessAgent manager, DefaultAccessOwner target) {
			
			target.setDefaultAccess(Access.GRANT);
		}
	},
	DENY {
	
		public String toText() {
			
			return "(D)";
		}
		
		public void interpret(KeroAccessAgent manager, DefaultAccessOwner target) {
			
			target.setDefaultAccess(Access.DENY);
		}
	};

	public abstract void interpret(KeroAccessAgent manager, DefaultAccessOwner prop);
	
	public static DefaultAccessNode fromAccess(Access access) {

		if(access == Access.GRANT) {
			
			return DefaultAccessNode.GRANT;
		}
		else if(access == Access.DENY) {
			
			return DefaultAccessNode.DENY;
		}
		else {
			
			return DefaultAccessNode.EMPTY;
		}
	}
}
