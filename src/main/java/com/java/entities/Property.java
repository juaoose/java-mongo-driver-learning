package com.java.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Property {
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String value;
	
	public Property(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
