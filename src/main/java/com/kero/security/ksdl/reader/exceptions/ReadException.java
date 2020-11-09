package com.kero.security.ksdl.reader.exceptions;

import com.kero.security.ksdl.resource.KsdlResource;
import com.kero.security.ksdl.resource.repository.KsdlResourceRepository;

public class ReadException extends RuntimeException {

	public ReadException(KsdlResourceRepository repository, KsdlResource resource, Exception exception) {
		super(buildMessage(repository, resource, exception));
		
	}
	
	private static String buildMessage(KsdlResourceRepository repository, KsdlResource resource, Exception exception) {
		
		StringBuilder builder = new StringBuilder();
			builder.append("\n");
			builder.append("\n");
			builder.append("Repository: "+repository.getName());
			builder.append("\n");
			builder.append("Resource: "+repository.adaptAddress(resource.getAddress()));
			builder.append("\n");
			builder.append("\n");
			builder.append(exception.getMessage());
			
		return builder.toString();
	}
}
