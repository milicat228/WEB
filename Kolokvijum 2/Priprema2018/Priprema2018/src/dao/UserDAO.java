package dao;

import java.util.HashMap;
import java.util.Map;

import beans.User;

public class UserDAO {
	private Map<String, User> users = new HashMap<>();
	
	public UserDAO(){
		users.put("admin", new User("admin", "admin"));
		users.put("guest", new User("guest", "guest"));
	}
	
	public User find(String username, String password) {
		if (!users.containsKey(username)) {
			return null;
		}
		User user = users.get(username);
		if (!user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}
	
}
