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

public class FileResourceTest {

	@Test
	public void readData() throws IOException {
		
		FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
		
		Path file = fileSystem.getPath("file1.k-s");
		
		Files.write(file, "test".getBytes(), StandardOpenOption.CREATE_NEW);

		FileResource resource = new FileResource(file);
		
		assertEquals(resource.read(), "test");
	}
}
