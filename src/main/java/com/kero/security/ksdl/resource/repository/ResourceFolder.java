package com.kero.security.ksdl.resource.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.kero.security.ksdl.resource.FileResource;
import com.kero.security.ksdl.resource.KsdlTextResource;
import com.kero.security.ksdl.resource.exception.FileResourceIOException;

public class ResourceFolder implements KsdlResourceRepository<KsdlTextResource> {

	protected Path path;
	protected Set<String> suffixes;
	
	public ResourceFolder(Path path, String... suffixes) {
		super();
		
		this.path = path;
		this.suffixes = new HashSet<>(Arrays.asList(suffixes));
	}
	
	public ResourceFolder(Path path) {
		this(path, new String[] {".ks", ".k-s"});
		
	}
	
	@Override
	public Collection<KsdlTextResource> getAll() {
		
		Collection<KsdlTextResource> resources = new HashSet<>();
		
		try {
			
			Files.walk(this.path).forEach((sub)-> {
				 
				if(isSuitable(sub)) {
	
					resources.add(new FileResource(sub));
				}
			});
		}
		catch(Exception e) {
			
			throw new FileResourceIOException(e);
		}
		
		return resources;
	}
	
	private boolean isSuitable(Path path) {
		 
		if(!Files.isRegularFile(path)) return false;
 
		for(String suffix : suffixes) {
 
			if(path.toString().endsWith(suffix)) {
 
				return true;
			}
		}
 
		return false;
	}
 
	public Path getPath() {
 
		return this.path;
	}

	@Override
	public String getName() {
		
		return this.path.toString();
	}
}
