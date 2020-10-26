package com.kero.security.lang.nodes;

import com.kero.security.core.DefaultAccessOwner;
import com.kero.security.core.access.Access;
import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.DefaultAccessToken;

public enum DefaultAccessNode implements KsdlNode {
	
	EMPTY {
	
		public TokenSequence tokenize() {
			
			return new TokenSequence(DefaultAccessToken.EMPTY);
		}
		
		public void interpret(KeroAccessAgent manager, DefaultAccessOwner target) {
			
			target.setDefaultAccess(Access.UNKNOWN);
		}
	},
	GRANT {
	
		public TokenSequence tokenize() {
			
			return new TokenSequence(DefaultAccessToken.GRANT);
		}
		
		public void interpret(KeroAccessAgent manager, DefaultAccessOwner target) {
			
			target.setDefaultAccess(Access.GRANT);
		}
	},
	DENY {
	
		public TokenSequence tokenize() {
			
			return new TokenSequence(DefaultAccessToken.DENY);
		}
		
		public void interpret(KeroAccessAgent manager, DefaultAccessOwner target) {
			
			target.setDefaultAccess(Access.DENY);
		}
	};

	public abstract void interpret(KeroAccessAgent manager, DefaultAccessOwner prop);
}
