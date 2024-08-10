package keyStore.manager;

public class Key {

	private String user;
	private String password;
	private String website;
	
	public Key(String user, String password, String website) {
		super();
		this.user = user;
		this.password = password;
		this.website = website;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Override
	public String toString() {
		return "Keys [user=" + user + ", password=" + password + ", website=" + website + "]";
	}

}
