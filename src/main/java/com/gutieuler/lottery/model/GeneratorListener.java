package com.gutieuler.lottery.model;

import java.util.Collection;

public interface GeneratorListener<T> {

	public void start();
	
	public void element(T element);
	
	public void ends(Collection<T> elements);
	
}
