package hotelRoom.JungGu;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import login.loginController;
import nonMemNotice.nonMemNoticeController;
import util.DBUtils;
import base.*;
import choice.ChoiceController;

public class RoomController implements Initializable {

	@FXML
	private AnchorPane roomType, price, attraction;
	@FXML
	private MenuItem itemType, itemPrice, itemAttraction;
	@FXML
	private Button btnback, btncon;
	@FXML
	private Label choice, title;

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	MemberVO mv;
	MemberRVO mrv;
	NonMemberVO nmv;
	NonMemberRVO nmrv;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnback.setOnAction(event -> {
			back("../../hotelList/JungGu.fxml", btnback);
		});

		btncon.setOnAction(e -> {
			Confirm();
		});

	}

	private void back(String location, Button btnback) {
		Stage stage = new Stage();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource(location));
		} catch (IOException e1) {
		}
		stage = (Stage) btnback.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}

	public void handle(ActionEvent event) {
		MenuItem mi = (MenuItem) event.getSource();
		if (mi == itemType) {
			roomType.toFront();
		} else if (mi == itemPrice) {
			price.toFront();
		} else if (mi == itemAttraction) {
			attraction.toFront();
		}
	}

	public void change(ActionEvent event) {
		MenuItem mi = (MenuItem) event.getSource();
		String ch = mi.getText();
		choice.setText(ch);
	}

	public void Confirm() {
		System.out.println("Confirm");
		String sql = "SELECT reservable FROM tbl_hotel WHERE h_name=? AND room=?";

		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title.getText());
			pstmt.setString(2, choice.getText());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				boolean ps = rs.getBoolean("reservable");
				if (ps == true) {
					// 난수 생성
					Random ran = new Random();
					String rand = Integer.toString(ran.nextInt(8) + 1);
					for (int i = 0; i < 7; i++) {
						rand += Integer.toString(ran.nextInt(9));
					}

					int rans = Integer.parseInt(rand);

					loginController.mv.setM_reserve_num(rans);

					if (loginController.mv.getM_id() != null) {
						// 회원예약
						System.out.println("회원예약이 진행됩니다.");

						String sqlm = "UPDATE tbl_member SET m_reserve_num=? where m_id=?";
						pstmt = conn.prepareStatement(sqlm);
						pstmt.setInt(1, rans);
						pstmt.setString(2, loginController.mv.getM_id());
						pstmt.executeUpdate();

						String sqlrm = "INSERT INTO m_reservations(m_reserve_num,m_reserve_hotel,m_reserve_room,"
								+ "m_reserve_date,m_id,m_name,m_phone)" + "VALUES(?,?,?,?,?,?,?)";
						pstmt = conn.prepareStatement(sqlrm);
						pstmt.setInt(1, rans);
						pstmt.setString(2, title.getText());
						pstmt.setString(3, choice.getText());
						pstmt.setString(4, ChoiceController.mrv.getM_reserve_date());
						pstmt.setString(5, loginController.mv.getM_id());
						pstmt.setString(6, loginController.mv.getM_name());
						pstmt.setString(7, loginController.mv.getM_phone());
						pstmt.executeUpdate();

						String change = "UPDATE tbl_hotel SET reservable=? WHERE h_name=? AND room=?";
						pstmt = conn.prepareStatement(change);
						pstmt.setInt(1, 0);
						pstmt.setString(2, title.getText());
						pstmt.setString(3, choice.getText());
						pstmt.executeUpdate();

					} else {
						// 비회원 예약진행
						System.out.println("비회원예약이 진행됩니다.");

						String sqln = "UPDATE tbl_non_member SET nm_reserve_num=? WHERE nm_phone=?";

						pstmt = conn.prepareStatement(sqln);
						pstmt.setInt(1, rans);
						pstmt.setString(2, nonMemNoticeController.nmv.getNm_phone());
						pstmt.executeUpdate();

						String sqlrn = "INSERT INTO nm_reservations(nm_reserve_num,nm_reserve_hotel,nm_reserve_room,"
								+ "nm_reserve_date,nm_name,nm_phone)" + "VALUES(?,?,?,?,?,?)";

						pstmt = conn.prepareStatement(sqlrn);
						pstmt.setInt(1, rans);
						pstmt.setString(2, title.getText());
						pstmt.setString(3, choice.getText());
						pstmt.setString(4, ChoiceController.nmrv.getNm_reserve_date());
						pstmt.setString(5, nonMemNoticeController.nmv.getNm_name());
						pstmt.setString(6, nonMemNoticeController.nmv.getNm_phone());
						pstmt.executeUpdate();

						String change = "UPDATE tbl_hotel SET reservable=? WHERE h_name=? AND room=?";
						pstmt = conn.prepareStatement(change);
						pstmt.setInt(1, 0);
						pstmt.setString(2, title.getText());
						pstmt.setString(3, choice.getText());
						pstmt.executeUpdate();
					}

					Stage stage = new Stage();
					Parent root = null;
					try {
						root = FXMLLoader.load(getClass().getResource("../../confirm/Confirm.fxml"));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					stage = (Stage) btncon.getScene().getWindow();
					stage.setScene(new Scene(root));
					stage.show();
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("안내문");
					alert.setHeaderText("현재 선택하신 객실은 현재 예약이 불가능합니다.");
					alert.setContentText("다른 객실 예약을 진행 해주시길 바랍니다.");
					alert.show();
				}
			} else {
				System.out.println("검색 결과 없음");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt);
		}

	}// confirm 종료
}
