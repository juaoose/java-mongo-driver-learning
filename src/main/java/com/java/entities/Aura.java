package com.java.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Aura {
	@JsonProperty("test_name")
	private String name;
	
	@JsonProperty("test_properties")
	private List<Property> properties;
	
	public Aura(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
}
