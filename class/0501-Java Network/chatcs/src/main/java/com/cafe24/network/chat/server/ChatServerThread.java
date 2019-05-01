package com.cafe24.network.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.cafe24.network.chat.domain.User;

public class ChatServerThread extends Thread {

	private ArrayList<User> listUser;
	
	public ChatServerThread(ArrayList<User> listUser) {
		this.listUser = listUser;
	}
	
	@Override
	public void run() {
		
		// 접속한 호스트 정보
		Socket socket = listUser.get(listUser.size()-1).getSocket();
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();
		System.out.println("connected by client[" + remoteHostAddress + ":" + remotePort + "]" );
		
		try {
			// 스트림 얻기
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream(), StandardCharsets.UTF_8));
			
			// 요청 처리
			while(true) {
				String request = br.readLine();
				if(request == null) {
					System.out.println("클라이언트로 부터 연결 끊김");
					break;
				}
				
				// 프로토콜 분석
				System.out.println(request);
			}
			
		} catch(SocketException e) {
			System.out.println("[server] sudden closed by client");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if( socket != null && !socket.isClosed()) {
					socket.close();	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
