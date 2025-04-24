package Dialog;

import com.example.pharmacyproject.PharmacyApplication;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import service.TaiKhoanService;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgetPasswordDialog implements Initializable {
    private TaiKhoanService accountService;
    @FXML
    private AnchorPane inputVitalInfoView, changePassView;

    @FXML
    private TextField tenTKField, cccdField, newPassField, confirmNewPassField;

    @FXML
    private Label guildTextLabel;

    @FXML
    private Button confirmBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountService = PharmacyApplication.getAccountService();
        setEventListener();
    }

    private void setEventListener() {
        confirmBtn.setOnMouseClicked(event -> confirmBtnClicked());
        inputVitalInfoView.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                changePassView.setVisible(true);
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), changePassView);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
                fadeTransition.play();
            }
        });
    }

    private void confirmBtnClicked() {
        if (inputVitalInfoView.isVisible()) {
            onChangePass();
        }
    }

    private void onChangePass() {
        try {
            if (accountService.forgetPassword(tenTKField.getText().trim(), cccdField.getText().trim())) {
                showSuccessAlert("Thành công", "Mật khẩu mới của bạn là 123456");
            } else {
                showFailAlert("Lỗi", "Đổi mật khẩu thất bại");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showSuccessAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        confirmBtn.getScene().getWindow().hide();
    }

    private void showFailAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        confirmBtn.getScene().getWindow().hide();
    }
}
