package com.cafe24.network.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import com.cafe24.network.chat.server.MainThread;

public class ChatClientApp {
	
	public static void main(String[] args) {
		String name = null;
		Scanner scanner = new Scanner(System.in);

		while (true) {

			System.out.println("대화명을 입력하세요.");
			System.out.print(">>> ");
			name = scanner.nextLine();

			if (name.isEmpty() == false) {
				break;
			}

			System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
		}
		
		scanner.close();

		// 1. 소켓 만들고
		// 2. iostream
		// 3. join 성공
		// 4.
		
		Socket socket = null;

		try {
			// 소켓 생성
			socket = new Socket();
			
			// 서버 연결
			socket.connect(new InetSocketAddress(MainThread.IP, MainThread.PORT));
			System.out.println(MainThread.IP + ":" + MainThread.PORT + " connected");
			
			//IOStream 생성(받아오기)
			BufferedReader br = new BufferedReader( 
					new InputStreamReader( socket.getInputStream(), "utf-8") );
			
			PrintWriter pw = new PrintWriter(
					new OutputStreamWriter(
							socket.getOutputStream(), "utf-8" ), true );
			
			pw.println("join:" + name);
			
			while(true) {
				String request = br.readLine();
				if("join:ok".equals(request))
					break;
			}
			new ChatWindow(name, socket).show();
			
		} catch(ConnectException e) {
			System.out.println("서버 연결 실패");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}