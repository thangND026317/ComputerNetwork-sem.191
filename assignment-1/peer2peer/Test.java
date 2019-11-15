package peer2peer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class Test {
	public static void main(String args[]) throws IOException {
				
		HashMap<String, String> _userSocket = new HashMap<String, String>();
		_userSocket.put("thang1", "4");
		_userSocket.put("thang2", "5");
		_userSocket.put("thang3", "6");

		Set<String> keys = _userSocket.keySet();
		String list = String.join("@", keys);
		System.out.println(list);
		
		for(Entry<String, String> mapElement : _userSocket.entrySet()) {
			if (_userSocket.get(mapElement.getKey()).equals("5")) _userSocket.remove(mapElement.getKey());
		}
		
		keys = _userSocket.keySet();
		list = String.join("@", keys);
		System.out.println(list);
		
//		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//		while(true) {
//			String message = "";
//			message = input.readLine();
//			String[] request = message.split("@");
//			System.out.println(message);
//			switch (request[0]) {
//				case "friendlist":
//					System.out.println("the friend list");
//					break;
//				case "signup":
//					System.out.println("[Server]: Signed up successfully!");
//					System.out.println("Failed to sign up!");
//					break;
//				case "signin":
//					System.out.println("[Server]: Signed in successfully!");
//					System.out.println("Failed to sign in!");
//					break ;
//				case "chatto":
//					System.out.println("[Server]: I see your request");
//					break;
//			}
//		}
	}
}
