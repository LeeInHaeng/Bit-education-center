package com.cafe24.network.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import com.cafe24.network.chat.server.ChatWindow;

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

		// 1. 소켓 만들고
		// 2. iostream
		// 3. join 성공
		// 4.

		new ChatWindow(name).show();
		
		Socket socket = null;

		try {
			// 소켓 생성
			socket = new Socket();
			
			// 서버 연결
			socket.connect(new InetSocketAddress(ChatWindow.IP, ChatWindow.PORT));
			System.out.println(ChatWindow.IP + ":" + ChatWindow.PORT + " connected");
			
			//IOStream 생성(받아오기)
			BufferedReader br = new BufferedReader( 
					new InputStreamReader( socket.getInputStream(), "utf-8") );
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(scanner != null) {
					scanner.close();
				}		
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}