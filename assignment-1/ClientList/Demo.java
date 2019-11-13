package ClientList;

import java.util.HashMap;

public class Demo {
	
	public class Users {
		// Hash table for username and password
		private HashMap<String, String> _users;
		//Hash table for username and isLoggedIn
		private HashMap<String, Boolean> _userstatus;
		
		public Users() {
			_users = new HashMap<String, String>();
			_userstatus = new HashMap<String, Boolean>();
		}
		
		public boolean signUp(String username, String password) {
			if (_users.containsKey(username)) return false;
			_users.put(username, password);
			_userstatus.put(username, false);
			return true;
		}
		
		public boolean signIn(String username, String password) {
			if (_users.containsKey(username)) {
				if (_users.get(username) == password) {
					_userstatus.put(username, true);
				}
				return true;
			}
			return false;
		}
		
		public boolean changeUsername(String username, String new_username) {
			if (!username.equals(new_username)) {
				if (!_users.containsKey(username) || _users.containsKey(new_username)) return false;
				signUp(new_username, _users.get(username));
				_users.remove(username);
				_userstatus.remove(username);
				return true;
			}
			
			return false;
		}
		
		public boolean changePassword(String username, String new_password) {
			if (_users.containsKey(username) && _users.get(username) != new_password) {
				_users.put(username, new_password);
				_userstatus.put(username, false);
				return true;
			}
			return false;
		}
	}
	
	public static void main(String[] args) {

	}
	
}
