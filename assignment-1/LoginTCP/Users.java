package LoginTCP;

import java.util.HashMap;

public class Users {
	// Hash table for username and password
	private static HashMap<String, String> _users = new HashMap<String, String>();
	//Hash table for username and isLoggedIn
	private static HashMap<String, Boolean> _userstatus = new HashMap<String, Boolean>();
	
	public static int signUp(String username, String password) {
		if (_users.containsKey(username)) return 0;
		_users.put(username, password);
		_userstatus.put(username, false);
		return 1;
	}
	
	public static int signIn(String username, String password) {
		if (!_users.containsKey(username)) return -1;
		if (_users.get(username) != password) return 0;
		_userstatus.put(username, true);
		return 1;
	}
	
	public static int changeUsername(String username, String new_username) {
		if (new_username.equals(username)) return -1;
		if (_users.containsKey(new_username)) return 0;
		signUp(new_username, _users.get(username));
		_users.remove(username);
		_userstatus.remove(username);
		return 1;
	}
	
	public static int changePassword(String username, String new_password) {
		if (_users.get(username) == new_password) return -1;
			_users.put(username, new_password);
			_userstatus.put(username, false);
			return 1;
		} 
}