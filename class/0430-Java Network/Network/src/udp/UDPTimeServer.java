package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {

	public static final int PORT = 7000;
	public static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			// 1. 소켓 생성
			socket = new DatagramSocket(PORT);
			
			while(true) {
				// 2. 데이터 수신
				DatagramPacket receivePacket = new DatagramPacket(
						new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);
				byte[] data = receivePacket.getData();
				int length = receivePacket.getLength();
				String message = new String(data, 0, length, "utf-8");
				
				System.out.println("[server] received : " + message);
				
				// 3. 데이터 전송		
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
				String date = format.format(new Date());
				byte[] sendData = date.getBytes("utf-8");
				DatagramPacket sendPacket = new DatagramPacket(
						sendData, sendData.length, 
						receivePacket.getAddress(), receivePacket.getPort());
				
				socket.send(sendPacket);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(socket != null && !socket.isClosed())
				socket.close();
		}
		
	}
}
