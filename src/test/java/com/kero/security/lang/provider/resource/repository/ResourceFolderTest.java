package com.kero.security.lang.provider.resource.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import com.kero.security.ksdl.resource.KsdlTextResource;
import com.kero.security.ksdl.resource.repository.ResourceFolder;

public class ResourceFolderTest {
	
	@Test
	public void getRoots() throws IOException {
		
		FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
		
		Path root = fileSystem.getPath("/root");
		Files.createDirectories(root);
		
		Path subFolder = fileSystem.getPath("/root/sub");
		Files.createDirectories(subFolder);
		
		Path file1 = fileSystem.getPath("/root/file1.k-s");
		Path file2 = fileSystem.getPath("/root/sub/file2.k-s");
		Path file3 = fileSystem.getPath("/root/sub/file3.txt");
		
		Files.write(file1, "text1".getBytes(), StandardOpenOption.CREATE_NEW);
		Files.write(file2, "text2".getBytes(), StandardOpenOption.CREATE_NEW);
		Files.write(file3, "text3".getBytes(), StandardOpenOption.CREATE_NEW);
		
		ResourceFolder folder = new ResourceFolder(root);
	
		List<KsdlTextResource> resources = new ArrayList<>(folder.getAll());
		
		assertEquals(resources.size(), 2);
		assertTrue(resources.get(0).getRawText().equals("text1") || resources.get(0).getRawText().equals("text2"));
		assertTrue(resources.get(1).getRawText().equals("text1") || resources.get(1).getRawText().equals("text2"));
	}
}
