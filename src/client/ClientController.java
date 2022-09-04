package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import login.loginController;

public class ClientController implements Initializable {

	@FXML private Button send,exit;
	@FXML private TextField talk;
	@FXML private TextArea show;
	
	public static final String IP ="127.0.0.1";
	public static final int PORT =5001;
	
	Socket server;
	
	PrintWriter pw;
	
	BufferedReader br;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String id = loginController.mv.getM_id();
		startClient();
		send.setOnAction(e->{
			String text = talk.getText().trim();
			if(text.equals("")) {
				chatText("원하시는 내용을 말씀해주세요.");
				talk.requestFocus();
				return;
			}
			chatText(id+":"+text);
			send(1,text);
		});
		exit.setOnAction(e->{
			stopClient();
		});
		

	}

	
	public void startClient() {
		try {
			
			server = new Socket(IP,PORT);
			chatText("[상담채팅방에 입장하셨습니다.]");
			
			pw = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(server.getOutputStream())
					),true);
			
			br = new BufferedReader(new InputStreamReader(server.getInputStream()));
			String name = loginController.mv.getM_id();
			System.out.println(name);
			send(0,name);
		} catch (IOException e) {
			chatText("[상담사와의 연결이 끊어졌습니다.]");
			stopClient();
		}
		receive();
	}
	
	
	public void chatText(String text) {
		Platform.runLater(()->{
			show.appendText(text+"\n");
		});
	}
	
	public void send(int order, String text) {
		
		pw.println(order+":"+text);
		talk.clear();
		talk.requestFocus();
	}
	
	public void stopClient() {
		try {
			chatText("[상담채팅 종료]");
			if(server!=null&&!server.isClosed()) {
				server.close();
			}
		} catch (IOException e) {}
	}
	
	public void receive(){
		Thread t = new Thread(()->{
			while(true) {
				try {
					String receiveData = br.readLine();
					System.out.println(receiveData);
					if(receiveData==null) {
						break;
					}
					
					chatText("상담사:"+receiveData);
					
				} catch (IOException e) {
					System.out.println("상담채팅 오류 :"+e.getMessage());
					break;
				}
				
			}
		});
		t.start();

		
			
		

	}
	
}
