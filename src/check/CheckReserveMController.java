package check;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import base.MemberRVO;
import base.MemberVO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import login.loginController;
import util.DBUtils;
import confirm.ConfirmController;

public class CheckReserveMController implements Initializable {
	@FXML
	private TextField mrvNumber, mrvName, mrvHotel, mrvDate, mrvPhone, mrvRoom;
	@FXML
	private Button btnMain, btnCancel;
	@FXML
	private Label lblName;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		lblName.setText(loginController.mv.getM_name());
		selectReserve(loginController.mv);
		
		btnMain.setOnAction(event -> {
			Stage stage = new Stage();
			Parent root = null;

			try {
				root = FXMLLoader.load(getClass().getResource("../mainPage/mainPage.fxml"));
				stage = (Stage) btnMain.getScene().getWindow();
				stage.setScene(new Scene(root));
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		btnCancel.setOnAction(e->{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("주의사항을 확인하셨나요?");
			alert.setContentText("OK를 누르시면 예약정보가 삭제됩니다.\n예약을 취소하시겠습니까?");
			
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK){ 
				
				m_delete();
				
				Stage stage = new Stage();
				Parent root = null;
				try {
					root = FXMLLoader.load(
							getClass().getResource("../mainPage/mainPage.fxml") // 7번
							);
				} catch (IOException e1) {} 
				stage=(Stage)btnCancel.getScene().getWindow();
				stage.setScene(new Scene(root));
				stage.show();
			}else if(result.get() == ButtonType.CANCEL){
				System.out.println("취소");
			}
			
		});
		
	} // initialize

	
	public void selectReserve(MemberVO m) {
		String sql = "SELECT * FROM m_reservations WHERE m_id = ? AND m_reserve_num=?";

		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getM_id());
			pstmt.setLong(2, m.getM_reserve_num());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int m_reserve_num = rs.getInt("m_reserve_num");
				String m_reserve_hotel = rs.getString("m_reserve_hotel");
				String m_reserve_room = rs.getString("m_reserve_room");
				String m_reserve_date = rs.getString("m_reserve_date");
				String m_name = rs.getString("m_name");
				String m_phone = rs.getString("m_phone");

				String mrn = Integer.toString(m_reserve_num);
				
				mrvNumber.setText(mrn);
				mrvName.setText(m_name); 
				mrvHotel.setText(m_reserve_hotel);
				mrvDate.setText(m_reserve_date);
				mrvPhone.setText(m_phone);
				mrvRoom.setText(m_reserve_room);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt);
		}
	
	}//selectRev

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
			pstmt.setString(2, mrvHotel.getText());
			pstmt.setString(3, mrvRoom.getText());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(pstmt);
		}
	}


	
	 

	

}// class