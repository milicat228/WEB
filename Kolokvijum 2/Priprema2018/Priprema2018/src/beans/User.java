package beans;

import java.util.HashMap;

public class User {
	private String username;
	private String password;
	private Boolean loggedIn = false;
	private HashMap<String,Account> accounts = new HashMap<String,Account>();
	
	public User(String username, String password){
		this.username = username;
		this.password = password;		
	}
	
	public HashMap<String,Account> getAccounts(){
		return accounts;
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
	public Boolean getLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}	
}
