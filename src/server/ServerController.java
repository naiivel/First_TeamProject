package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import login.loginController;

public class ServerController implements Initializable {

	@FXML public Button send;
	@FXML private Button start;
	@FXML public TextField talk;
	@FXML public TextArea show;
	
	public static final String IP ="127.0.0.1";
	public static final int PORT =5001;
	
	
	
	ExecutorService serverPool;
	
	ServerSocket serverSocket;
	
	Hashtable<String,PrintWriter> ht;
	
	PrintWriter pw;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		start.setOnAction(e->{
			if(start.getText().equals("START")) {
				startServer();
			}else {
				stopServer();
			}
		});
		
		

	}
	
	
	public void startServer() {
		serverPool = Executors.newFixedThreadPool(20);
		ht = new Hashtable<>();
		
		try {
			System.out.println("채팅서버 오픈");
			serverSocket = new ServerSocket();
			chatText("[상담서버 연결]");
			serverSocket.bind(new InetSocketAddress(IP,PORT));
			
		} catch (IOException e) {
			System.out.println("사용할 수 없는 IP또는 Port번호 입니다."+e.getMessage());
			stopServer();
			return;
		}
		chatText("[고객 상담 창 오픈]");
		Runnable run = new Runnable() {

			@Override
			public void run() {
				while(true) {
					try {
						Platform.runLater(()->{
							start.setText("STOP");
						});
						
						
						chatText("상담 대기중");
						Socket client = serverSocket.accept();
						String clientIP = client.getInetAddress().getHostAddress();
						System.out.println(clientIP+"-접속");
						
						ServerTask sc = new ServerTask(client,ServerController.this);
						serverPool.submit(sc);
						
						pw = new PrintWriter(new BufferedWriter(
								new OutputStreamWriter(client.getOutputStream())
								),true);
						
					} catch (IOException e) {
						stopServer();
						break;
					}
					}
				
			}
		};
		serverPool.submit(run);
	
	}
	
	public void chatText(String text) {
		Platform.runLater(()->{
			show.appendText(text+"\n");
		});
	}
	
	
	public void stopServer() {
		try {
			if(ht != null) {
				for(PrintWriter pw : ht.values()) {
					if(pw != null) {
						pw.close();
					}
				}
			}
			
			if(serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			
			if(serverPool != null && !serverPool.isShutdown()) {
				serverPool.shutdownNow();
			}
			chatText("[상담 연결이 끊어졌습니다]");
			start.setText("START");
		} catch (IOException e) {}
	}
	
	
	
	

	
	
}
