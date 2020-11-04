package com.kero.security.ksdl.extractor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.scheme.AccessScheme;
import com.kero.security.core.scheme.storage.AccessSchemeStorage;
import com.kero.security.ksdl.resource.additionals.ResourceAddress;
import com.kero.security.ksdl.resource.repository.KsdlWritableResourceRepository;
import com.kero.security.lang.KsdlParser;
import com.kero.security.lang.collections.RootNodeList;

public class DefaultExtractor implements KsdlExtractor {

	private KeroAccessAgent agent;
	
	private Set<String> boilerplatePackages = new HashSet<>();
	
	public DefaultExtractor(KeroAccessAgent agent) {
		
		this.agent = agent;
		
		this.boilerplatePackages.add("impl");
	}
	
	@Override
	public void extractTo(KsdlWritableResourceRepository repository) {
	
		AccessSchemeStorage storage = agent.getSchemeStorage();
		Set<AccessScheme> schemes = new HashSet<>(storage.values());

		Map<String, Set<AccessScheme>> packages = buildPackageRepresentations(schemes);

		packages.forEach((packageName, packageSchemes)-> {
			
			Map<AccessScheme, Set<AccessScheme>> packs = splitToPacksByRoots(packageSchemes);
			
			packs.forEach((rootScheme, packSchemes)-> {
				
				String packName = rootScheme.getName();
				String address = packageName + ResourceAddress.SEPARATOR + packName;
			
				RootNodeList nodes = KsdlParser.getInstance().parse(packSchemes);
			
				// “еперь нужно пон€ть как и куда это писать.
			});
		});
	}
	
	protected Map<String, Set<AccessScheme>> buildPackageRepresentations(Set<AccessScheme> schemes) {
		
		Map<String, Set<AccessScheme>> representation = new HashMap<>();
	
		schemes.forEach(scheme -> {
			
			Class<?> typeClass = scheme.getTypeClass();
			Package typePackage = typeClass.getPackage();
			String packageName = removeBoilerplate(typePackage.getName());
			
			representation.putIfAbsent(packageName, new HashSet<>());
			
			representation.get(packageName).add(scheme);
		});
		
		return representation;
	}
	
	protected Map<AccessScheme, Set<AccessScheme>> splitToPacksByRoots(Set<AccessScheme> schemes) {
	
		Map<AccessScheme, Set<AccessScheme>> packs = new HashMap<>();
		
		Set<AccessScheme> roots = schemes.stream()
			.filter(scheme -> !schemes.contains(scheme.getParent()))
		.collect(Collectors.toSet());
		
		roots.forEach(root -> {
			
			Set<AccessScheme> childs = schemes.stream()
				.filter(child -> root.getTypeClass().isAssignableFrom(child.getTypeClass()))
			.collect(Collectors.toSet());
	
			packs.put(root, childs);
		});
		
		return packs;
	}
	
	private String removeBoilerplate(String packageName) {
		
		for(String boilerplateName : boilerplatePackages) {
			
			packageName = packageName.replaceAll(boilerplateName+"\\.|\\."+boilerplateName, "");
		}
		
		return packageName;
	}
}