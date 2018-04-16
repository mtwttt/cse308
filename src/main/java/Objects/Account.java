package Objects;

public class Account {
	String username;
	String password;
	boolean isAdmin = true;
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
	public boolean validate() {
		this.isAdmin = true;
		return true;
	}
	public boolean isAdmin() {
		return this.isAdmin;
	}
	
}
