package com.cafe24.network.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Iterator;
import java.util.List;

public class ChatServerThread extends Thread {
	
	private String nickName;
	private Socket socket;
	private List<Writer> listWriters;
	
	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}
	
	@Override
	public void run() {
		
		// 접속한 호스트 정보
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();
		System.out.println("connected by client[" + remoteHostAddress + ":" + remotePort + "]" );
		
		try {
			// 스트림 얻기
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter(
					new OutputStreamWriter(
							socket.getOutputStream(), StandardCharsets.UTF_8 ), true );
			
			// 요청 처리
			while(true) {
				String request = br.readLine();
				
				if(request == null) {
					System.out.println("클라이언트로 부터 연결 끊김");
					break;
				}
				
				// 프로토콜 분석
				String[] tokens = request.split( ":" );
				
				if( "join".equals( tokens[0] ) ) {

				   doJoin( tokens[1], pw );

				} else if( "message".equals( tokens[0] ) ) {
				   
				   doMessage( tokens[1], pw );

				} else if( "quit".equals( tokens[0] ) ) {

					doQuit(pw);

				} else {

				   System.out.println( "에러:알수 없는 요청(" + tokens[0] + ")" );
				}

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
	
	private void doJoin( String nickName, Writer writer ) {
		this.nickName = nickName;
		
		String data = nickName + "님이 참여하였습니다.\r\n"; 
		broadcast( data, writer );

		/* writer pool에  저장 */ 
		addWriter(writer);
		
		// ack
		PrintWriter printWriter = (PrintWriter)writer;
		printWriter.println( "join:ok" );
		printWriter.flush();
	}
	private void addWriter( Writer writer ) {
		synchronized( listWriters ) {
			listWriters.add( writer );
		}
	}
	
	private void doMessage(String message, Writer writer) {
		// Base64 디코딩
		Decoder decoder = Base64.getDecoder();
		byte[] decodeMessage;
		
		try {
			decodeMessage = decoder.decode(message.getBytes("utf-8"));
			broadcast(nickName + " >> " + new String(decodeMessage), writer);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private void doQuit(Writer writer) {
		String data = nickName + "님이 퇴장 하였습니다.";
		broadcast(data, writer);
		
		removeWriter(writer);
	}
	
	private void removeWriter( Writer writer ) {
		synchronized( listWriters ) {
			/* ConcurrentModificationException 발생 코드
			for(Writer removeWriter : listWriters) {
				if(writer.equals(removeWriter)) {
					listWriters.remove(removeWriter);
				}
			}*/
			
			for(Iterator<Writer> iter = listWriters.iterator(); iter.hasNext();)
			{
				if(iter.next().equals(writer)) {
					iter.remove(); 
				} 
			}
		}
	}

	private void broadcast( String data, Writer paramWriter ) {
		synchronized( listWriters ) {
			
			for( Writer writer : listWriters ) {
				if(!writer.equals(paramWriter)) {
					PrintWriter printWriter = (PrintWriter)writer;
					printWriter.println( data );
					printWriter.flush();
				}
		    }
		}
	}


}
