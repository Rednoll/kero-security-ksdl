package com.kero.security.ksdl.extractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.scheme.AccessScheme;
import com.kero.security.core.scheme.storage.AccessSchemeStorage;
import com.kero.security.ksdl.resource.additionals.ResourceAddress;
import com.kero.security.ksdl.script.BaseKsdlScript;
import com.kero.security.ksdl.script.KsdlScript;
import com.kero.security.ksdl.script.ScriptList;
import com.kero.security.lang.KsdlParser;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.nodes.BindNode;
import com.kero.security.lang.nodes.SchemeNode;

public class DefaultExtractor implements KsdlExtractor {

	private Set<String> boilerplatePackages = new HashSet<>();
	
	public DefaultExtractor() {
		
		this.boilerplatePackages.add("impl");
	}
	
	@Override
	public ScriptList extractFrom(KeroAccessAgent agent) {
	
		AccessSchemeStorage storage = agent.getSchemeStorage();
		Set<AccessScheme> schemes = new HashSet<>(storage.values());

		Map<String, Set<AccessScheme>> packages = buildPackageRepresentations(schemes);

		ScriptList result = new ScriptList();
		
			packages.forEach((packageName, packageSchemes)-> {
				
				Map<AccessScheme, List<AccessScheme>> packs = splitToPacksByRoots(packageSchemes);
				
				packs.forEach((rootScheme, packSchemes)-> {
					
					String packName = rootScheme.getName();
					String address = packageName + ResourceAddress.SEPARATOR + packName;
					
					result.add(createScript(new ResourceAddress(address), rootScheme, packSchemes));
				});
			});
		
		return result;
	}
	
	protected KsdlScript createScript(ResourceAddress address, AccessScheme rootScheme, List<AccessScheme> schemes) {
		
		return createScriptBindDown(address, rootScheme, schemes);
	}
	
	protected KsdlScript createScriptRound(ResourceAddress address, AccessScheme rootScheme, List<AccessScheme> schemes) {
		
		schemes = new ArrayList<>(schemes);
		
		Collections.sort(schemes, new Comparator<AccessScheme>() {

			@Override
			public int compare(AccessScheme o1, AccessScheme o2) {
				
				int distance = calcDistance(rootScheme, o1) - calcDistance(rootScheme, o2);
				
				if(distance != 0) return distance;
				
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		RootNodeList content = new RootNodeList();
			
			for(AccessScheme scheme : schemes) {
				
				content.add(KsdlParser.getInstance().parse(scheme, SchemeNode.class));
				content.add(KsdlParser.getInstance().parse(scheme, BindNode.class));
			}
			
		return new BaseKsdlScript(address, content);
	}
	
	protected KsdlScript createScriptBindDown(ResourceAddress address, AccessScheme rootScheme, List<AccessScheme> schemes) {
		
		schemes = new ArrayList<>(schemes);
		
		Collections.sort(schemes, new Comparator<AccessScheme>() {

			@Override
			public int compare(AccessScheme o1, AccessScheme o2) {
				
				int distance = calcDistance(rootScheme, o1) - calcDistance(rootScheme, o2);
				
				if(distance != 0) return distance;
				
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		RootNodeList content = new RootNodeList();
		
			schemes.forEach(scheme -> content.add(KsdlParser.getInstance().parse(scheme, SchemeNode.class)));
			schemes.forEach(scheme -> content.add(KsdlParser.getInstance().parse(scheme, BindNode.class)));
			
		return new BaseKsdlScript(address, content);
	}
	
	protected KsdlScript createScriptBindUp(ResourceAddress address, AccessScheme rootScheme, List<AccessScheme> schemes) {
		
		schemes = new ArrayList<>(schemes);
		
		Collections.sort(schemes, new Comparator<AccessScheme>() {

			@Override
			public int compare(AccessScheme o1, AccessScheme o2) {
				
				int distance = calcDistance(rootScheme, o1) - calcDistance(rootScheme, o2);
				
				if(distance != 0) return distance;
				
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		RootNodeList content = new RootNodeList();
		
			schemes.forEach(scheme -> content.add(KsdlParser.getInstance().parse(scheme, BindNode.class)));
			schemes.forEach(scheme -> content.add(KsdlParser.getInstance().parse(scheme, SchemeNode.class)));
			
		return new BaseKsdlScript(address, content);
	}
	
	protected int calcDistance(AccessScheme parent, AccessScheme scheme) {
		
		if(parent == scheme) return 0;
		
		int result = 1;
		
		while(scheme.getParent() != parent) {
			
			scheme = scheme.getParent();
			result++;
		}
		
		return result;
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
	
	protected Map<AccessScheme, List<AccessScheme>> splitToPacksByRoots(Set<AccessScheme> schemes) {
	
		Map<AccessScheme, List<AccessScheme>> packs = new HashMap<>();
		
		Set<AccessScheme> roots = schemes.stream()
			.filter(scheme -> !schemes.contains(scheme.getParent()))
		.collect(Collectors.toSet());
		
		roots.forEach(root -> {
			
			List<AccessScheme> childs = schemes.stream()
				.filter(child -> root.getTypeClass().isAssignableFrom(child.getTypeClass()))
			.collect(Collectors.toList());
	
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