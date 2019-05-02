package com.cafe24.network.chat.server;

import java.io.IOException;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainThread extends Thread {
	
	public static final int PORT = 9996;
	public static final String IP = "192.168.1.22";
	private static List<Writer> listWriters = new ArrayList<Writer>();

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		try {
			// 서버 소켓 생성
			serverSocket = new ServerSocket();
			
			// 바인딩
			serverSocket.bind(new InetSocketAddress("0.0.0.0", PORT));
			System.out.println("[서버] 연결 기다림  :" + PORT);
			
			// 요청 대기
			while(true) {
				Socket socket = serverSocket.accept();
				
				new ChatServerThread( socket, listWriters ).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
