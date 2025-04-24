package com.example.pharmacyproject;

import com.example.pharmacyproject.Dialog.UpdateInfoDialog;
import model.NhanVien;
import model.TaiKhoan;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.TaiKhoanService;
import service.NhanVienService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ThongTinCaNhanController implements Initializable {
    private TaiKhoanService accountService;
    private NhanVienService employeeService;
    private NhanVien personalInfo;
    private TaiKhoan taiKhoan;
    @FXML
    private Button updateInfoBtn, changePasswordBtn, confirmPassChangeBtn;

    @FXML
    private ImageView hoTenEditBtn, diaChiEditBtn, sdtEditBtn, ngaySinhEditBtn;

    @FXML
    private TextField tenTKTextField, hoTenTextField, diaChiTextField, sdtTextField, cccdTextField, currentPassTextField, newPassTextField;

    @FXML
    private DatePicker ngaySinhPicker, ngayVaoLamPicker;

    @FXML
    private ComboBox<String> caLamViecCB, tinhTrangCB;

    @FXML
    private RadioButton namRadioBtn, nuRadioBtn;

    @FXML
    private VBox inputPassVbox;

    public void setPersonalInfo(NhanVien nhanVien){
        this.personalInfo = nhanVien;
        System.out.println(personalInfo.getHoTen());
        showPersonalInfo();

    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
        System.out.println(taiKhoan.getMaTaiKhoan());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeService = PharmacyApplication.getEmployeeService();
        accountService = PharmacyApplication.getAccountService();
        setEventListener();
    }

    private void showPersonalInfo() {
        tenTKTextField.setText(personalInfo.getMaNhanVien());
        hoTenTextField.setText(personalInfo.getHoTen());
        diaChiTextField.setText(personalInfo.getDiaChi());
        sdtTextField.setText(personalInfo.getSdt());
        cccdTextField.setText(personalInfo.getCccd());
        ngayVaoLamPicker.setValue(personalInfo.getNgayVaoLam());
        tinhTrangCB.setValue(String.valueOf(personalInfo.getTinhTrangNhanVien()));
    }

    private void setEventListener() {
        confirmPassChangeBtn.setOnMouseClicked(event -> onConfirmChangePass());
        updateInfoBtn.setOnMouseClicked(event -> onUpdateBtnClick());
        changePasswordBtn.setOnMouseClicked(event -> onChangePassBtnClick());

        //imageViews edit buttons handle
        hoTenEditBtn.setOnMouseClicked(event ->{
            hoTenTextField.setEditable(true);
            hoTenTextField.requestFocus();
        });
        diaChiEditBtn.setOnMouseClicked(event -> {
            diaChiTextField.setEditable(true);
            diaChiTextField.requestFocus();
        });
        sdtEditBtn.setOnMouseClicked(event -> {
            sdtTextField.setEditable(true);
            sdtTextField.requestFocus();
        });
        ngaySinhEditBtn.setOnMouseClicked(event -> ngaySinhPicker.setDisable(false));
    }

    private void onConfirmChangePass() {
        try {
            String currentPass = currentPassTextField.getText().trim();
            String newPass = newPassTextField.getText().trim();

            String maTaiKhoan = taiKhoan.getMaTaiKhoan();
            if(accountService.doiMatKhau(maTaiKhoan, currentPass, newPass)){
                showAlertDialog("Thành công", "Đổi mật khẩu thành công");
            } else {
                showAlertDialog("Lỗi", "Mật khẩu hiện tại không đúng");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlertDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void onChangePassBtnClick() {
        inputPassVbox.setVisible(true);
        FadeTransition fadeVbox = new FadeTransition(Duration.millis(500), inputPassVbox);
        fadeVbox.setFromValue(0);
        fadeVbox.setToValue(1);
        fadeVbox.play();
    }

    private void onUpdateBtnClick() {
        personalInfo.setHoTen(hoTenTextField.getText().trim());
        personalInfo.setDiaChi(diaChiTextField.getText().trim());
        personalInfo.setSdt(sdtTextField.getText().trim());
        //show dialog
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("update_info_dialog.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 500, 300);
            stage.setTitle("Vital Care");
            stage.setScene(scene);
            stage.setResizable(false);

            UpdateInfoDialog updateInfoDialog = fxmlLoader.getController();
            updateInfoDialog.setPersonalInfo(personalInfo, taiKhoan);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
