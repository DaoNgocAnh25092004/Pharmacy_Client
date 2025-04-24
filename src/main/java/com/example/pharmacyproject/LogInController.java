package com.example.pharmacyproject;

import model.NhanVien;
import model.TaiKhoan;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.TaiKhoanService;
import service.NhanVienService;

import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    private TaiKhoanService accountService;
    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountService = PharmacyApplication.getAccountService();
    }

    @FXML
    protected void onForgetPassword(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PharmacyApplication.class.getResource("forget_password_dialog.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Quên mật khẩu");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onLogInButtonClick() throws Exception {
        String userName = userNameField.getText().trim();
        String password = passwordField.getText().trim();

        accountService = PharmacyApplication.getAccountService();
        TaiKhoan taiKhoan = accountService.dangNhap(userName, password);
        System.out.println(taiKhoan);
        NhanVienService employeeService = PharmacyApplication.getEmployeeService();
        if (taiKhoan != null) {
            NhanVien nhanVien = employeeService.timNhanVienTheoMaTaiKhoan(taiKhoan.getMaTaiKhoan());
            try {
                loginBtn.getScene().getWindow().hide();
                FXMLLoader fxmlLoader = new FXMLLoader(PharmacyApplication.class.getResource("manager_dashboard.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 1024, 640);
                scene.getStylesheets().add(PharmacyApplication.class.getResource("style.css").toExternalForm());
                stage.setTitle("Vital Care");
                stage.setScene(scene);
                stage.setResizable(false);

                Dashboard dashboard = fxmlLoader.getController();
                dashboard.setPrimaryStage(stage);
                dashboard.setNhanVienInfo(nhanVien, taiKhoan);

                stage.show();
            } catch (IOException e) {
                showErrorDialog();
                e.printStackTrace();
            }
        } else {
            showErrorDialog();
        }
    }
    private void showErrorDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText("Tên tài khoản hoặc mật khẩu không đúng");
        alert.showAndWait();
    }
}
