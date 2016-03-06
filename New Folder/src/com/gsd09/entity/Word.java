package com.gsd09.entity;

public class Word {
	
	String sourceword;
	String translation;

	public Word() {
		super();
	}
	public Word(String sourceword, String translation) {
		super();
		this.sourceword = sourceword;
		this.translation = translation;
	}
	public String getsourceword() {
		return sourceword;
	}
	public void setsourceword(String sourceword) {
		this.sourceword = sourceword;
	}
	public String gettranslation() {
		return translation;
	}
	public void settranslation(String translation) {
		this.translation = translation;
	}
	
	@Override
	public String toString() {
		return "Word [sourceword=" + sourceword + ",  translation=" + translation + "]";
	}
	
	

}
