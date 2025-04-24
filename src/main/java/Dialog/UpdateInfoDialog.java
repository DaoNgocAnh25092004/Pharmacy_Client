package Dialog;

import com.example.pharmacyproject.PharmacyApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.NhanVien;
import model.TaiKhoan;
import service.NhanVienService;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateInfoDialog implements Initializable {
    private NhanVien nhanVien;
    private TaiKhoan taiKhoan;
    @FXML
    private TextField passTextField;

    @FXML
    private Button confirmBtn;

    public void setPersonalInfo(NhanVien nhanVien, TaiKhoan taiKhoan){
        this.nhanVien = nhanVien;
        this.taiKhoan = taiKhoan;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEventListener();
    }

    private void setEventListener() {
        confirmBtn.setOnMouseClicked(event -> {
            try {
                String password = passTextField.getText().trim();
                if (password.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Vui lòng nhập mật khẩu");
                    alert.showAndWait();
                } else {
                    NhanVienService employeeService = PharmacyApplication.getEmployeeService();
                    String maNhanVien = nhanVien.getMaNhanVien();
                    if (taiKhoan.getPassword().equals(password)) {
                        //TODO: Update info
                        if (employeeService.update(nhanVien)){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Thành công");
                            alert.setHeaderText(null);
                            alert.setContentText("Cập nhật thông tin thành công");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Lỗi");
                            alert.setHeaderText(null);
                            alert.setContentText("Cập nhật thông tin thất bại");
                            alert.showAndWait();
                        }
                        confirmBtn.getScene().getWindow().hide();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Lỗi");
                        alert.setHeaderText(null);
                        alert.setContentText("Mật khẩu không đúng");
                        alert.showAndWait();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
