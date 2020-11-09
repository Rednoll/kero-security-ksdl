package com.kero.security.lang.collections;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.kero.security.lang.exception.UnexpectedTokenException;
import com.kero.security.lang.tokens.KsdlToken;

public class TokenSequence extends TreeMap<Integer, KsdlToken> {

	private static final long serialVersionUID = 1L;
	
	private String script = null;
	
	public TokenSequence() {
		super();
		
	}
	
	public TokenSequence(String script) {
		this();
		
		this.script = script;
	}
	
	public TokenSequence(TokenSequence seq) {
		super(seq);
		
		this.script = seq.script;
	}
	
	public KsdlToken peek() {
		
		return !this.isEmpty() ? this.firstEntry().getValue() : null;
	}
	
	public <T extends KsdlToken> void consume(T token) throws UnexpectedTokenException {
		
		if(token instanceof Enum && token.getClass().getSuperclass() != Enum.class) {
			
			consume((Class<T>) token.getClass().getSuperclass());
		}
		else {
	
			consume(token.getClass());
		}
	}
	
	
	public <T extends KsdlToken> void consume(Class<T> tokenClass) throws UnexpectedTokenException {
	
		tryPoll(tokenClass);
	}
	
	public <T extends KsdlToken> T tryPoll(Class<T> clazz) throws UnexpectedTokenException {
	
		if(!isToken(0, clazz)) {
			
			if(this.size() == 0) throw new UnexpectedTokenException(this.script, -1, clazz, null);
			
			int position = this.firstEntry().getKey();
			KsdlToken token = this.firstEntry().getValue();
			
			throw new UnexpectedTokenException(this.script, position, clazz, token.getClass());
		}
		
		return (T) pollFirstEntry().getValue();
	}
	
	public <T extends KsdlToken> T tryGetOrDefault(T def) {
		
		if(def instanceof Enum && def.getClass().getSuperclass() != Enum.class) {
			
			return tryGetOrDefault((Class<T>) def.getClass().getSuperclass(), def);
		}
		else {
	
			return tryGetOrDefault((Class<T>) def.getClass(), def);
		}
	}
	
	public <T extends KsdlToken> T tryGetOrDefault(Class<T> tokenClass, T def) {
		
		if(!isToken(0, tokenClass)) return def;
	
		return (T) pollFirstEntry().getValue();
	}
	
	public boolean isToken(int index, KsdlToken token) {
		
		if(index >= size()) return false;
		
		Iterator iterator = this.entrySet().iterator();
		
		for(int i = 0; i < index; i++) {
			
			iterator.next();
		}

		return ((Map.Entry) iterator.next()).getValue().equals(token);
	}
	
	public boolean isToken(int index, Class<? extends KsdlToken> tokenClass) {
		
		if(index >= size()) return false;
		
		Iterator iterator = this.entrySet().iterator();
		
		for(int i = 0; i < index; i++) {
			
			iterator.next();
		}
		
		Class<?> currentClass = ((Map.Entry) iterator.next()).getValue().getClass();
		
		return tokenClass.isAssignableFrom(currentClass);
	}
}
