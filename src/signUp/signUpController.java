package signUp;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import base.DynamicHotels;
import base.MemberVO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import login.loginController;
import util.DBUtils;

public class signUpController implements Initializable {
	DynamicHotels dh= new DynamicHotels();
	@FXML private Button btnIdCheck,btnConfirm,btnCancel;
	@FXML private TextField textID,textName,textPhone;
	@FXML private PasswordField textPW,textPWC;
	@FXML private Label IDC,PWC;
	private Stage primaryStage;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	MemberVO mv;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("호출시 자동 실행");
		btnIdCheck.setOnAction(e -> IDCCheck(textID.getText()));
		btnConfirm.setOnAction(e -> {
			if(textID.getText().trim().equals("") || textPW.getText().trim().equals("") || 
					textPWC.getText().trim().equals("") 
					|| textName.getText().trim().equals("") || textPhone.getText().trim().equals("")) {
			message("입력하지 않은 정보가 있습니다.");
			}else {
				signUp();
			}
		});
		btnCancel.setOnAction(e -> signUpCancel());
		
		
		//비밀번호, 비밀번호 확인 실시간 검사
				textPWC.textProperty().addListener(e->{
					if(textPW.getText() == null) {
						PWC.setText("PWC");
					} else if(!textPW.getText().equals(textPWC.getText())){
						PWC.setText("비밀번호가 일치하지 않습니다.");
						PWC.setTextFill(Color.RED);
					} else if(textPW.getText().equals(textPWC.getText())) {
						PWC.setText("비밀번호가 일치합니다.");
						PWC.setTextFill(Color.BLUE);
					}
				});
		
		//텍스트 입력창에 함수입력
		textID.textProperty().addListener((attribute, before, after)->{
			idlengthCheck();
		});
		
		textPW.textProperty().addListener((attribute, before, after)->{
			pwlengthCheck();
		});
		
		textPWC.textProperty().addListener((attribute, before, after)->{
			pwlengthCheck();
		});
		
		textName.textProperty().addListener((attribute, before, after)->{
			pwlengthCheck();
		});
				
		textPhone.textProperty().addListener((attribute, before, after)->{
			pwlengthCheck();
		});
		
	}//initialize 종료


	public void IDCCheck(String m_id) {
		//IDC 버튼
		  try {
			String sql = "SELECT m_id FROM tbl_member WHERE m_id = ?";
			  
			  conn = DBUtils.getConnection();
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setString(1,m_id);
			  rs = pstmt.executeQuery();
			  
			  
			  if(rs.next()){
			 	  message("이미 존재하는 아이디입니다.");
			  } else {
			 	 message("사용가능한 아이디입니다.");
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs,pstmt);
		}
		  
		 
		 
	}
	
	public void signUp() {
		System.out.println("회원가입 선택");
		dbJoin();
		buttonClick();
		Stage stage = new Stage();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
		} catch (IOException e1) {}
		stage=(Stage)btnConfirm.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public void signUpCancel() {
		System.out.println("취소 선택");
		Stage stage = new Stage();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
		} catch (IOException e1) {}
		stage=(Stage)btnConfirm.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public void buttonClick() {
		idlengthCheck();
		pwlengthCheck();
	}
	
	public void idlengthCheck() {
		if(textID.getLength()>8) {
			String tmp = textID.getText();
			tmp = tmp.substring(0,8);
			textID.setText(tmp);
		}
	}
	
	public void pwlengthCheck() {
		if(textPW.getLength()>10) {
			String tmp = textPW.getText();
			tmp = tmp.substring(0,10);
			textPW.setText(tmp);
		}
	}
	
	
	
	public void message(String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(content);
		alert.show();
	}
	
	public void dbJoin() {
		
		//String sql= "INSERT INTO tbl_member(m_id, m_pw, m_name, m_phone) VALUES (?,?,?,?)";
		try {

			String sql= "INSERT INTO tbl_member(m_id, m_pw, m_name, m_phone) VALUES (?,?,?,?)";
			conn = DBUtils.getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, textID.getText());
			pstmt.setString(2, textPW.getText());
			pstmt.setString(3, textName.getText());
			pstmt.setString(4, textPhone.getText());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(pstmt);
		}
	}
	
}