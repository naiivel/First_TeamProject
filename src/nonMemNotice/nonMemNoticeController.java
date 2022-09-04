package nonMemNotice;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import base.MemberVO;
import base.NonMemberVO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.DBUtils;

public class nonMemNoticeController implements Initializable {
	@FXML
	private TextArea textAreaNotice;
	@FXML
	private TextField textFieldName;
	@FXML
	private TextField textFieldPhone;
	@FXML
	private Button buttonConfirm;
	@FXML
	private Button buttonCancel;
	private Stage primaryStage;

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	// 로그인 된 사용자 정보 저장
	public static NonMemberVO nmv;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buttonConfirm.setOnAction(e -> confirm());
		buttonCancel.setOnAction(e -> cancel());

	}

	public void cancel() {
		System.out.println("Back 선택");
		Stage stage = new Stage();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
		} catch (IOException e1) {
		}
		stage = (Stage) buttonCancel.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();

	}

	public void confirm() {

		String sql = "INSERT INTO tbl_non_member(nm_name, nm_phone) VALUES (?,?)";

		try {
			conn = DBUtils.getConnection();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, textFieldName.getText());
			pstmt.setString(2, textFieldPhone.getText());
			pstmt.executeUpdate();

			String sql2 = "SELECT * FROM tbl_non_member WHERE nm_name = ? AND nm_phone = ?";

			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, textFieldName.getText());
			pstmt.setString(2, textFieldPhone.getText());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String name = rs.getString("nm_name");
				String phone = rs.getString("nm_phone");

				nmv = new NonMemberVO(name, phone);
				System.out.println(nmv.toString());

				Stage stage = new Stage();
				Parent root = null;
				try {
					root = FXMLLoader.load(getClass().getResource("../mainPage/mainPage.fxml"));
				} catch (IOException e1) {
				}
				stage = (Stage) buttonConfirm.getScene().getWindow();
				stage.setScene(new Scene(root));
				stage.show();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt);
		}

	}

}
