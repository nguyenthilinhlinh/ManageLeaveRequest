package helper;

import java.util.*;

public class Valid {
	public static String input(String regex, String message) {
		Scanner sc = new Scanner(System.in);
		System.out.println(message);
		while(true) {
			String str = sc.nextLine();
			if(str.matches(regex)) {
				return str;
			}else {
				System.out.println("vui lòng nhập lại");
			}
		}
	}
}
