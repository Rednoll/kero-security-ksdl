package com.kero.security.ksdl.writer;

import com.kero.security.ksdl.resource.KsdlWritableResource;
import com.kero.security.ksdl.resource.additionals.ResourceAddress;
import com.kero.security.ksdl.resource.repository.KsdlWritableResourceRepository;
import com.kero.security.lang.KsdlLexer;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.tokens.DefaultAccessToken;
import com.kero.security.lang.tokens.KeyWordToken;
import com.kero.security.lang.tokens.KsdlToken;
import com.kero.security.lang.tokens.NameToken;
import com.kero.security.lang.tokens.RoleToken;

public class TextualWriter implements KsdlWriter {

	private KsdlWritableResourceRepository<KsdlWritableResource<String>> repository;
	
	public TextualWriter(KsdlWritableResourceRepository<KsdlWritableResource<String>> repository) {
	
		this.repository = repository;
	}
	
	@Override
	public void write(ResourceAddress target, RootNodeList roots) {
	
		KsdlWritableResource<String> resource = repository.getOrCreateResource(target);
		
		TokenSequence sequence = KsdlLexer.getInstance().tokenize(roots);
		
		String rawData = ""; //TODO
		
		resource.writeData(rawData);
	}
	
	public String delex(TokenSequence sequence) {
		
		StringBuilder builder = new StringBuilder();
		
		boolean schemeName = false;
		boolean shortBlockActive = false;
		
		for(KsdlToken token : sequence) {
			
			if(token == KeyWordToken.SCHEME) {
				
				builder.append("scheme ");
				schemeName = true;
			}
			else if(token == KeyWordToken.OPEN_BLOCK) {
				
				if(shortBlockActive) {
					
					builder.append(": ");
				}
				else {
					
					builder.append(" {" + "\n");
				}
			}
			else if(token == KeyWordToken.CLOSE_BLOCK) {
				
				if(shortBlockActive) {
					
					builder.append("\n");
					shortBlockActive = false;
				}
				else {
					
					builder.append("}" + "\n");
				}
			}
			else if(token == KeyWordToken.FORWARD_DIRECTION) {
				
				builder.append(" -> ");
			}
			else if(token == KeyWordToken.METALINE) {
				
				builder.append("- ");
				shortBlockActive = true;
			}
			else if(token instanceof NameToken) {
				
				builder.append(((NameToken) token).getRaw());
			}
			else if(token instanceof RoleToken) {
				
				RoleToken role = (RoleToken) token;
				
				builder.append(role.getAccessible() ? "+" : "-");
				builder.append(role.getRoleName());
			}
			else if(token == DefaultAccessToken.GRANT) {
				
				builder.append("(G)");
			}
			else if(token == DefaultAccessToken.DENY) {
				
				builder.append("(D)");
			}
		}
		
		String result = builder.toString();
			result = result.replaceAll(" +", " ");
	
		return result;
	}
}
