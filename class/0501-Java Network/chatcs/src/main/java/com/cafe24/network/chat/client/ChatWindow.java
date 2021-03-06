package com.cafe24.network.chat.client;

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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class ChatWindow {

	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;
	
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;

	public ChatWindow(String name, Socket socket) {
		frame = new Frame(name);
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 80);
		
		this.socket = socket;
		try {
			this.br = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream(), "utf-8"));
			
			this.pw = new PrintWriter(
					new OutputStreamWriter(
							socket.getOutputStream(), "utf-8"), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void finish() {
		
		pw.println("quit:");
		
		// socket 정리
		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
		
		new ChatClientReceiveThread().start();
	}

	private void updateTextArea(String message) {
		textArea.append(message);
	}

	private void sendMessage() {
		String message = textField.getText();
		
		// Base64 인코딩
		Encoder encoder = Base64.getEncoder();
		byte[] base64Message;
		try {
			base64Message = encoder.encode(message.getBytes("utf-8"));
			System.out.println(new String(base64Message));
			pw.println("message:"+ new String(base64Message));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		textField.setText("");
		textField.requestFocus();

		// test
		updateTextArea(message+"\r\n");
	}

	public class ChatClientReceiveThread extends Thread{

		@Override
		public void run() {
			try {
				while(true) {
					String request;
					request = br.readLine();
					updateTextArea(request+"\r\n");
				}
			} catch(SocketException e) {
				System.out.println("[server] sudden closed by client");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}