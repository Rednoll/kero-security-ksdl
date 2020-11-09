package com.kero.security.lang.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.kero.security.lang.tokens.DefaultAccessToken;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.KsdlToken;
import com.kero.security.lang.tokens.NameToken;
import com.kero.security.lang.tokens.RoleToken;

public class UnexpectedTokenException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private static Map<Class<? extends KsdlToken>, String> tokenNames;
	
	static {
		
		tokenNames = new HashMap<>();
			tokenNames.put(DefaultAccessToken.class, "default rule (G)/(D)");
			tokenNames.put(DefaultAccessToken.DENY.getClass(), "(D)");
			tokenNames.put(DefaultAccessToken.GRANT.getClass(), "(G)");
			
			tokenNames.put(KeyWordToken.BIND.getClass(), "bind");
			tokenNames.put(KeyWordToken.CLOSE_BLOCK.getClass(), "} / \n");
			tokenNames.put(KeyWordToken.OPEN_BLOCK.getClass(), "{ / :");
			tokenNames.put(KeyWordToken.EXTENDS.getClass(), "extends");
			tokenNames.put(KeyWordToken.METALINE.getClass(), "-");
			tokenNames.put(KeyWordToken.SCHEME.getClass(), "scheme");
			tokenNames.put(KeyWordToken.TO.getClass(), "to");
			
			tokenNames.put(NameToken.class, "some name");
			tokenNames.put(RoleToken.class, "role rule");
			tokenNames.put(null, "end of script");
			
	}
	
	private String script;
	
	private int positionInScript;
	
	private Class<? extends KsdlToken> expectedToken;
	private Class<? extends KsdlToken> receivedToken;
	
	public UnexpectedTokenException(String script, int positionInScript, Class<? extends KsdlToken> expectedToken, Class<? extends KsdlToken> receivedToken) {
		super(buildMessage(script, positionInScript, expectedToken, receivedToken));
		
		this.script = script;
		this.positionInScript = positionInScript;
	
		this.expectedToken = expectedToken;
		this.receivedToken = receivedToken;
	}
	
	public Class<? extends KsdlToken> getReceivedToken() {
		
		return this.receivedToken;
	}
	
	public Class<? extends KsdlToken> getExpectedToken() {
	
		return this.expectedToken;
	}
	
	public int getPositionInScript() {
		
		return this.positionInScript;
	}

	public String getScript() {
		
		return this.script;
	}

	private static String buildMessage(String script, int positionInScript, Class<? extends KsdlToken> expectedToken, Class<? extends KsdlToken> receivedToken) {

		StringBuilder builder = new StringBuilder();
			builder.append("Expected token <"+tokenNames.get(expectedToken)+"> but received <"+tokenNames.get(receivedToken)+">");
			builder.append("\n");
			
			if(positionInScript != -1) {
				
				String row = script.substring(
						script.substring(0, positionInScript).lastIndexOf('\n'),
						positionInScript + script.substring(positionInScript, script.length()).indexOf('\n')
					);
	
					int rowNumber = StringUtils.countMatches(script.substring(0, positionInScript), '\n') + 1;
					
					int columnNumber = positionInScript - script.substring(0, positionInScript).lastIndexOf('\n') - 1; // -1 cause we miss \n
					
				builder.append("row: "+rowNumber+" col: "+columnNumber);
				builder.append("\n");
				builder.append(row);
				builder.append("\n");
				builder.append(StringUtils.repeat(' ', columnNumber - 2));
				builder.append(StringUtils.repeat('^', 4));
				builder.append("\n");
			}
			
			
		return builder.toString();
	}
}
