package com.cafe24.network.chat.server;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.cafe24.network.chat.domain.User;

public class ChatWindow {

	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;
	
	private String nickName;
	public static final int PORT = 7000;
	public static final String IP = "192.168.1.22";

	public ChatWindow(String name) {
		frame = new Frame(name);
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 80);
		
		this.nickName = name;
	}

	public void finish() {
		// socket 정리
		System.exit(0);

	}

	public void show() {
		// Button
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);
		buttonSend.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionEvent) {
				sendMessage();
			}
		});

		// Textfield
		textField.setColumns(80);
		textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if (keyCode == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}

		});

		// Pannel
		pannel.setBackground(Color.LIGHT_GRAY);
		pannel.add(textField);
		pannel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, pannel);

		// TextArea
		textArea.setEditable(false);
		frame.add(BorderLayout.CENTER, textArea);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// System.exit(0);
				finish();
			}
		});
		frame.setVisible(true);
		frame.pack();
		
	}

	private void updateTextArea(String message) {
		textArea.append(message);
	}

	private void sendMessage() {
		String message = textField.getText();
		// pw.println("MSG "+ message);

		textField.setText("");
		textField.requestFocus();

		// test
		updateTextArea(message);
	}

	public class MainThread {

		private ArrayList<User> listUser = new ArrayList<User>();

		public void main(String[] args) {
			ServerSocket serverSocket = null;
			
			try {
				// 서버 소켓 생성
				serverSocket = new ServerSocket();
				
				// 바인딩
				serverSocket.bind(new InetSocketAddress("0.0.0.0", PORT));
				System.out.println("[서버] 연결 기다림  :" + PORT);
				
				// 요청 대기
				while(true) {
					Socket socket = serverSocket.accept();
					
					PrintWriter pw = new PrintWriter(
							new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
					
					listUser.add(new User(socket, pw, nickName));
					new ChatServerThread(listUser).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}

	}

}