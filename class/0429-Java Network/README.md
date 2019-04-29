# Java SocketProgram

### java에서 final 의미
- 클래스에서 final : 상속 불가능
- 메서드에서 final : 오버라이딩 불가능
- 변수에서 final : 변수의 값 변경 불가능

### cmd에서 클래스 수행
- workspace에서 프로젝트명의 bin에 들어간다.
  - java 패키지.클래스명
  - classpath가 잡히지 않아서 수행되지 않을 경우 -cp 옵션을 줘서 경로를 입력한다.
- 경로까지 이동해서 수행하기 번거로우면 bin 경로를 환경 변수로 잡아둔다.

### 클라이언트 소켓 프로그래밍
- 소켓 생성
```java
Socket socket = new Socket();
```
- 서버 연결
```java
try {
	socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
} catch (IOException e) {
	e.printStackTrace();
}
```
- Input, Output 스트림 받아오기 (보조스트림 사용X)
```java
InputStream is = socket.getInputStream();
OutputStream os = socket.getOutputStream();
```
- 쓰기 (보조스트림 사용X)
```java
String data = "Hello World!\n";
os.write(data.getBytes("utf-8"))
```
- 읽기 (보조스트림 사용X)
```java
byte[] buffer = new byte[256];
int readByteCount = is.read(buffer); // blocking
if(readByteCount == -1) {
	System.out.println("[client] closed by server");
}
			
data = new String(buffer, 0, readByteCount, "utf-8");
System.out.println("[client] received : " + data);
```

### socket에서 보조스트림 사용
- IOStream 생성
  - PrintWriter에서 true 옵션 : auto-flush를 true로
  - auto-flush : 버퍼가 다 차지 않더라도 개행이 되면 즉시 보낸다.
```java
BufferedReader br = new BufferedReader(
		new InputStreamReader(
				socket.getInputStream(), "utf-8"));

PrintWriter pr = new PrintWriter(
		new OutputStreamWriter(
				socket.getOutputStream(),"utf-8"), true);
```
- 데이터 읽기
```java
String data = br.readLine();
if(data == null) {
	log("closed by client");
	break;
}
log("received : " + data);
```
- 데이터 쓰기
```java
pr.println(data);
```

# Java SocketProgram 멀티 쓰레드

### 기존 소스의 문제
- 서버 쪽에서 accept를 호출하고 있지 않기 때문에 하나의 클라이언트밖에 연결하지 못한다.
- accept를 하지 못한 경우 서버쪽에서는 클라이언트의 요청을 backlog에 임시 저장 (컨넥트 큐 역할)
- backlog로 인해 client쪽에서는 연결된 것처럼 보이지만, 실제로 연결 된 것은 아니다.
  - 데이터 통신 불가능

### Java 쓰레드의 문제
- 쓰레드를 사용하면서 공유자원 동기화를 위해 lock을 걸기 때문에 성능에 문제
  - 공유 객체가 아닌 불변 객체에서만 쓰레드를 사용하는 것을 권장 (함수형 프로그래밍)
  - 함수형은 매개변수가 들어오면 내부 변수를 사용해서 내부에서 처리 후 리턴하는 방식
  - Java의 클래스는 함수형에 위반 (외부 필드에 접근하기 때문에)
- Java 1.8 부터 함수형 프로그래밍을 많이 지원한다.

### 멀티 쓰레드 구현
- EchoServer 수정
```java
while(true) {
	//3. accept
	Socket socket = serverSocket.accept(); //blocking
				
	Thread thread = new EchoServerReceiveThread(socket);
	thread.start();
}
```
- EchoServerReceiveThread 클래스는 Thread를 extends 하고, run을 오버라이딩 구현한다.
- 현재 쓰레드 정보 얻기
```java
Thread.currentThread().getId()
```

# 트래픽 처리

### 웹 서버
- TCP 세션 구성
- HTTP 응답

### 아파치 : 쓰레드를 통해서 WAS 수행
- WAS 와의 정보 처리만을 담당

### nginx : 이벤트 드리븐 방식의 비동기 처리
- 아파치에 비해 월등하게 성능이 높음
- 아파치 앞에 nginx를 둠으로써 대부분의 처리를 담당
- 요청 ---> SSL ---> nginx ---> 80(모니터링) ---> 아파치 ---> WAS
- nginx 앞에 바니쉬를 둠으로써 캐시 이용
  - 캐시 되어있지 않은 트래픽에 대해서만 nginx로 넘김

### 서킷 브레이커
- 외부 서비스의 장애로 인한 연쇄적 장애 전파를 막기 위해 자동으로 외부 서비스와 연결을 차단 및 복구하는 역할을 한다. 서킷 브레이커를 사용하는 목적은 애플리케이션의 안정성과 장애 저항력을 높이는 데 있다.
  - ex) 로그인 시스템 장애시 로그인과 관련된 장바구니, 결제 시스템 등이 모두 다운

### VRRP
- 가상 아이피를 잡고, LB를 통해 패킷 처리

### API 서버
- 파일 IO가 많은, 정적 파일 서빙
- 웹 스크래핑과 같은 디비 작업 자체보다는 다른 작업이 많은 경우
- 독립적인 작업이 가능한데, CPU나 다른 작업이 많이 필요한 서버
- 이미지의 영상 인식이나 전처리 등

### DB 서버
- 쿼리를 최대한 간단하게 만든다.
- 트랜잭션 쪼개서 데드락 발생을 방지한다.
- OR, IN 사용 X ---> full search
  - join을 통해 full search를 막는다.

### Stateless
- 웹 서버와 WAS에 어떠한 상태도 기록하지 않는다.
- 이를 통해 서버의 확장을 쉽도록 한다.
- 세션 등의 정보를 DB에 저장
- 클라우드 환경은 기본적으로 다 stateless 방식
