package com.kero.security.ksdl.script;

import java.util.Collection;
import java.util.LinkedList;

public class ScriptList extends LinkedList<KsdlScript> {

	private static final long serialVersionUID = 1L;

	public boolean add(Collection<KsdlScript> scripts) {
		
		return this.addAll(scripts);
	}
}
