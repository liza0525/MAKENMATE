package com.cocktail.exception;

public class FileStorageException extends RuntimeException{
	public FileStorageException() {
		super();
	}
	public FileStorageException(String msg) {
		super(msg);
	}
	public FileStorageException(String msg, Throwable t) {
		super(msg,t);
	}
}
