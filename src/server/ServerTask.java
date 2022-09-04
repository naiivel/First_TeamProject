package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Platform;
import javafx.scene.control.Button;
import login.loginController;





public class ServerTask implements Runnable {

	Socket client;
	
	ServerController sc;
	
	PrintWriter pw; // client쪽으로 출력
	BufferedReader br; //client로부터 받음
	
	String userID;
	
	boolean isRun = true;
	
	public ServerTask(Socket client,ServerController sc) {
		this.client = client;
		this.sc = sc;
		
		try {
			OutputStream os = client.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			pw = new PrintWriter(bw,true); 
			
			InputStream is = client.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			
		} catch (IOException e) {
			sc.chatText("고객 연결오류:"+e.getMessage());
		}
	}
	
	
	@Override
	public void run() {
		sc.send.setOnAction(e->{
			String text = sc.talk.getText().trim();
			if(text.equals("")) {
				sc.talk.requestFocus();
				return;
			}
			sc.show.appendText("상담사:"+text+"\n");
			
			pw.println(text);
			sc.talk.clear();
			sc.talk.requestFocus();
		});
		
		
		while(isRun) {
			try {
				String receiveData = br.readLine();
				System.out.println(receiveData);
				if(receiveData==null) {
					break;
				}
				
				String[] data = receiveData.split("\\:");
				System.out.println(data);
				String order =data[0];
				String text = data[1];
				switch(order) {
				case "0":
					this.userID = text;
					sc.chatText(text+"고객님이 입장하셨습니다.");
					sc.ht.put(text,pw);
					break;
				case "1":
					sc.chatText("고객님:"+text);
					break;
				}
				
				
				
			} catch (IOException e) {
				System.out.println("고객 연결 오류"+e.getMessage());
				isRun = false;
			}
		}
		
		if(client!=null&&!client.isClosed()) {
			try {
				client.close();
			} catch (IOException e) {}
		}
		
		sc.ht.remove(userID);
		sc.show.appendText(userID+"님이 나가셨습니다.");
	}
	
	

}
