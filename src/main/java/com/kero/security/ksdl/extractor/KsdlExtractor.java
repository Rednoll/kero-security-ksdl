package com.kero.security.ksdl.extractor;

import com.kero.security.ksdl.resource.repository.KsdlWritableResourceRepository;

public interface KsdlExtractor {

	public void extractTo(KsdlWritableResourceRepository repository);
}
