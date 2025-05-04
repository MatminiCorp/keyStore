package com.matmini.keyStore.manager;

public class Registry {

	private String name;
	private String url;
	private String username;
	private String password;
	private String note;

	public Registry(String name, String url, String username, String password, String note) {
		this.name = name;
		this.url = url;
		this.username = username;
		this.password = password;
		this.note = note;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Registry [name=" + name + ", url=" + url + ", username=" + username + 
		       ", password=" + password + ", note=" + note + "]";
	}
}
