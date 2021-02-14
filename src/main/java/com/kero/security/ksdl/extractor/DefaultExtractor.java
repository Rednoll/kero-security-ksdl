package com.kero.security.ksdl.extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.scheme.AccessScheme;
import com.kero.security.core.scheme.storage.AccessSchemeStorage;
import com.kero.security.lang.KsdlParser;
import com.kero.security.lang.collections.RootNodeList;
import com.kero.security.lang.nodes.BindNode;
import com.kero.security.lang.nodes.SchemeNode;

public class DefaultExtractor implements KsdlExtractor {

	private Set<String> boilerplatePackages = new HashSet<>();
	
	private Path rootFolder;
	
	public DefaultExtractor(Path rootFolder) {
		
		this.rootFolder = rootFolder;
		
		this.boilerplatePackages.add("impl");
	}
	
	@Override
	public void extractFrom(KeroAccessAgent agent) throws IOException {
		
		AccessSchemeStorage storage = agent.getSchemeStorage();
		Set<AccessScheme> schemes = new HashSet<>(storage.values());

		Map<String, Set<AccessScheme>> packages = buildPackageRepresentations(schemes);

		packages.forEach((packageName, packageSchemes)-> {
			
			Map<AccessScheme, List<AccessScheme>> packs = splitToPacksByRoots(packageSchemes);
			
			packs.forEach((rootScheme, packSchemes)-> {
				
				String packName = rootScheme.getName();
				String address = packageName + this.rootFolder.getFileSystem().getSeparator() + packName + ".k-s";
				
				Path path = this.rootFolder.resolve(address);
				
				RootNodeList list = createScript(rootScheme, packSchemes);
				
				try {
				
					if(!Files.exists(path.getParent())) {
						
						Files.createDirectories(path.getParent());
					}
					
					Files.write(path, list.toText().getBytes(), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
				}
				catch(IOException e) {
					
					e.printStackTrace();
				}
			});
		});
	}
	
	protected RootNodeList createScript(AccessScheme rootScheme, List<AccessScheme> schemes) {
		
		return createScriptBindDown(rootScheme, schemes);
	}
	
	protected RootNodeList createScriptRound(AccessScheme rootScheme, List<AccessScheme> schemes) {
		
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
			
		return content;
	}
	
	protected RootNodeList createScriptBindDown(AccessScheme rootScheme, List<AccessScheme> schemes) {
		
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

		return content;
	}
	
	protected RootNodeList createScriptBindUp(AccessScheme rootScheme, List<AccessScheme> schemes) {
		
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
			
		return content;
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
			
			packageName = packageName.replaceAll("\\.", Matcher.quoteReplacement(this.rootFolder.getFileSystem().getSeparator()));
			
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