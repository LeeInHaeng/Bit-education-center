# UDP
- �� ���� ������
- TCP�� �޸� ������� ���� ���·� ������ ����� �ϱ� ������ ��Ŷ�� ���ǵ� ���ɼ��� �ִ�.
- �ӵ� �鿡�� ū ������ �ִ�.(ó�� ���� �ӵ��� ������.)

### UDP Echo ����/Ŭ���̾�Ʈ
- ��������, Ŭ���̾�Ʈ ������ ������ ����.
- ��Ƽ�����带 ������� �ʴ��� ���� ����ڸ� ������ �� �ִ�.
  - ���ؼ� ������ ����.
- UDP ���� ����
```java
DatagramSocket socket = new DatagramSocket(PORT);
```
- Ŭ���̾�Ʈ UDP ���� ����
```java
socket = new DatagramSocket();
```
- ������ ����
```java
DatagramPacket receivePacket = new DatagramPacket(
		new byte[BUFFER_SIZE], BUFFER_SIZE);
socket.receive(receivePacket);
byte[] data = receivePacket.getData();
int length = receivePacket.getLength();
String message = new String(data, 0, length, "utf-8");
```
- ������ ����
```java
byte[] sendData = message.getBytes("utf-8");
DatagramPacket sendPacket = new DatagramPacket(
		sendData, sendData.length, 
		receivePacket.getAddress(), receivePacket.getPort());
socket.send(sendPacket);
```
- Ŭ���̾�Ʈ ������ ����
```java
byte[] sendData = line.getBytes("utf-8");
DatagramPacket sendPacket = new DatagramPacket(
		sendData, sendData.length, 
		new InetSocketAddress(SERVER_IP, UDPEchoServer.PORT));
socket.send(sendPacket)
```
