# CentOS6 ȯ�漳��

### ���� IP ����
- vi /etc/sysconfig/network-scripts/ifcfg-eth0
```
BOOTPROTO=static
ONBOOT=yes

IPADDR=192.168.1.51
NETMASK=255.255.255.0
GATEWAY=192.168.1.1
DNS1=168.126.63.1
```
- ���� �� service network restart

# IO �۾�

### IO (KeyboardTest)
- IO�� ���õ� �͵��� try catch ������ ����ؾ� �Ѵ�.
- �⺻ IO(��� ��Ʈ��)�� byte�� ���� ���, String ��ü ���� �� utf-8�� ��ȯ�� �־�� �Ѵ�.
  - ��� ��Ʈ��(ǥ�� �Է�, Ű����, System.in)
  - ���Ǹ� ���� ���� ��Ʈ�� ���
- InputStreamReader : ���� ��Ʈ��, �ڵ� ���ڵ�
```java
// byte|byte|byte --> char
InputStreamReader isr = new InputStreamReader(System.in, "utf-8");
```
- BufferedReader : ���� ���۰� �� �������� ��ũ IO�� ���� ����
  - IO�� ���̱� ���� ���
  - �ڵ� ���� ó���� ���� ���� ������ �б� ��������.
```java
// char|char|char|\n --> string
BufferedReader br = new BufferedReader(isr);
```
- Scanner : InputStreaReader�� BufferedReader�� ����ϱ� ���� �����س��� ��ƿ

### ���� ��Ʈ�� (PhoneList01)
- ��� ��Ʈ��
```java
FileInputStream fs = new FileInputStream("phone.txt");
```
- ���� ��Ʈ��1(bytes --> char)
```java
InputStreamReader isr = new InputStreamReader(fs, "UTF-8");
```
- ���� ��Ʈ��2(char --> string)
```java
BufferedReader br = new BufferedReader(isr);
```

### ��ũ������ (Scanner ��� X) (PhoneList01)
- ù ��° ���� : ��ũ������ �� String
- �� ��° ���� : ����
  - �ǰ� �����̽��� ���
```java
StringTokenizer st = new StringTokenizer(line, "\t ");

while(st.hasMoreElements()) {
	String token = st.nextToken();
```

### Scanner ��� (PhoneList02)
- �⺻������ ��, �����̽�, ������ ��ũ�������� �����Ǿ� �ִ�.
- ��ũ���������� �ڵ�絵 �������� �ξ� ���ϴ�.
```java
File file = new File("phone.txt");
scan = new Scanner(file);
			
while(scan.hasNextLine()) {
	String name = scan.next();
	String phone01 = scan.next();
	String phone02 = scan.next();
	String phone03 = scan.next();
				
	System.out.println(name + " : " + phone01 + "-" + phone02 + "-" + phone03);
}
```

# ��Ʈ��ũ ���α׷���

### InetAddress Ŭ���� (Localhost)
- InetAddress : IP Address�� ���� �ִ�.
  - getLocalHost() Ȥ�� getAllByName()�� �̿��� ���ͳݿ� ���� ������ ���´�.
- InetAddress + port ��ȣ : InetSocketAddress
- NSLookup ����

### TCP ���� ���α׷��� (TCPServer)
- ���� ���� ����
```java
ServerSocket serverSocket = new ServerSocket();
```
- ���ε�
  - Socket�� SocketAddress(IPAddress + Port)�� ���ε� �Ѵ�.
```java
InetAddress inetAddress = InetAddress.getLocalHost();
String localhost = inetAddress.getHostAddress();
serverSocket.bind(new InetSocketAddress(localhost, 5000));
```
- accept
  - Ŭ���̾�Ʈ�� ���� ��û�� ��ٸ���.
  - Ŭ���̾�Ʈ�� ������ �Ǳ� ������ blocking ���°� �ȴ�.
```java
Socket socket = serverSocket.accept(); 
```

- Ŭ���̾�Ʈ�� ����� ���� ���
```java
InetSocketAddress inetRemoteSocketAddress = 
		(InetSocketAddress) socket.getRemoteSocketAddress();
			
String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
int remotePort = inetRemoteSocketAddress.getPort()
```
- IO ��Ʈ�� �޾ƿ��� 
```java
InputStream is = socket.getInputStream();
OutputStream os = socket.getOutputStream();
```
- ������ �а� ����
```java
while(true) {
	// 5. ������ �б�
	byte[] buffer = new byte[256];
	int readByteCount = is.read(buffer);
	if(readByteCount == -1) {
		// Ŭ���̾�Ʈ�� ���� ����
		System.out.print("[server] closed by client");
		break;
	}
					
	String data = new String(buffer, 0, readByteCount, "utf-8");
	System.out.println("[server] received : " + data);
					
	// 6. ������ ����
	os.write(data.getBytes("utf-8"));
}
```
- IO ��Ʈ�� �޾ƿ��� (BufferedReader, BufferedWriter)
```java
BufferedReader in = 
		new BufferedReader(new InputStreamReader(
				socket.getInputStream(), "UTF-8"));
BufferedWriter ow = 
		new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream(), "UTF-8"));
```
- ������ �а� ���� (BufferedReader, BufferedWriter)
```java
while(true) {
	if(line == null) {
		// Ŭ���̾�Ʈ�� ���� ����
		System.out.print("[server] closed by client");
		break;
	}
	System.out.println("[server] received : " + line);
					
	// 6. ������ ����
	ow.write(line + "\r\n");
	ow.flush();
}
```