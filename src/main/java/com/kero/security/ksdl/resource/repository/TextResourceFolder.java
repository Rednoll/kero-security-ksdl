package com.kero.security.ksdl.resource.repository;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.regex.Matcher;

import com.kero.security.ksdl.resource.FileTextResource;
import com.kero.security.ksdl.resource.additionals.ResourceAddress;

public class TextResourceFolder extends ResourceFolder<String> {

	public TextResourceFolder(Path path, Collection<String> suffixes) {
		super(path, suffixes);
		
	}
	
	public TextResourceFolder(Path path, String... suffixes) {
		super(path, suffixes);
		
	}
	
	public TextResourceFolder(Path path) {
		super(path);
		
	}

	@Override
	protected FileTextResource getResource(Path path) {
		
		return new FileTextResource(this.path, path);
	}

	@Override
	public FileTextResource createResource(ResourceAddress address) {
		
		String addressPath = address.getRaw().replaceAll("\\"+ResourceAddress.SEPARATOR, Matcher.quoteReplacement(File.separator));
			addressPath += suffixes.iterator().next();
		
		return new FileTextResource(this.path, Paths.get(this.path.toString(), addressPath));
	}
}
