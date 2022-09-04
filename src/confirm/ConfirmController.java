package confirm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.loginController;
import nonMemNotice.nonMemNoticeController;
import util.DBUtils;
import base.*;
import choice.ChoiceController;

public class ConfirmController implements Initializable {

	@FXML private Button btn1,btn2;
	@FXML private TextField number,phone,date,name,hotel,room,cost;
	private Stage primaryStage;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(loginController.mv.getM_id()!=null) {
			m_print();
		}else {
			nm_print();
		}
		
		System.out.println("호출시 자동 실행");

		btn1.setOnAction(e -> confirm());
		btn2.setOnAction(e -> back());
	} //initialize 종료
		
	public void confirm() {
		System.out.println("확인 선택");
		
		Stage stage = new Stage();
		Parent root = null;
		try {
			root = FXMLLoader.load(
					getClass().getResource("../mainPage/mainPage.fxml") // 4번
					);
		} catch (IOException e1) {}
		stage=(Stage)btn1.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
		
	}
	
	public void back() {
		System.out.println("취소 선택");
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("알림");
		alert.setHeaderText("예약취소를 하시면 예약정보가 전부 삭제됩니다.");
		alert.setContentText("예약취소를 하시겠습니까?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK){ 
			System.out.println("확인");
			if(loginController.mv.getM_id()!=null){
			  m_delete();
			 } else { 
			   nm_delete();
			 }
				  
			Stage stage = new Stage();
			Parent root = null;
			try {
				root = FXMLLoader.load(
						getClass().getResource("../mainPage/mainPage.fxml") // 7번
						);
			} catch (IOException e1) {} 
			stage=(Stage)btn2.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		}else if(result.get() == ButtonType.CANCEL){
			System.out.println("취소");
		}
	}

	public void m_print() {
		//회원예약정보
		 String sql = "SELECT * FROM m_reservations WHERE m_id = ?";
		  
		  try {
			  conn = DBUtils.getConnection();
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setString(1, loginController.mv.getM_id());
			  rs = pstmt.executeQuery();
			  
			  if(rs.next()) {
				int m_reserve_num = rs.getInt("m_reserve_num");
				String m_reserve_hotel = rs.getString("m_reserve_hotel");
				String m_reserve_room = rs.getString("m_reserve_room");
				String m_reserve_date = rs.getString("m_reserve_date");
				String m_name = rs.getString("m_name");
				String m_phone = rs.getString("m_phone");
				
				String mrn = Integer.toString(m_reserve_num);
			  
				number.setText(mrn);
				hotel.setText(m_reserve_hotel);
				room.setText(m_reserve_room);
				date.setText(m_reserve_date);
				name.setText(m_name);
				phone.setText(m_phone);
			  }
			  
			  String sql2 = "SELECT price FROM tbl_hotel WHERE h_name=? AND room=?";
			  pstmt = conn.prepareStatement(sql2);
			  pstmt.setString(1, hotel.getText());
			  pstmt.setString(2, room.getText());
			  rs=pstmt.executeQuery();
			  
			  if(rs.next()) {
				  int p = (int) (rs.getInt("price")*0.9);
				  String pay = Integer.toString(p)+"<회원가 적용>";
				  
				  cost.setText(pay);
			  }
			  
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs,pstmt);
		}
		 
	}
	
	public void nm_print() {
		//비회원예약 정보
		String sql = "SELECT * FROM nm_reservations WHERE nm_phone=?";
		
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nonMemNoticeController.nmv.getNm_phone());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int nm_reserve_num = rs.getInt("nm_reserve_num");
				String nm_reserve_hotel = rs.getString("nm_reserve_hotel");
				String nm_reserve_room = rs.getString("nm_reserve_room");
				String nm_reserve_date = rs.getString("nm_reserve_date");
				String nm_name = rs.getString("nm_name");
				String nm_phone = rs.getString("nm_phone");
				
				String nmrv = Integer.toString(nm_reserve_num);
				
				number.setText(nmrv);
				hotel.setText(nm_reserve_hotel);
				room.setText(nm_reserve_room);
				date.setText(nm_reserve_date);
				name.setText(nm_name);
				phone.setText(nm_phone);
			}
			
			String sql2 = "SELECT price FROM tbl_hotel WHERE h_name=? AND room=?";
			  pstmt = conn.prepareStatement(sql2);
			  pstmt.setString(1, hotel.getText());
			  pstmt.setString(2, room.getText());
			  rs=pstmt.executeQuery();
			  
		  if(rs.next()) {
			  int p = rs.getInt("price");
			  String pay = Integer.toString(p);
				  
			  cost.setText(pay);
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs,pstmt);
		}
	}
	
	public void m_delete() {
		//회원예약취소일 때
		try {
			String sql = "DELETE FROM m_reservations WHERE m_id = ?";
			
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginController.mv.getM_id());
			pstmt.executeUpdate();
		
			String sql2 = "UPDATE tbl_member SET m_reserve_num=null WHERE m_id = ?";
			
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, loginController.mv.getM_id());
			pstmt.executeUpdate();
			
			String change = "UPDATE tbl_hotel SET reservable=? WHERE h_name=? AND room=?";
			pstmt = conn.prepareStatement(change);
			pstmt.setInt(1, 1);
			pstmt.setString(2, hotel.getText());
			pstmt.setString(3, room.getText());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(pstmt);
		}
	}
	
	public void nm_delete() {
		try {
			String sql = "DELETE FROM nm_reservations WHERE nm_phone =?";
			
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nonMemNoticeController.nmv.getNm_phone());
			pstmt.executeUpdate();
			
			String sql2 = "UPDATE tbl_non_member SET nm_reserve_num=null WHERE nm_phone";
					
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, nonMemNoticeController.nmv.getNm_phone());
			pstmt.executeUpdate();
					
			String change = "UPDATE tbl_hotel SET reservable=? WHERE h_name=? AND room=?";
			pstmt = conn.prepareStatement(change);
			pstmt.setInt(1, 1);
			pstmt.setString(2, hotel.getText());
			pstmt.setString(3, room.getText());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(pstmt);
		}
	}
	
}