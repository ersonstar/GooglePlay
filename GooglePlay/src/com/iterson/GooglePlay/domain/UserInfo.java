package com.iterson.GooglePlay.domain;

public class UserInfo {
	private String name;
	private String email;
	private String Url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public UserInfo(String name, String email, String url) {
		super();
		this.name = name;
		this.email = email;
		Url = url;
	}
	public UserInfo() {
		super();
	}
	
	
}
