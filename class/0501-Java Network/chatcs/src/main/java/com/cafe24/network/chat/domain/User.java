package com.cafe24.network.chat.domain;

import java.io.PrintWriter;
import java.net.Socket;

public class User {

	private String nickName;
	private Socket socket;
	private PrintWriter pw;
	
	public User( Socket socket, PrintWriter pw, String nicName) {
		this.socket = socket;
		this.pw = pw;
		this.nickName = nicName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public PrintWriter getPw() {
		return pw;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}

}
