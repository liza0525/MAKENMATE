package com.cocktail.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResponse<T> extends BasicResponse {
    private T object;

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

}