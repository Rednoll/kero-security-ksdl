package com.kero.security.lang.collections;

import java.util.Arrays;
import java.util.LinkedList;

import com.kero.security.lang.tokens.KsdlToken;

public class TokenSequence extends LinkedList<KsdlToken> {

	private static final long serialVersionUID = 1L;

	public TokenSequence() {
		super();
		
	}
	
	public TokenSequence(TokenSequence seq) {
		super(seq);
		
	}
	
	public TokenSequence(KsdlToken... tokens) {
		this();
		
		this.addAll(Arrays.asList(tokens));
	}
	
	public <T extends KsdlToken> void consume(T token) {
		
		if(token instanceof Enum && token.getClass().getSuperclass() != Enum.class) {
			
			tryPoll((Class<T>) token.getClass().getSuperclass());
		}
		else {
	
			tryPoll(token.getClass());
		}
	}
	
	public <T extends KsdlToken> T tryPoll(Class<T> clazz) {
		
		if(!clazz.isAssignableFrom(this.peek().getClass())) throw new RuntimeException("Incorrect token: "+clazz);
		
		return (T) this.poll();
	}
	
	public boolean add(TokenSequence seq) {
		
		return this.addAll(seq);
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
	
		return (T) poll();
	}
	
	public boolean isToken(int index, KsdlToken token) {
		
		if(index >= size()) return false;
		
		return get(index) == token;
	}
	
	public boolean isToken(int index, Class<? extends KsdlToken> tokenClass) {
		
		return tokenClass.isAssignableFrom(get(index).getClass());
	}
}
