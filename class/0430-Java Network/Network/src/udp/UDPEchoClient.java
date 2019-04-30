package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPEchoClient {

	private static final String SERVER_IP = "192.168.1.22";
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		Scanner scanner = null;
		
		try {
			
			// 1. 소켓 생성
			socket = new DatagramSocket();
			// 스캐너 생성(표준입력 연결)
			scanner = new Scanner(System.in);
	
			while(true) {
				// 2. 키보드 입력 받기
				System.out.print(">> ");
				String line = scanner.nextLine();
				if("quit".contentEquals(line)) {
					break;
				}
				
				// 3. 데이터 쓰기
				byte[] sendData = line.getBytes("utf-8");
				DatagramPacket sendPacket = new DatagramPacket(
						sendData, sendData.length, 
						new InetSocketAddress(SERVER_IP, UDPEchoServer.PORT));
				socket.send(sendPacket);
				
				// 4. 데이터 받기
				DatagramPacket receivePacket = new DatagramPacket(
						new byte[UDPEchoServer.BUFFER_SIZE], UDPEchoServer.BUFFER_SIZE);
				socket.receive(receivePacket);
				byte[] data = receivePacket.getData();
				int length = receivePacket.getLength();
				String message = new String(data, 0, length, "utf-8");
				
				System.out.println("<< " + message);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(scanner != null) {
				scanner.close();
			}
			if(socket != null && !socket.isClosed())
				socket.close();
		}
	}

}
