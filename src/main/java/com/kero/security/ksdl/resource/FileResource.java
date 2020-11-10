package com.kero.security.ksdl.resource;

import java.nio.file.Path;

import com.kero.security.ksdl.resource.additionals.ResourceAddress;

public abstract class FileResource<T> implements KsdlWritableResource<T> {

	protected Path path;
	protected Path repositoryPath;
	
	public FileResource(Path repositoryPath, Path path) {

		this.repositoryPath = repositoryPath;
		this.path = path;
	}
	
	@Override
	public ResourceAddress getAddress() {
		
		String fileSeparator = this.path.getFileSystem().getSeparator();
		
		String addressPath = this.repositoryPath.relativize(this.path).toString();
			addressPath = addressPath.replaceFirst("\\..+$", "");
			
		String address = addressPath.replaceAll("\\"+fileSeparator, "\\"+ResourceAddress.SEPARATOR);
		
		if(address.startsWith(".")) {
		
			address = address.substring(1);
		}
		
		return new ResourceAddress(address);
	}
}
