package com.kero.security.ksdl.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.Test;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import com.kero.security.ksdl.resource.FileTextResource;

public class FileResourceTest {

	@Test
	public void readData() throws IOException {
		
		FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
		
		Path file = fileSystem.getPath("file1.k-s");
		
		Files.write(file, "test".getBytes(), StandardOpenOption.CREATE_NEW);

		FileTextResource resource = new FileTextResource(fileSystem.getPath("/"), file);
		
		assertEquals(resource.readData(), "test");
	}
	
	@Test
	public void getAdddress() throws IOException {
		
		FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
		
		Path root = fileSystem.getPath("/root");
		Files.createDirectory(root);
		
		Files.createDirectory(fileSystem.getPath("/root/sub"));
		
		Path file = fileSystem.getPath("/root/sub/file1.k-s");
		Files.createFile(file);
		
		FileTextResource resource = new FileTextResource(root, file);
		
		assertEquals(resource.getAddress().getRaw(), "sub.file1");
	}
}
