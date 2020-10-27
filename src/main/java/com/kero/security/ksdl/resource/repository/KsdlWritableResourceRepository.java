package com.kero.security.ksdl.resource.repository;

import com.kero.security.ksdl.resource.KsdlResource;

public interface KsdlWritableResourceRepository<T extends KsdlResource> extends KsdlResourceRepository<T> {

	public T createResource(String name);
}
