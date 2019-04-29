package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PhoneList01 {

	public static void main(String[] args) {
	
		BufferedReader br = null;
		try {
			// 기반 스트림
			FileInputStream fs = new FileInputStream("phone.txt");
			
			// 보조 스트림1(bytes --> char)
			InputStreamReader isr = new InputStreamReader(fs, "UTF-8");
			
			br = new BufferedReader(isr);
			
			String line = null;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "\t ");
				
				int index=0;
				while(st.hasMoreElements()) {
					String token = st.nextToken();
					
					System.out.print(token);

					if(index == 0) { // 이름
						System.out.print(" : ");
					} else if(index == 1) {
						System.out.print("-");
					}else if(index == 2) {
						System.out.print("-");
					}
					
					index++;
				}
				System.out.print("\n");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
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
