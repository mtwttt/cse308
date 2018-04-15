package Objects;

public class Account {
	String username;
	Byte[] password;
	boolean isAdmin = false;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Byte[] getPassword() {
		return password;
	}
	public void setPassword(Byte[] password) {
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
