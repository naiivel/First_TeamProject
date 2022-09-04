package mainPage;

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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import login.loginController;
import util.DBUtils;

public class mainPageController implements Initializable {

	@FXML
	Button reserve, checkRev, chat, btnLogOut;
	private Stage primaryStage;

	MemberVO mv;
	MemberRVO mrv;
	NonMemberVO nmv;
	NonMemberRVO nmrv;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("호출시 자동 실행");
		reserve.setOnAction(e -> {

			reservation();
		
		});

		checkRev.setOnAction(e -> {
			if (loginController.mv.getM_id() != null) {
				checkMemRev();
			} else {
				checkNonMemRev();
			}
		});

		btnLogOut.setOnAction(e -> {
			Stage stage = new Stage();
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
			} catch (IOException e1) {
			}
			stage = (Stage) btnLogOut.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		});

		chat.setOnAction(e -> {
			connect();
		});
	}

	public void reservation() {

		Stage stage = new Stage();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("../choice/Choice.fxml") // 5번 이동
			);
		} catch (IOException e1) {
		}
		stage = (Stage) reserve.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();

	}

	public void checkMemRev() {
		System.out.println("예약확인 선택");
		Stage stage = new Stage();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("../check/CheckReserveM.fxml") // 9번 이동
			);
		} catch (IOException e1) {
		}
		stage = (Stage) checkRev.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();

	}

	public void checkNonMemRev() {
		System.out.println("예약확인 선택");
		Stage stage = new Stage();
		Parent root = null;

		try {
			root = FXMLLoader.load(getClass().getResource("../check/CheckReserveNM.fxml"));
		} catch (IOException e) {

		}

		stage = (Stage) checkRev.getScene().getWindow();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("../check/checkRev.css").toString());
		stage.setScene(scene);
		stage.show();
	}

	public void connect() {
		if (loginController.mv.getM_id() != null) {
			Stage stage = new Stage();
			Parent root = null;

			try {
				root = FXMLLoader.load(getClass().getResource("../client/Client.fxml"));
			} catch (IOException e) {

			}
			stage.setTitle("상담원 연결");
			stage.getIcons().add(new Image("file:resource/icon/counsel.png"));
			stage.setScene(new Scene(root));
			stage.show();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("이용불가 안내");
			alert.setContentText("해당기능은 회원전용 기능입니다.");
			alert.show();
		}
	}


}