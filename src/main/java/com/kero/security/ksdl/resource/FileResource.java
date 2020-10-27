package com.kero.security.ksdl.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.kero.security.ksdl.resource.exception.FileResourceIOException;

public class FileResource implements KsdlTextResource {

	private Path path;
	
	public FileResource(Path path) {
		
		this.path = path;
	}
	
	@Override
	public String getRawText() {
		
		try {
			
			return new String(Files.readAllBytes(path));
		}
		catch(IOException e) {
		
			throw new FileResourceIOException(e);
		}
	}
	
	public String getName() {
		
		return this.path.toString();
	}
}
