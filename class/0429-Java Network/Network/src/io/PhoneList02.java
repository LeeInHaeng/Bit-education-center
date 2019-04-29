package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PhoneList02 {

	public static void main(String[] args) {
		
		Scanner scan = null;
		try {
			File file = new File("phone.txt");
			scan = new Scanner(file);
			
			while(scan.hasNextLine()) {
				String name = scan.next();
				String phone01 = scan.next();
				String phone02 = scan.next();
				String phone03 = scan.next();
				
				System.out.println(name + " : " + phone01 + "-" + phone02 + "-" + phone03);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			if(scan != null) {
				scan.close();
			}
		}
	}

}
