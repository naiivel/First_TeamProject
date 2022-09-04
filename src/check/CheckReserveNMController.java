package check;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import base.MemberVO;
import base.NonMemberRVO;
import base.NonMemberVO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import login.loginController;
import nonMemNotice.nonMemNoticeController;
import util.DBUtils;

public class CheckReserveNMController implements Initializable {
	@FXML
	private TextField nmrvNumber, nmrvName, nmrvHotel, nmrvDate, nmrvPhone, nmrvRoom;
	@FXML
	private Button btnMain, btnCancel;
	@FXML
	private Label lblName;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		lblName.setText(nonMemNoticeController.nmv.getNm_name());
		selectReserve(nonMemNoticeController.nmv);

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
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("알림");
			alert.setHeaderText("주의사항을 확인하셨나요?");
			alert.setContentText("OK를 누르시면 예약정보가 삭제됩니다.\n예약을 취소하시겠습니까?");
			
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK){ 
				
				nm_delete();
				
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

	
	public void selectReserve(NonMemberVO nm) {
		String sql = "SELECT * FROM nm_reservations WHERE nm_name= ? AND nm_phone=?";

		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nm.getNm_name());
			pstmt.setString(2, nm.getNm_phone());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int nm_reserve_num = rs.getInt("nm_reserve_num");
				String nm_reserve_hotel = rs.getString("nm_reserve_hotel");
				String nm_reserve_room = rs.getString("nm_reserve_room");
				String nm_reserve_date = rs.getString("nm_reserve_date");
				String nm_name = rs.getString("nm_name");
				String nm_phone = rs.getString("nm_phone");

				String mrn = Integer.toString(nm_reserve_num);
				
				nmrvNumber.setText(mrn);
				nmrvName.setText(nm_name); 
				nmrvHotel.setText(nm_reserve_hotel);
				nmrvDate.setText(nm_reserve_date);
				nmrvPhone.setText(nm_phone);
				nmrvRoom.setText(nm_reserve_room);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt);
		}
	
	}//selectRev

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
			pstmt.setString(2, nmrvHotel.getText());
			pstmt.setString(3, nmrvRoom.getText());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(pstmt);
		}
	}
	
}//controller
