# Java SocketProgram

### java���� final �ǹ�
- Ŭ�������� final : ��� �Ұ���
- �޼��忡�� final : �������̵� �Ұ���
- �������� final : ������ �� ���� �Ұ���

### cmd���� Ŭ���� ����
- workspace���� ������Ʈ���� bin�� ����.
  - java ��Ű��.Ŭ������
  - classpath�� ������ �ʾƼ� ������� ���� ��� -cp �ɼ��� �༭ ��θ� �Է��Ѵ�.
- ��α��� �̵��ؼ� �����ϱ� ���ŷο�� bin ��θ� ȯ�� ������ ��Ƶд�.

### Ŭ���̾�Ʈ ���� ���α׷���
- ���� ����
```java
Socket socket = new Socket();
```
- ���� ����
```java
try {
	socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
} catch (IOException e) {
	e.printStackTrace();
}
```
- Input, Output ��Ʈ�� �޾ƿ��� (������Ʈ�� ���X)
```java
InputStream is = socket.getInputStream();
OutputStream os = socket.getOutputStream();
```
- ���� (������Ʈ�� ���X)
```java
String data = "Hello World!\n";
os.write(data.getBytes("utf-8"))
```
- �б� (������Ʈ�� ���X)
```java
byte[] buffer = new byte[256];
int readByteCount = is.read(buffer); // blocking
if(readByteCount == -1) {
	System.out.println("[client] closed by server");
}
			
data = new String(buffer, 0, readByteCount, "utf-8");
System.out.println("[client] received : " + data);
```

### socket���� ������Ʈ�� ���
- IOStream ����
  - PrintWriter���� true �ɼ� : auto-flush�� true��
  - auto-flush : ���۰� �� ���� �ʴ��� ������ �Ǹ� ��� ������.
```java
BufferedReader br = new BufferedReader(
		new InputStreamReader(
				socket.getInputStream(), "utf-8"));

PrintWriter pr = new PrintWriter(
		new OutputStreamWriter(
				socket.getOutputStream(),"utf-8"), true);
```
- ������ �б�
```java
String data = br.readLine();
if(data == null) {
	log("closed by client");
	break;
}
log("received : " + data);
```
- ������ ����
```java
pr.println(data);
```

# Java SocketProgram ��Ƽ ������

### ���� �ҽ��� ����
- ���� �ʿ��� accept�� ȣ���ϰ� ���� �ʱ� ������ �ϳ��� Ŭ���̾�Ʈ�ۿ� �������� ���Ѵ�.
- accept�� ���� ���� ��� �����ʿ����� Ŭ���̾�Ʈ�� ��û�� backlog�� �ӽ� ���� (����Ʈ ť ����)
- backlog�� ���� client�ʿ����� ����� ��ó�� ��������, ������ ���� �� ���� �ƴϴ�.
  - ������ ��� �Ұ���

### Java �������� ����
- �����带 ����ϸ鼭 �����ڿ� ����ȭ�� ���� lock�� �ɱ� ������ ���ɿ� ����
  - ���� ��ü�� �ƴ� �Һ� ��ü������ �����带 ����ϴ� ���� ���� (�Լ��� ���α׷���)
  - �Լ����� �Ű������� ������ ���� ������ ����ؼ� ���ο��� ó�� �� �����ϴ� ���
  - Java�� Ŭ������ �Լ����� ���� (�ܺ� �ʵ忡 �����ϱ� ������)
- Java 1.8 ���� �Լ��� ���α׷����� ���� �����Ѵ�.

### ��Ƽ ������ ����
- EchoServer ����
```java
while(true) {
	//3. accept
	Socket socket = serverSocket.accept(); //blocking
				
	Thread thread = new EchoServerReceiveThread(socket);
	thread.start();
}
```
- EchoServerReceiveThread Ŭ������ Thread�� extends �ϰ�, run�� �������̵� �����Ѵ�.
- ���� ������ ���� ���
```java
Thread.currentThread().getId()
```

# Ʈ���� ó��

### �� ����
- TCP ���� ����
- HTTP ����

### ����ġ : �����带 ���ؼ� WAS ����
- WAS ���� ���� ó������ ���

### nginx : �̺�Ʈ �帮�� ����� �񵿱� ó��
- ����ġ�� ���� �����ϰ� ������ ����
- ����ġ �տ� nginx�� �����ν� ��κ��� ó���� ���
- ��û ---> SSL ---> nginx ---> 80(����͸�) ---> ����ġ ---> WAS
- nginx �տ� �ٴϽ��� �����ν� ĳ�� �̿�
  - ĳ�� �Ǿ����� ���� Ʈ���ȿ� ���ؼ��� nginx�� �ѱ�

### ��Ŷ �극��Ŀ
- �ܺ� ������ ��ַ� ���� ������ ��� ���ĸ� ���� ���� �ڵ����� �ܺ� ���񽺿� ������ ���� �� �����ϴ� ������ �Ѵ�. ��Ŷ �극��Ŀ�� ����ϴ� ������ ���ø����̼��� �������� ��� ���׷��� ���̴� �� �ִ�.
  - ex) �α��� �ý��� ��ֽ� �α��ΰ� ���õ� ��ٱ���, ���� �ý��� ���� ��� �ٿ�

### VRRP
- ���� �����Ǹ� ���, LB�� ���� ��Ŷ ó��

### API ����
- ���� IO�� ����, ���� ���� ����
- �� ��ũ���ΰ� ���� ��� �۾� ��ü���ٴ� �ٸ� �۾��� ���� ���
- �������� �۾��� �����ѵ�, CPU�� �ٸ� �۾��� ���� �ʿ��� ����
- �̹����� ���� �ν��̳� ��ó�� ��

### DB ����
- ������ �ִ��� �����ϰ� �����.
- Ʈ����� �ɰ��� ����� �߻��� �����Ѵ�.
- OR, IN ��� X ---> full search
  - join�� ���� full search�� ���´�.

### Stateless
- �� ������ WAS�� ��� ���µ� ������� �ʴ´�.
- �̸� ���� ������ Ȯ���� ������ �Ѵ�.
- ���� ���� ������ DB�� ����
- Ŭ���� ȯ���� �⺻������ �� stateless ���
