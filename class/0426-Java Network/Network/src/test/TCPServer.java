package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) {
		
		ServerSocket serverSocket = null;
		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();
			
			// 2. 바인딩(binding)
			//InetAddress inetAddress = InetAddress.getLocalHost();
			//String localhost = inetAddress.getHostAddress();
			//serverSocket.bind(new InetSocketAddress(localhost, 5000));
			serverSocket.bind(new InetSocketAddress("0.0.0.0", 5000));
			
			// 3. accept
			Socket socket = serverSocket.accept(); 
			
			InetSocketAddress inetRemoteSocketAddress = 
					(InetSocketAddress) socket.getRemoteSocketAddress();
			
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();
			
			System.out.println("[server] connected by client" + 
					remoteHostAddress + ":" + 
					remotePort);
			
			try {
				// 4. IO 스트림 받아오기
				BufferedReader in = 
						new BufferedReader(new InputStreamReader(
								socket.getInputStream(), "UTF-8"));
				BufferedWriter ow = 
						new BufferedWriter(new OutputStreamWriter(
								socket.getOutputStream(), "UTF-8"));
				
				while(true) {
					// 5. 데이터 읽기
					String line = in.readLine();
					/*
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer);
					if(readByteCount == -1) {
						// 클라이언트가 정상 종료
						System.out.print("[server] closed by client");
						break;
					}
					
					String data = new String(buffer, 0, readByteCount, "utf-8");
					System.out.println("[server] received : " + data);
					*/
					if(line == null) {
						// 클라이언트가 정상 종료
						System.out.print("[server] closed by client");
						break;
					}
					System.out.println("[server] received : " + line);
					
					// 6. 데이터 쓰기
					//os.write(data.getBytes("utf-8"));
					ow.write(line + "\r\n");
					ow.flush();
				}
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				try {
					if(socket != null && !socket.isClosed())
						socket.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}

			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(serverSocket != null &&
						serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
