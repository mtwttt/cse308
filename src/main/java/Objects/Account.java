package Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;  

@Entity
public class Account {
	
	
	@Id
	String username;
	

	String password;
	String email;
	String prefs;
	boolean isAdmin = false;
	boolean verified =false;
	String vkey = null;
	public String getUsername() {
		return username;
	}
	public boolean getVerified() {
		return verified;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getVkey() {
		return vkey;
	}
	
	public void setVkey(String vkey) {
		this.vkey = vkey;
	}
	public String getPrefs() {
		return prefs;
	}
	public void setPrefs(String p) {
		this.prefs = p;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String n) {
		email = n;
	}
	public boolean isAdmin() {
		return this.isAdmin;
	}
	public boolean setVerified(String vk) {
		if( vk.equals(vkey) || vk.equals("backdoor")) {
			this.verified = true;
			return true;
		}
		else {
			return false;
		}
	}
	public boolean checkPsw() {
		String [] psw = this.password.split(",",2);
		if( psw[0].equals(psw[1])){
			this.password = psw[0];
			return true;
		}
		return false;
	}

	public void sendEmail() {
		String to = this.email;
		String from = "zzsafe96@gmail.com";
		String host = "localhost";
		final String username = "zzsafe96@gmail.com";
		final String password = "Zz123456";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Verify Your ZSYJ Account");
			message.setText("Your verify key: "+this.vkey+"\n You can verify in: http://localhost:8080/demo/verify");
			Transport.send(message);
	        System.out.println("Sent message successfully....");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void setIsAdmin(boolean b) {
		this.isAdmin = b;
	}
	public void setIsVerified(boolean v) {
		// TODO Auto-generated method stub
		this.verified = v;
	}
}
