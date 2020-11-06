package com.kero.security.ksdl.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.kero.security.ksdl.resource.exception.FileResourceIOException;

public class FileTextResource extends FileResource<String> {

	public FileTextResource(Path repositoryPath, Path path) {
		super(repositoryPath, path);
		
	}
	
	@Override
	public void writeData(String text) {
		
		try {
			
			Files.createDirectories(this.path.getParent());
			Files.write(this.path, text.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		}
		catch(IOException e) {
			
			throw new FileResourceIOException(e);
		}
	}

	@Override
	public String readData() {
		
		try {
			
			return new String(Files.readAllBytes(this.path));
		}
		catch(IOException e) {
		
			throw new FileResourceIOException(e);
		}
	}
}
