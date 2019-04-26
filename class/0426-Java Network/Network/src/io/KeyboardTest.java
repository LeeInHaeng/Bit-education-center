package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KeyboardTest {

	public static void main(String[] args) {
		
		BufferedReader br = null;
		try {
			// 기반 스트림(표준 입력, 키보드, System.in)
			
			// 보조 스트림1
			// byte|byte|byte --> char
			InputStreamReader isr = new InputStreamReader(System.in, "utf-8");
			
			// 보조 스트림2
			// char|char|char|\n --> string
			br = new BufferedReader(isr);
			
			// read
			String line = null;
			while((line = br.readLine()) != null) {
				if("exit".equals(line))
					break;
				System.out.println(">> " + line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
