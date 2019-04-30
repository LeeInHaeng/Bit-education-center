# UDP
- 비 연결 지향형
- TCP와 달리 연결되지 않은 상태로 데이터 통신을 하기 때문에 패킷이 유실될 가능성이 있다.
- 속도 면에서 큰 장점이 있다.(처음 반응 속도가 빠르다.)

### UDP Echo 서버/클라이언트
- 서버소켓, 클라이언트 소켓의 구분이 없다.
- 멀티쓰레드를 사용하지 않더라도 여러 사용자를 연결할 수 있다.
  - 컨넥션 과정이 없음.
- UDP 소켓 생성
```java
DatagramSocket socket = new DatagramSocket(PORT);
```
- 클라이언트 UDP 소켓 생성
```java
socket = new DatagramSocket();
```
- 데이터 수신
```java
DatagramPacket receivePacket = new DatagramPacket(
		new byte[BUFFER_SIZE], BUFFER_SIZE);
socket.receive(receivePacket);
byte[] data = receivePacket.getData();
int length = receivePacket.getLength();
String message = new String(data, 0, length, "utf-8");
```
- 데이터 전송
```java
byte[] sendData = message.getBytes("utf-8");
DatagramPacket sendPacket = new DatagramPacket(
		sendData, sendData.length, 
		receivePacket.getAddress(), receivePacket.getPort());
socket.send(sendPacket);
```
- 클라이언트 데이터 전송
```java
byte[] sendData = line.getBytes("utf-8");
DatagramPacket sendPacket = new DatagramPacket(
		sendData, sendData.length, 
		new InetSocketAddress(SERVER_IP, UDPEchoServer.PORT));
socket.send(sendPacket)
```
