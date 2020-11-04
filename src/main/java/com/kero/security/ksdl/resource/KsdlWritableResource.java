package com.kero.security.ksdl.resource;

public interface KsdlWritableResource<T> extends KsdlResource<T> {

	public void writeData(T data);
}
