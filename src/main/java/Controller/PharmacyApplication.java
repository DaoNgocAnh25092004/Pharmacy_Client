package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import service.*;

import java.io.IOException;
import java.rmi.Naming;


public class PharmacyApplication extends Application {
    @Getter
    private static TaiKhoanService accountService;
    @Getter
    private static NhanVienService employeeService;
    @Getter
    private static SanPhamService productService;
    @Getter
    private static KhachHangService customerService;
    @Getter
    private static HoaDonService invoiceService;
    @Getter
    private static ChiTietHoaDonService detailService;
    @Getter
    private static NhomThuocService nhomThuocService;
    @Getter
    private static DonViTinhService donViTinhService;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PharmacyApplication.class.getResource("log_in.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 379);
        stage.setTitle("Vital Care");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            accountService = (TaiKhoanService) Naming.lookup("rmi://localhost:9731/account");
            employeeService = (NhanVienService) Naming.lookup("rmi://localhost:9731/employee");
            productService = (SanPhamService) Naming.lookup("rmi://localhost:9731/product");
            customerService = (KhachHangService) Naming.lookup("rmi://localhost:9731/customer");
            invoiceService = (HoaDonService) Naming.lookup("rmi://localhost:9731/invoice");
            detailService = (ChiTietHoaDonService) Naming.lookup("rmi://localhost:9731/invoice_detail");
            donViTinhService = (DonViTinhService) Naming.lookup("rmi://localhost:9731/unit");
            nhomThuocService = (NhomThuocService) Naming.lookup("rmi://localhost:9731/drug_group");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        launch();
    }

}