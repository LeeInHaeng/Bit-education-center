# CentOS6 환경설정

### 고정 IP 설정
- vi /etc/sysconfig/network-scripts/ifcfg-eth0
```
BOOTPROTO=static
ONBOOT=yes

IPADDR=192.168.1.51
NETMASK=255.255.255.0
GATEWAY=192.168.1.1
DNS1=168.126.63.1
```
- 수정 후 service network restart

# IO 작업

### IO (KeyboardTest)
- IO와 관련된 것들은 try catch 구문을 사용해야 한다.
- 기본 IO(기반 스트림)는 byte를 받을 경우, String 객체 생성 후 utf-8로 변환해 주어야 한다.
  - 기반 스트림(표준 입력, 키보드, System.in)
  - 편의를 위해 보조 스트림 사용
- InputStreamReader : 보조 스트림, 자동 인코딩
```java
// byte|byte|byte --> char
InputStreamReader isr = new InputStreamReader(System.in, "utf-8");
```
- BufferedReader : 내부 버퍼가 다 찰때까지 디스크 IO를 하지 않음
  - IO를 줄이기 위해 사용
  - 자동 개행 처리로 인해 라인 단위로 읽기 쉬워진다.
```java
// char|char|char|\n --> string
BufferedReader br = new BufferedReader(isr);
```
- Scanner : InputStreaReader와 BufferedReader를 사용하기 쉽게 구현해놓은 유틸

### 파일 스트림 (PhoneList01)
- 기반 스트림
```java
FileInputStream fs = new FileInputStream("phone.txt");
```
- 보조 스트림1(bytes --> char)
```java
InputStreamReader isr = new InputStreamReader(fs, "UTF-8");
```
- 보조 스트림2(char --> string)
```java
BufferedReader br = new BufferedReader(isr);
```

### 토크나이저 (Scanner 사용 X) (PhoneList01)
- 첫 번째 인자 : 토크나이저 할 String
- 두 번째 인자 : 패턴
  - 탭과 스페이스일 경우
```java
StringTokenizer st = new StringTokenizer(line, "\t ");

while(st.hasMoreElements()) {
	String token = st.nextToken();
```

### Scanner 사용 (PhoneList02)
- 기본적으로 탭, 스페이스, 개행이 토크나이저로 구현되어 있다.
- 토크나이저보다 코드양도 적어지고 훨씬 편하다.
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

# 네트워크 프로그래밍

### InetAddress 클래스 (Localhost)
- InetAddress : IP Address를 갖고 있다.
  - getLocalHost() 혹은 getAllByName()을 이용해 인터넷에 대한 정보를 얻어온다.
- InetAddress + port 번호 : InetSocketAddress
- NSLookup 구현

### TCP 서버 프로그래밍 (TCPServer)
- 서버 소켓 생성
```java
ServerSocket serverSocket = new ServerSocket();
```
- 바인딩
  - Socket에 SocketAddress(IPAddress + Port)를 바인딩 한다.
```java
InetAddress inetAddress = InetAddress.getLocalHost();
String localhost = inetAddress.getHostAddress();
serverSocket.bind(new InetSocketAddress(localhost, 5000));
```
- accept
  - 클라이언트의 연결 요청을 기다린다.
  - 클라이언트와 연결이 되기 전까지 blocking 상태가 된다.
```java
Socket socket = serverSocket.accept(); 
```

- 클라이언트와 연결된 소켓 얻기
```java
InetSocketAddress inetRemoteSocketAddress = 
		(InetSocketAddress) socket.getRemoteSocketAddress();
			
String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
int remotePort = inetRemoteSocketAddress.getPort()
```
- IO 스트림 받아오기 
```java
InputStream is = socket.getInputStream();
OutputStream os = socket.getOutputStream();
```
- 데이터 읽고 쓰기
```java
while(true) {
	// 5. 데이터 읽기
	byte[] buffer = new byte[256];
	int readByteCount = is.read(buffer);
	if(readByteCount == -1) {
		// 클라이언트가 정상 종료
		System.out.print("[server] closed by client");
		break;
	}
					
	String data = new String(buffer, 0, readByteCount, "utf-8");
	System.out.println("[server] received : " + data);
					
	// 6. 데이터 쓰기
	os.write(data.getBytes("utf-8"));
}
```
- IO 스트림 받아오기 (BufferedReader, BufferedWriter)
```java
BufferedReader in = 
		new BufferedReader(new InputStreamReader(
				socket.getInputStream(), "UTF-8"));
BufferedWriter ow = 
		new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream(), "UTF-8"));
```
- 데이터 읽고 쓰기 (BufferedReader, BufferedWriter)
```java
while(true) {
	if(line == null) {
		// 클라이언트가 정상 종료
		System.out.print("[server] closed by client");
		break;
	}
	System.out.println("[server] received : " + line);
					
	// 6. 데이터 쓰기
	ow.write(line + "\r\n");
	ow.flush();
}
```