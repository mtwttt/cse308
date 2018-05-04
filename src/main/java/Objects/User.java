package Objects;

public class User {
	String firstName;
	String lastName;
	String email;
	String password;
	
	public String getPassword() {
		return password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setFirstName(String f) {
		firstName = f;
	}
	public void setLastName(String l) {
		lastName = l;
	}
	public void setEmail(String e) {
		email = e;
	}
	public void setPassword(String p) {
		password = p;
	}
	
}
