package com.cocktail.exception;

public class CocktailException extends RuntimeException{
	public CocktailException() {
		super();
	}
	public CocktailException(String msg) {
		super(msg);
	}
	public CocktailException(String msg, Throwable t) {
		super(msg,t);
	}
	
}
