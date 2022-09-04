package login;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import base.MemberRVO;
import base.MemberVO;
import base.NonMemberRVO;
import base.NonMemberVO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.DBUtils;


public class loginController implements Initializable{
	@FXML private TextField textFieldID;
	@FXML private TextField textFieldPw;
	@FXML private Button loginButton;
	@FXML private Button regiButton;
	@FXML private Button nonMemRegiButton;
	private Stage primaryStage;
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	// 로그인 된 사용자 정보 저장
	public static MemberVO mv;
	
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
		
        System.out.println("호출시 자동 실행");
        loginButton.setOnAction(e -> {
        	login(textFieldID.getText(),textFieldPw.getText());
        	
        });
        regiButton.setOnAction(e -> register());
        nonMemRegiButton.setOnAction(e -> nonMemRegi());
    }
    
    public void nonMemRegi() {
    	
    	mv = new MemberVO();
    	System.out.println("비회원로그인선택");
        Stage stage = new Stage();
        Parent root = null;
        try {
           root = FXMLLoader.load(
                 getClass().getResource("../nonMemNotice/nonMemNotice.fxml")
                 );
        } catch (IOException e1) {}
        stage= (Stage)nonMemRegiButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        
	}

	public void register() {
        System.out.println("회원가입선택");
        Stage stage = new Stage();
        Parent root = null;
        try {
           root = FXMLLoader.load(
                 getClass().getResource("../signUp/signUp.fxml")
                 );
        } catch (IOException e1) {}
        stage = (Stage)regiButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
       
	}

	public void login(String m_id,String m_pw) {
		
		 String sql = "SELECT * FROM tbl_member WHERE m_id = ? AND m_pw = ?";
	        
	       
	    	try {
	    		conn = DBUtils.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, m_id);
				pstmt.setString(2, m_pw);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					
					String id = rs.getString("m_id");
					String pw = rs.getString("m_pw");
					String name = rs.getString("m_name");
					String phone = rs.getString("m_phone");
					
					mv= new  MemberVO(id,pw,name,phone);
			    	System.out.println(mv.toString());
					
					
					Stage stage = new Stage();
					Parent root = null;
					   root = FXMLLoader.load(
					         getClass().getResource("../mainPage/mainPage.fxml")
					         );
					stage= (Stage)loginButton.getScene().getWindow();
					stage.setScene(new Scene(root));
					stage.show();

	
					
				
					
				} else {
					System.out.println("로그인실패");
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("로그인 실패");
					alert.setContentText("로그인을 실패하셨습니다.");
					alert.show();
				}
				
			} catch (SQLException | IOException e) {
			}finally {
				DBUtils.close(rs,pstmt);
			}
		
    } // login 메소드
	

	
}// implements initialize
