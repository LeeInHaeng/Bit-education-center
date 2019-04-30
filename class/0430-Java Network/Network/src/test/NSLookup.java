package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class NSLookup {

	public static void main(String[] args) {

		BufferedReader br = null;
		try {
			while(true) {
				System.out.print(" > ");
				InputStreamReader isr = new InputStreamReader(System.in, "utf-8");
				br = new BufferedReader(isr);
				String hostName = br.readLine();
				if(hostName.equals("exit")) {
					break;
				}
				
				InetAddress[] inetAddresses = InetAddress.getAllByName(hostName);

				for(InetAddress addr : inetAddresses) {
					System.out.println(addr.getHostName() + " : " + 
							addr.getHostAddress());
				}
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
