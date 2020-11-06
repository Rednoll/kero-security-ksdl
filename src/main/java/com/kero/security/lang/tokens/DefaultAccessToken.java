package com.kero.security.lang.tokens;

import com.kero.security.lang.nodes.DefaultAccessNode;

public enum DefaultAccessToken implements KsdlToken {

	EMPTY {

		@Override
		public DefaultAccessNode toNode() {
			
			return DefaultAccessNode.EMPTY;
		}

		@Override
		public String toText() {
			
			return "";
		}
		
		@Override
		public String toString() {
			
			return "DefaultRuleToken [defaultAccessible=null]";
		}
		
		@Override
		public Boolean getDefaultAccessible() {
			
			return null;
		}
	}, 
	GRANT {
		
		@Override
		public DefaultAccessNode toNode() {
			
			return DefaultAccessNode.GRANT;
		}

		@Override
		public String toText() {
			
			return "(G)";
		}
		
		@Override
		public String toString() {
			
			return "DefaultRuleToken [defaultAccessible=true]";
		}
		
		@Override
		public Boolean getDefaultAccessible() {
			
			return true;
		}
	},
	DENY {
		
		@Override
		public DefaultAccessNode toNode() {
			
			return DefaultAccessNode.DENY;
		}

		@Override
		public String toText() {
			
			return "(D)";
		}
		
		@Override
		public String toString() {
			
			return "DefaultRuleToken [defaultAccessible=false]";
		}
		
		@Override
		public Boolean getDefaultAccessible() {
			
			return false;
		}
	};
	
	public abstract DefaultAccessNode toNode();
	public abstract String toText();
	public abstract Boolean getDefaultAccessible();
}
