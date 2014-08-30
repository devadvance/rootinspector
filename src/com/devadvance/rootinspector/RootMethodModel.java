package com.devadvance.rootinspector;

public class RootMethodModel {
	
	public String method;
	public String description;
	public String status;
	
	public RootMethodModel() {
	}
	
	public RootMethodModel(String method, String description, String status) {
		this.method = method;
		this.description = description;
		this.status = status;
	}
}
