package com.gsd09.entity;

import java.io.Serializable;

public class ODOSTag implements Serializable {
	private String id;

	private String name;

	public void setId(String id){
	this.id = id;
	}
	public String getId(){
	return this.id;
	}
	public void setName(String name){
	this.name = name;
	}
	public String getName(){
	return this.name;
	}
	
}