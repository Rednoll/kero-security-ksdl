package com.kero.security.ksdl.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.kero.security.ksdl.resource.exception.FileResourceIOException;

public class FileResource implements KsdlResource {

	protected Path path;
	
	public FileResource(Path path) {

		this.path = path;
	}
	
	@Override
	public String read() {
		
		try {
			
			return new String(Files.readAllBytes(this.path));
		}
		catch(IOException e) {
		
			throw new FileResourceIOException(e);
		}
	}
}
