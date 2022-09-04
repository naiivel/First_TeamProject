package choice;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import login.loginController;
import nonMemNotice.nonMemNoticeController;
import base.*;

public class ChoiceController implements Initializable {
	@FXML
	private Label lblGijang, lblHaeundaeGu, lblSeoGu, lblJungGu, lblSuyeongGu;
	@FXML
	private Button btnNext, btnCancel;
	@FXML
	private DatePicker checkInDate, checkOutDate;
	String locate = new String();

	MemberVO mv;
	public static MemberRVO mrv;
	NonMemberVO nmv;
	public static NonMemberRVO nmrv;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		lblGijang.setOnMouseClicked(e -> {
			resetLbl();
			lblGijang.setBorder(new Border(
					new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2))));
			locate = lblGijang.getText();

			if (e.getClickCount() == 2) {
				lblGijang.setBorder(null);
				locate = null;
			}
		});

		lblHaeundaeGu.setOnMouseClicked(e -> {
			resetLbl();
			lblHaeundaeGu.setBorder(new Border(
					new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2))));
			locate = new String(lblHaeundaeGu.getText());

			if (e.getClickCount() == 2) {
				lblHaeundaeGu.setBorder(null);
				locate = null;
			}
		});

		lblJungGu.setOnMouseClicked(e -> {
			resetLbl();
			lblJungGu.setBorder(new Border(
					new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2))));
			locate = new String(lblJungGu.getText());

			if (e.getClickCount() == 2) {
				lblJungGu.setBorder(null);
				locate = null;
			}
		});

		lblSeoGu.setOnMouseClicked(e -> {
			resetLbl();
			lblSeoGu.setBorder(new Border(
					new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2))));
			locate = new String(lblSeoGu.getText());

			if (e.getClickCount() == 2) {
				lblSeoGu.setBorder(null);
				locate = null;
			}
		});

		lblSuyeongGu.setOnMouseClicked(e -> {
			resetLbl();
			lblSuyeongGu.setBorder(new Border(
					new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(2))));
			locate = new String(lblSuyeongGu.getText());

			if (e.getClickCount() == 2) {
				lblSuyeongGu.setBorder(null);
				locate = null;
			}
		});

		btnNext.setOnAction(event -> {
			System.out.println(lblGijang.getBorder());
			if (lblGijang.getBorder()==null && lblHaeundaeGu.getBorder()==null && lblJungGu.getBorder()==null 
					&& lblSeoGu.getBorder()==null && lblSuyeongGu.getBorder()==null)  {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("선택되지 않은 항목이 있습니다.");
				alert.setContentText("여행할 지역을 선택해주세요.");
				alert.show();
			}
			if (checkInDate.getValue() == null || checkOutDate.getValue() == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("다음단계를 진행할수 없습니다.");
				alert.setContentText("예약일을 선택해주세요.");
				alert.show();
			}

			LocalDate dateIn = checkInDate.getValue();
			System.out.println(dateIn);
			String startDate = dateIn.format(DateTimeFormatter.ofPattern("yy/MM/dd"));
			System.out.println(startDate);

			LocalDate dateOut = checkOutDate.getValue();
			System.out.println(dateOut);
			String endDate = dateOut.format(DateTimeFormatter.ofPattern("yy/MM/dd"));
			System.out.println(endDate);
			if (startDate.compareTo(endDate) > 0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("예약일을 다시 확인해주세요.");
				alert.setContentText("체크아웃 일정이 체크인 일정보다 느립니다.");
				alert.show();
				return;
			}

			String reserveDate = startDate + "-" + endDate;
			System.out.println(reserveDate);

			if (loginController.mv.getM_id() != null) {
				mrv = new MemberRVO(reserveDate);
			} else {
				nmrv = new NonMemberRVO(reserveDate);
			}

			switch (locate) {
			case "기장군":
				selectLocation(locate);
				break;
			case "해운대구":
				selectLocation(locate);
				break;
			case "중구":
				selectLocation(locate);
				break;
			case "서구":
				selectLocation(locate);
				break;
			case "수영구":
				selectLocation(locate);
				break;
			}

		});

		btnCancel.setOnAction(event -> {
			checkInDate.setValue(null);
			checkOutDate.setValue(null);
			lblGijang.setBorder(null);
			lblHaeundaeGu.setBorder(null);
			lblSeoGu.setBorder(null);
			lblJungGu.setBorder(null);
			lblSuyeongGu.setBorder(null);
			locate = null;
		});
	}// initialize

	public void selectLocation(String locate) {
		Stage stage = new Stage();
		Parent root = null;
		try {
			if (locate.equals("기장군")) {
				root = FXMLLoader.load(getClass().getResource("../hotelList/Gijang.fxml"));
			} else if (locate.equals("해운대구")) {
				root = FXMLLoader.load(getClass().getResource("../hotelList/HaeundaeGu.fxml"));
			} else if (locate.equals("중구")) {
				root = FXMLLoader.load(getClass().getResource("../hotelList/JungGu.fxml"));
			} else if (locate.equals("서구")) {
				root = FXMLLoader.load(getClass().getResource("../hotelList/SeoGu.fxml"));
			} else if (locate.equals("수영구")) {
				root = FXMLLoader.load(getClass().getResource("../hotelList/SuyeongGu.fxml"));
			}
		} catch (IOException e) {
		}

		stage = (Stage) btnNext.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}

	public void resetLbl() {
		lblGijang.setBorder(null);
		lblHaeundaeGu.setBorder(null);
		lblSeoGu.setBorder(null);
		lblJungGu.setBorder(null);
		lblSuyeongGu.setBorder(null);
		locate = null;
		
	}

}// controller
