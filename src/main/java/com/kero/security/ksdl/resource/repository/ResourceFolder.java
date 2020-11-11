package com.kero.security.ksdl.resource.repository;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kero.security.ksdl.resource.KsdlWritableResource;
import com.kero.security.ksdl.resource.additionals.ResourceAddress;
import com.kero.security.ksdl.resource.exception.FileResourceIOException;

public abstract class ResourceFolder<T> implements KsdlWritableResourceRepository<KsdlWritableResource<T>> {

	private static Logger LOGGER = LoggerFactory.getLogger("Kero-Security-KSDL");
	
	protected Path path;
	protected Set<String> suffixes;
	
	public ResourceFolder(Path path, Collection<String> suffixes) {
		
		this.path = path;
		this.suffixes = new HashSet<>(suffixes);
	}
	
	public ResourceFolder(Path path, String... suffixes) {
		this(path, Arrays.asList(suffixes));
		
	}
	
	public ResourceFolder(Path path) {
		this(path, new String[] {".ks", ".k-s"});
		
	}
	
	protected abstract KsdlWritableResource<T> getResource(Path path);
	
	@Override
	public void push() {
		
	}
	
	@Override
	public Collection<KsdlWritableResource<T>> getAll() {
		
		Collection<KsdlWritableResource<T>> resources = new HashSet<>();
		
		try {
			
			Files.walk(this.path).forEach((sub)-> {
				 
				if(isSuitable(sub)) {
	
					LOGGER.debug("Found KSDL file: "+sub);
					
					resources.add(getResource(sub));
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
 
	@Override
	public KsdlWritableResource<T> getResource(ResourceAddress address) {
		
		if(!hasResource(address)) throw new RuntimeException("Resource with address: "+address+" not found!");
		
		return getResource(determineResourcePath(address));
	}
	
	@Override
	public boolean hasResource(ResourceAddress address) {
		
		return determineResourcePath(address) != null;
	}
	
	private Path determineResourcePath(ResourceAddress address) {
	
		FileSystem fs = this.path.getFileSystem();
		
		String fileSeparator = fs.getSeparator();
		
		String addressPath = address.getRaw().replaceAll("\\"+ResourceAddress.SEPARATOR, Matcher.quoteReplacement(fileSeparator));
		
		for(String suffix : suffixes) {
			
			Path path = fs.getPath(this.path.toString(), addressPath + suffix);
			
			if(Files.exists(path)) return path;
		}
		
		return null;
	}
	
	public Path getPath() {
 
		return this.path;
	}

	@Override
	public String getName() {
		
		return this.path.toString();
	}
}
